package com.petstay.tasks;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.SelectFromOptions;

import java.time.LocalDate;

import static com.petstay.models.HotelFormats.date;
import static com.petstay.ui.BookingPage.CALCULATE_BUTTON;
import static com.petstay.ui.BookingPage.CHECK_IN;
import static com.petstay.ui.BookingPage.CHECK_OUT;
import static com.petstay.ui.BookingPage.PET;
import static com.petstay.ui.BookingPage.PET_OPTION;

public class PrepareBooking {

    public static final String CHECK_IN_DATE = "fecha de entrada";
    public static final String CHECK_OUT_DATE = "fecha de salida";

    /** Llena el formulario de reserva y calcula el resumen, sin confirmar. */
    public static Performable of(String pet, LocalDate checkIn, LocalDate checkOut) {
        return Task.where("{0} prepara una reserva para " + pet + " del " + date(checkIn) + " al " + date(checkOut),
                actor -> {
                    actor.remember(CHECK_IN_DATE, checkIn);
                    actor.remember(CHECK_OUT_DATE, checkOut);
                    // Una reserva nueva todavía no tiene código
                    actor.forget(ConfirmBooking.BOOKING_CODE_KEY);

                    actor.attemptsTo(NavigateTo.theBookingForm());
                    // Las opciones dicen "Toby (Perro, Pequeño)": se busca la que empieza por el nombre
                    String petId = PET_OPTION.of(pet).resolveFor(actor).getDomAttribute("value");
                    actor.attemptsTo(
                            SelectFromOptions.byValue(petId).from(PET),
                            Enter.theValue(date(checkIn)).into(CHECK_IN),
                            Enter.theValue(date(checkOut)).into(CHECK_OUT),
                            Click.on(CALCULATE_BUTTON)
                    );
                });
    }
}
