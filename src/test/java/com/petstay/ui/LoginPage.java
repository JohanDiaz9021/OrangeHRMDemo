package com.petstay.ui;

import net.serenitybdd.screenplay.targets.Target;

public class LoginPage {

    public static final Target USERNAME = Target.the("campo usuario").locatedBy("[name='usuario']");
    public static final Target PASSWORD = Target.the("campo contraseña").locatedBy("#pass-01");
    public static final Target LOGIN_BUTTON = Target.the("botón ingresar").locatedBy("[data-testid='btn-ingresar']");
}
