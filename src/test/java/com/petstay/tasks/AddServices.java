package com.petstay.tasks;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.SetCheckbox;

import java.util.Arrays;

import static com.petstay.ui.BookingPage.CALCULATE_BUTTON;
import static com.petstay.ui.BookingPage.SERVICE;

public class AddServices {

    /** Marca los servicios y recalcula, porque cualquier cambio en el formulario oculta el resumen. */
    public static Performable named(String... services) {
        return Task.where("{0} agrega los servicios " + Arrays.asList(services),
                actor -> {
                    for (String service : services) {
                        actor.attemptsTo(SetCheckbox.of(SERVICE.of(service)).toTrue());
                    }
                    actor.attemptsTo(Click.on(CALCULATE_BUTTON));
                });
    }
}
