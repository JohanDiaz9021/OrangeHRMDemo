package com.petstay.stepdefinitions;

import com.petstay.questions.TheBookingSummary;
import com.petstay.questions.TheBookings;
import com.petstay.tasks.Book;
import com.petstay.tasks.CancelBooking;
import com.petstay.tasks.ConfirmBooking;
import com.petstay.tasks.NavigateTo;
import com.petstay.tasks.PrepareBooking;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.ensure.Ensure;

import java.time.LocalDate;

import static com.petstay.models.HotelFormats.date;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class CancellationStepDefinitions {

    @Dado("que {string} tiene una reserva Confirmada de {int} noches por {word} con entrada el día {dia}")
    public void hasAConfirmedBookingFor(String pet, int nights, String total, LocalDate checkIn) {
        theActorInTheSpotlight().attemptsTo(
                PrepareBooking.of(pet, checkIn, checkIn.plusDays(nights)),
                Ensure.that(TheBookingSummary.total()).isEqualTo(total),
                ConfirmBooking.now()
        );
    }

    @Dado("que {string} tiene una reserva en estado {string} con reembolso de {word}")
    public void hasABookingWithStatus(String pet, String status, String refund) {
        Actor actor = theActorInTheSpotlight();
        // Con 10 días de anticipación la cancelación reembolsa el 100%
        LocalDate checkIn = LocalDate.now().plusDays(10);
        actor.attemptsTo(Book.forPet(pet, checkIn, checkIn.plusDays(2)));
        String code = actor.recall(ConfirmBooking.BOOKING_CODE_KEY);
        actor.attemptsTo(
                CancelBooking.accepting(code),
                Ensure.that(TheBookings.statusOf(code)).isEqualTo(status),
                Ensure.that(TheBookings.refundOf(code)).isEqualTo(refund)
        );
    }

    @Cuando("cancelo la reserva y acepto la confirmación")
    public void cancelsAndAccepts() {
        Actor actor = theActorInTheSpotlight();
        actor.attemptsTo(CancelBooking.accepting(actor.recall(ConfirmBooking.BOOKING_CODE_KEY)));
    }

    @Cuando("cancelo la reserva y rechazo la confirmación")
    public void cancelsAndDeclines() {
        Actor actor = theActorInTheSpotlight();
        actor.attemptsTo(CancelBooking.declining(actor.recall(ConfirmBooking.BOOKING_CODE_KEY)));
    }

    @Entonces("la lista muestra el código, {string}, las fechas, {string}, {string} y reembolso {string}")
    public void listShowsTheBooking(String pet, String total, String status, String refund) {
        Actor actor = theActorInTheSpotlight();
        String code = actor.recall(ConfirmBooking.BOOKING_CODE_KEY);
        LocalDate checkIn = actor.recall(PrepareBooking.CHECK_IN_DATE);
        LocalDate checkOut = actor.recall(PrepareBooking.CHECK_OUT_DATE);
        actor.attemptsTo(
                NavigateTo.myBookings(),
                Ensure.that(TheBookings.detailsOf(code))
                        .containsExactly(code, pet, date(checkIn), date(checkOut), total, status, refund)
        );
    }

    @Entonces("la lista no muestra reembolso para esa reserva")
    public void listShowsNoRefund() {
        Actor actor = theActorInTheSpotlight();
        String code = actor.recall(ConfirmBooking.BOOKING_CODE_KEY);
        // La aplicación muestra un guion largo cuando no hay reembolso
        actor.attemptsTo(Ensure.that(TheBookings.refundOf(code)).isEqualTo("—"));
    }

    @Entonces("la opción de cancelar no está disponible para esa reserva")
    public void cancelOptionIsNotAvailable() {
        Actor actor = theActorInTheSpotlight();
        String code = actor.recall(ConfirmBooking.BOOKING_CODE_KEY);
        actor.attemptsTo(Ensure.that(TheBookings.canBeCancelled(code)).isFalse());
    }

    @Entonces("el reembolso mostrado sigue siendo {string}")
    public void refundIsStill(String refund) {
        Actor actor = theActorInTheSpotlight();
        String code = actor.recall(ConfirmBooking.BOOKING_CODE_KEY);
        actor.attemptsTo(Ensure.that(TheBookings.refundOf(code)).isEqualTo(refund));
    }
}
