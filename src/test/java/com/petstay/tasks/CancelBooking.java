package com.petstay.tasks;

import com.petstay.interactions.WaitForLoading;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;

import static com.petstay.ui.MyBookingsPage.CANCEL_BUTTON;
import static com.petstay.ui.MyBookingsPage.CONFIRM_CANCELLATION;
import static com.petstay.ui.MyBookingsPage.KEEP_BOOKING;

public class CancelBooking {

    public static Performable accepting(String code) {
        return Task.where("{0} cancela la reserva " + code + " y acepta la confirmación",
                NavigateTo.myBookings(),
                Click.on(CANCEL_BUTTON.of(code)),
                Click.on(CONFIRM_CANCELLATION),
                WaitForLoading.toFinish()
        );
    }

    public static Performable declining(String code) {
        return Task.where("{0} intenta cancelar la reserva " + code + " pero rechaza la confirmación",
                NavigateTo.myBookings(),
                Click.on(CANCEL_BUTTON.of(code)),
                Click.on(KEEP_BOOKING)
        );
    }
}
