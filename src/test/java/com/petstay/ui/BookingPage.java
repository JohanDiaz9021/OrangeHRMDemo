package com.petstay.ui;

import net.serenitybdd.screenplay.targets.Target;

public class BookingPage {

    public static final Target PET = Target.the("lista de mascotas").locatedBy("[name='mascota']");
    public static final Target PET_OPTION = Target.the("opción de la mascota {0}")
            .locatedBy("//select[@name='mascota']/option[starts-with(normalize-space(), '{0} (')]");
    public static final Target CHECK_IN = Target.the("fecha de entrada").locatedBy("#fecha-entrada");
    public static final Target CHECK_OUT = Target.the("fecha de salida").locatedBy("[data-testid='fecha-salida']");
    public static final Target SERVICE = Target.the("servicio {0}")
            .locatedBy("//label[contains(@class,'opcion-check')][contains(normalize-space(), '{0}')]/input");
    public static final Target CALCULATE_BUTTON = Target.the("botón calcular total").locatedBy("#btn-calcular");

    public static final Target SUMMARY = Target.the("resumen de la reserva").locatedBy(".panel-resumen");
    public static final Target NIGHTS = Target.the("noches").locatedBy("[data-testid='res-noches']");
    public static final Target ACCOMMODATION = Target.the("valor del alojamiento").locatedBy("#res-alojamiento");
    public static final Target DISCOUNT_ROW = Target.the("fila del descuento").locatedBy(".panel-resumen .fila-descuento");
    public static final Target DISCOUNT = Target.the("descuento").locatedBy("[data-testid='res-descuento']");
    public static final Target BATH_ROW = Target.the("fila baño y peluquería").locatedBy(".panel-resumen .fila-bano");
    public static final Target BATH = Target.the("valor baño y peluquería").locatedBy(".res-bano");
    public static final Target WALK_ROW = Target.the("fila paseo diario").locatedBy(".panel-resumen .fila-paseo");
    public static final Target WALK = Target.the("valor paseo diario").locatedBy("#res-paseo");
    public static final Target TOTAL = Target.the("total").locatedBy("[data-testid='res-total']");
    public static final Target CONFIRM_BUTTON = Target.the("botón confirmar reserva").locatedBy(".panel-resumen .btn-primario");

    public static final Target BOOKING_CODE = Target.the("código de la reserva").locatedBy("[data-testid='codigo-reserva']");
}
