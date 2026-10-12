package com.petstay.ui;

import net.serenitybdd.screenplay.targets.Target;

public class MyBookingsPage {

    private static final String ROW = "//table[@data-testid='tabla-reservas']//tr[td[@class='codigo'][normalize-space()='{0}']]";

    public static final Target ROW_CELLS = Target.the("datos de la reserva {0}").locatedBy(ROW + "/td[position() < 8]");
    public static final Target STATUS = Target.the("estado de la reserva {0}").locatedBy(ROW + "//span[contains(@class,'estado')]");
    public static final Target REFUND = Target.the("reembolso de la reserva {0}").locatedBy(ROW + "/td[@class='reembolso']");
    public static final Target CANCEL_BUTTON = Target.the("botón cancelar de la reserva {0}").locatedBy(".btn-cancelar[data-codigo='{0}']");
    public static final Target PET_STATUSES = Target.the("estados de las reservas de {0}")
            .locatedBy("//table[@data-testid='tabla-reservas']//tr[td[2][normalize-space()='{0}']]//span[contains(@class,'estado')]");

    public static final Target CONFIRM_CANCELLATION = Target.the("botón sí, cancelar").locatedBy("#confirmar-cancelacion");
    public static final Target KEEP_BOOKING = Target.the("botón no, volver").locatedBy(".modal [data-accion='volver']");
}
