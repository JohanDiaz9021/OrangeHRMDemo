package com.petstay.ui;

import net.serenitybdd.screenplay.targets.Target;

public class PetsPage {

    public static final Target NAME = Target.the("campo nombre").locatedBy("#nombreMascota");
    public static final Target SPECIES = Target.the("especie {0}").locatedBy("[name='especie'][value='{0}']");
    public static final Target WEIGHT = Target.the("campo peso").locatedBy("[name='peso']");
    public static final Target AGE = Target.the("campo edad").locatedBy("[data-testid='edad']");
    public static final Target VACCINES = Target.the("casilla vacunas al día").locatedBy("[name='vacunas']");
    public static final Target REGISTER_BUTTON = Target.the("botón registrar mascota").locatedBy("#form-mascota button[type='submit']");

    public static final Target PET_NAMES = Target.the("nombres de la lista de mascotas").locatedBy("#tabla-mascotas tbody td.nombre");
    public static final Target PET_ROW_CELLS = Target.the("datos de la mascota {0}")
            .locatedBy("//table[@id='tabla-mascotas']//tr[td[@class='nombre'][normalize-space()='{0}']]/td");
    public static final Target EMPTY_LIST = Target.the("aviso de lista vacía").locatedBy(".panel-lista .lista-vacia");
}
