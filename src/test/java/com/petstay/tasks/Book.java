package com.petstay.tasks;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.questions.CurrentVisibility;

import java.time.LocalDate;

import static com.petstay.models.HotelFormats.date;
import static com.petstay.ui.BookingPage.SUMMARY;

public class Book {

    /**
     * Prepara y confirma una reserva. Si la aplicación la rechaza (no muestra el resumen),
     * no hay nada que confirmar y la tarea termina: el Entonces del escenario decide si eso era lo esperado.
     */
    public static Performable forPet(String pet, LocalDate checkIn, LocalDate checkOut) {
        return Task.where("{0} reserva para " + pet + " del " + date(checkIn) + " al " + date(checkOut),
                actor -> {
                    actor.attemptsTo(PrepareBooking.of(pet, checkIn, checkOut));
                    if (Boolean.TRUE.equals(CurrentVisibility.of(SUMMARY).answeredBy(actor))) {
                        actor.attemptsTo(ConfirmBooking.now());
                    }
                });
    }
}
