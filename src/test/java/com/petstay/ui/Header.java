package com.petstay.ui;

import net.serenitybdd.screenplay.targets.Target;

public class Header {

    public static final Target MY_PETS = Target.the("menú Mis mascotas").locatedBy("nav.menu a[href='mascotas.html']");
    public static final Target BOOK = Target.the("menú Reservar").locatedBy("nav.menu a[href='reservar.html']");
    public static final Target MY_BOOKINGS = Target.the("menú Mis reservas").locatedBy("#enlace-reservas");
    public static final Target RESET_DATA = Target.the("botón reiniciar datos").locatedBy("[data-testid='reiniciar-datos']");
}
