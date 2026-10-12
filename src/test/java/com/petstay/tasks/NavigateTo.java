package com.petstay.tasks;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static com.petstay.ui.Header.BOOK;
import static com.petstay.ui.Header.MY_BOOKINGS;
import static com.petstay.ui.Header.MY_PETS;
import static com.petstay.ui.PageMessage.PAGE_TITLE;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.containsText;

public class NavigateTo {

    public static Performable theLoginPage() {
        // La URL base está en serenity.conf (webdriver.base.url)
        return Task.where("{0} abre la pantalla de inicio de sesión", Open.relativeUrl("/index.html"));
    }

    public static Performable myPets() {
        return Task.where("{0} va a Mis mascotas", Click.on(MY_PETS), titleIs("Mis mascotas"));
    }

    public static Performable theBookingForm() {
        return Task.where("{0} va a Reservar", Click.on(BOOK), titleIs("Reservar estadía"));
    }

    public static Performable myBookings() {
        return Task.where("{0} va a Mis reservas", Click.on(MY_BOOKINGS), titleIs("Mis reservas"));
    }

    /** Espera a que cargue la pantalla nueva antes de que otra tarea o pregunta la lea. */
    private static Performable titleIs(String title) {
        return WaitUntil.the(PAGE_TITLE, containsText(title)).forNoMoreThan(10).seconds();
    }
}
