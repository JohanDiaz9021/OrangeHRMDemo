package com.petstay.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;
import net.serenitybdd.screenplay.questions.CurrentVisibility;
import net.serenitybdd.screenplay.targets.Target;

import static com.petstay.models.HotelFormats.amountOf;
import static com.petstay.models.HotelFormats.pesos;
import static com.petstay.ui.BookingPage.ACCOMMODATION;
import static com.petstay.ui.BookingPage.BATH;
import static com.petstay.ui.BookingPage.BATH_ROW;
import static com.petstay.ui.BookingPage.DISCOUNT;
import static com.petstay.ui.BookingPage.DISCOUNT_ROW;
import static com.petstay.ui.BookingPage.NIGHTS;
import static com.petstay.ui.BookingPage.SUMMARY;
import static com.petstay.ui.BookingPage.TOTAL;
import static com.petstay.ui.BookingPage.WALK;
import static com.petstay.ui.BookingPage.WALK_ROW;

/**
 * Lee el resumen de la reserva en términos de negocio.
 * La pantalla oculta las filas en cero y muestra el descuento como "-$48.000";
 * aquí se traduce a "$0" y "$48.000", que es como lo describe la historia de usuario.
 */
public class TheBookingSummary {

    public static Question<Boolean> isDisplayed() {
        return Question.about("si se muestra el resumen de la reserva")
                .answeredBy(actor -> CurrentVisibility.of(SUMMARY).answeredBy(actor));
    }

    public static Question<String> nights() {
        return Question.about("las noches del resumen").answeredBy(actor -> Text.of(NIGHTS).answeredBy(actor).trim());
    }

    public static Question<String> accommodation() {
        return Question.about("el valor del alojamiento").answeredBy(actor -> Text.of(ACCOMMODATION).answeredBy(actor).trim());
    }

    public static Question<String> discount() {
        return Question.about("el descuento por estadía larga")
                .answeredBy(actor -> pesos(Math.abs(amountShownIn(actor, DISCOUNT_ROW, DISCOUNT))));
    }

    /** Suma de los servicios adicionales: baño y peluquería más paseo diario. */
    public static Question<String> services() {
        return Question.about("el valor de los servicios adicionales")
                .answeredBy(actor -> pesos(amountShownIn(actor, BATH_ROW, BATH) + amountShownIn(actor, WALK_ROW, WALK)));
    }

    public static Question<String> total() {
        return Question.about("el total de la reserva").answeredBy(actor -> Text.of(TOTAL).answeredBy(actor).trim());
    }

    private static long amountShownIn(Actor actor, Target row, Target value) {
        return CurrentVisibility.of(row).answeredBy(actor) ? amountOf(Text.of(value).answeredBy(actor)) : 0;
    }
}
