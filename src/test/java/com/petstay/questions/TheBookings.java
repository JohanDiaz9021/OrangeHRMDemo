package com.petstay.questions;

import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;
import net.serenitybdd.screenplay.questions.CurrentVisibility;

import java.util.ArrayList;
import java.util.Collection;

import static com.petstay.ui.MyBookingsPage.CANCEL_BUTTON;
import static com.petstay.ui.MyBookingsPage.PET_STATUSES;
import static com.petstay.ui.MyBookingsPage.REFUND;
import static com.petstay.ui.MyBookingsPage.ROW_CELLS;
import static com.petstay.ui.MyBookingsPage.STATUS;

/** Consultas sobre la pantalla "Mis reservas". */
public class TheBookings {

    private static final String NOT_LISTED = "(la reserva no está en la lista)";

    public static Question<String> statusOf(String code) {
        return Question.about("el estado de la reserva " + code)
                .answeredBy(actor -> Text.ofEach(STATUS.of(code)).answeredBy(actor).stream().findFirst().orElse(NOT_LISTED));
    }

    public static Question<String> refundOf(String code) {
        return Question.about("el reembolso de la reserva " + code)
                .answeredBy(actor -> Text.ofEach(REFUND.of(code)).answeredBy(actor).stream().findFirst().orElse(NOT_LISTED));
    }

    /** Código, mascota, entrada, salida, total, estado y reembolso, en el orden de la tabla. */
    public static Question<Collection<String>> detailsOf(String code) {
        return Question.about("los datos de la reserva " + code)
                .answeredBy(actor -> new ArrayList<>(Text.ofEach(ROW_CELLS.of(code)).answeredBy(actor)));
    }

    public static Question<Collection<String>> statusesOf(String pet) {
        return Question.about("los estados de las reservas de " + pet)
                .answeredBy(actor -> new ArrayList<>(Text.ofEach(PET_STATUSES.of(pet)).answeredBy(actor)));
    }

    public static Question<Integer> activeCountOf(String pet) {
        return Question.about("la cantidad de reservas activas de " + pet)
                .answeredBy(actor -> (int) statusesOf(pet).answeredBy(actor).stream()
                        .filter("Confirmada"::equals)
                        .count());
    }

    public static Question<Boolean> canBeCancelled(String code) {
        return Question.about("si la reserva " + code + " se puede cancelar")
                .answeredBy(actor -> CurrentVisibility.of(CANCEL_BUTTON.of(code)).answeredBy(actor));
    }
}
