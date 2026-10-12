package com.petstay.ui;

import net.serenitybdd.screenplay.targets.Target;

/**
 * Elementos comunes a todas las pantallas: el mensaje de resultado y la capa "cargando".
 * Cada pantalla tiene un solo mensaje, por eso un selector agrupado sirve para todas.
 */
public class PageMessage {

    public static final Target MESSAGE = Target.the("mensaje de la pantalla").locatedBy(
            "#error-login, [data-testid='mensaje-mascotas'], [data-testid='mensaje-reserva'], #mensaje-reservas");
    public static final Target LOADING = Target.the("capa cargando").locatedBy("#capa-cargando");
    public static final Target PAGE_TITLE = Target.the("título de la pantalla").locatedBy("main h1");
}
