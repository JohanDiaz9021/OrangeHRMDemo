package com.petstay.stepdefinitions;

import com.petstay.questions.TheBookingSummary;
import com.petstay.questions.TheBookings;
import com.petstay.questions.TheMessage;
import com.petstay.questions.ThePetList;
import com.petstay.tasks.AddServices;
import com.petstay.tasks.Book;
import com.petstay.tasks.ConfirmBooking;
import com.petstay.tasks.NavigateTo;
import com.petstay.tasks.PrepareBooking;
import com.petstay.tasks.RegisterPet;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.ensure.Ensure;

import java.time.LocalDate;
import java.util.Map;

import static com.petstay.ui.BookingPage.SERVICE;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class BookingStepDefinitions {

    @Dado("tengo registrado el {especie} {string} de {int} kg \\({word}\\)")
    public void hasRegisteredThePet(String species, String name, int weight, String size) {
        // La edad no influye en las reservas: se usa una válida
        theActorInTheSpotlight().attemptsTo(
                RegisterPet.with(name, species, String.valueOf(weight), "3", true),
                Ensure.that(ThePetList.sizeOf(name)).isEqualTo(size)
        );
    }

    @Cuando("preparo una reserva para {string} con entrada el día {dia} y {int} noches, sin servicios")
    public void preparesABookingWithoutServices(String pet, LocalDate checkIn, int nights) {
        theActorInTheSpotlight().attemptsTo(PrepareBooking.of(pet, checkIn, checkIn.plusDays(nights)));
    }

    @Cuando("preparo una reserva para {string} con entrada el día {dia} y {int} noches")
    public void preparesABooking(String pet, LocalDate checkIn, int nights) {
        theActorInTheSpotlight().attemptsTo(PrepareBooking.of(pet, checkIn, checkIn.plusDays(nights)));
    }

    @Cuando("preparo una reserva para {string} del día {dia} al día {dia}")
    public void preparesABookingBetween(String pet, LocalDate checkIn, LocalDate checkOut) {
        theActorInTheSpotlight().attemptsTo(PrepareBooking.of(pet, checkIn, checkOut));
    }

    @Cuando("agrego {string} y {string}")
    public void addsServices(String firstService, String secondService) {
        theActorInTheSpotlight().attemptsTo(AddServices.named(firstService, secondService));
    }

    @Entonces("el resumen muestra alojamiento {string}, descuento {string} y total {string}")
    public void summaryShows(String accommodation, String discount, String total) {
        theActorInTheSpotlight().attemptsTo(
                Ensure.that(TheBookingSummary.accommodation()).isEqualTo(accommodation),
                Ensure.that(TheBookingSummary.discount()).isEqualTo(discount),
                Ensure.that(TheBookingSummary.total()).isEqualTo(total)
        );
    }

    @Entonces("el resumen muestra:")
    public void summaryShowsTable(DataTable table) {
        Map<String, String> expected = table.asMaps().get(0);
        theActorInTheSpotlight().attemptsTo(
                Ensure.that(TheBookingSummary.nights()).isEqualTo(expected.get("noches")),
                Ensure.that(TheBookingSummary.accommodation()).isEqualTo(expected.get("alojamiento")),
                Ensure.that(TheBookingSummary.discount()).isEqualTo(expected.get("descuento")),
                Ensure.that(TheBookingSummary.services()).isEqualTo(expected.get("servicios")),
                Ensure.that(TheBookingSummary.total()).isEqualTo(expected.get("total"))
        );
    }

    @Cuando("confirmo la reserva")
    public void confirmsTheBooking() {
        theActorInTheSpotlight().attemptsTo(ConfirmBooking.now());
    }

    @Entonces("se genera un código con formato {string}")
    public void aCodeIsGenerated(String format) {
        Actor actor = theActorInTheSpotlight();
        String code = actor.recall(ConfirmBooking.BOOKING_CODE_KEY);
        // "PS-XXXX": cada X representa un dígito
        String pattern = format.replace("X", "\\d");
        actor.attemptsTo(Ensure.that(String.valueOf(code)).matches(pattern));
    }

    @Entonces("la (nueva )reserva queda/sigue en estado {string}")
    public void bookingHasStatus(String status) {
        Actor actor = theActorInTheSpotlight();
        String code = actor.recall(ConfirmBooking.BOOKING_CODE_KEY);
        actor.attemptsTo(
                NavigateTo.myBookings(),
                Ensure.that(TheBookings.statusOf(code)).isEqualTo(status)
        );
    }

    @Entonces("la reserva es rechazada")
    public void bookingIsRejected() {
        theActorInTheSpotlight().attemptsTo(
                Ensure.that(TheBookingSummary.isDisplayed()).isFalse(),
                Ensure.that(TheMessage.isAnError()).isTrue()
        );
    }

    @Entonces("{string} no tiene reservas")
    public void petHasNoBookings(String pet) {
        theActorInTheSpotlight().attemptsTo(
                NavigateTo.myBookings(),
                Ensure.that(TheBookings.statusesOf(pet)).isEmpty()
        );
    }

    @Entonces("la opción {string} no está disponible")
    public void serviceIsNotAvailable(String service) {
        theActorInTheSpotlight().attemptsTo(Ensure.that(SERVICE.of(service)).isDisabled());
    }

    @Entonces("el total es {string}")
    public void totalIs(String total) {
        theActorInTheSpotlight().attemptsTo(Ensure.that(TheBookingSummary.total()).isEqualTo(total));
    }

    @Dado("que {string} tiene una reserva Confirmada del día {dia} al día {dia}")
    public void hasAConfirmedBooking(String pet, LocalDate checkIn, LocalDate checkOut) {
        Actor actor = theActorInTheSpotlight();
        actor.attemptsTo(Book.forPet(pet, checkIn, checkOut));
        String code = actor.recall(ConfirmBooking.BOOKING_CODE_KEY);
        actor.attemptsTo(
                NavigateTo.myBookings(),
                Ensure.that(TheBookings.statusOf(code)).isEqualTo("Confirmada")
        );
    }

    @Cuando("reservo para {string} del día {dia} al día {dia}")
    public void books(String pet, LocalDate checkIn, LocalDate checkOut) {
        theActorInTheSpotlight().attemptsTo(Book.forPet(pet, checkIn, checkOut));
    }

    @Entonces("{string} sigue con una sola reserva activa")
    public void petHasOnlyOneActiveBooking(String pet) {
        theActorInTheSpotlight().attemptsTo(
                NavigateTo.myBookings(),
                Ensure.that(TheBookings.activeCountOf(pet)).isEqualTo(1)
        );
    }
}
