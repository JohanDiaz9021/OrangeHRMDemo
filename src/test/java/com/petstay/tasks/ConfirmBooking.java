package com.petstay.tasks;

import com.petstay.interactions.WaitForLoading;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.questions.Text;

import static com.petstay.ui.BookingPage.BOOKING_CODE;
import static com.petstay.ui.BookingPage.CONFIRM_BUTTON;

public class ConfirmBooking {

    public static final String BOOKING_CODE_KEY = "código de la reserva";

    /** Confirma la reserva del resumen y recuerda el código generado para los pasos siguientes. */
    public static Performable now() {
        return Task.where("{0} confirma la reserva",
                actor -> {
                    actor.attemptsTo(
                            Click.on(CONFIRM_BUTTON),
                            WaitForLoading.toFinish()
                    );
                    actor.remember(BOOKING_CODE_KEY, Text.of(BOOKING_CODE).answeredBy(actor));
                });
    }
}
