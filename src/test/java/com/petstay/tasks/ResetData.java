package com.petstay.tasks;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;

import static com.petstay.ui.Header.RESET_DATA;

public class ResetData {

    /** Borra las mascotas y reservas guardadas en el navegador y vuelve a Mis mascotas. */
    public static Performable ofTheHotel() {
        return Task.where("{0} reinicia los datos del hotel", Click.on(RESET_DATA));
    }
}
