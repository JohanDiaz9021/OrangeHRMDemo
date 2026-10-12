package com.petstay.tasks;

import com.petstay.interactions.WaitForLoading;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.SetCheckbox;

import static com.petstay.ui.PetsPage.AGE;
import static com.petstay.ui.PetsPage.NAME;
import static com.petstay.ui.PetsPage.REGISTER_BUTTON;
import static com.petstay.ui.PetsPage.SPECIES;
import static com.petstay.ui.PetsPage.VACCINES;
import static com.petstay.ui.PetsPage.WEIGHT;

public class RegisterPet {

    /**
     * Peso y edad se reciben como texto para escribirlos tal cual vienen del Gherkin
     * (por ejemplo "10,01"), igual que lo haría el usuario.
     */
    public static Performable with(String name, String species, String weight, String age, boolean vaccinated) {
        return Task.where("{0} registra a la mascota " + name,
                Enter.theValue(name).into(NAME),
                Click.on(SPECIES.of(species)),
                Enter.theValue(weight).into(WEIGHT),
                Enter.theValue(age).into(AGE),
                vaccinated ? SetCheckbox.of(VACCINES).toTrue() : SetCheckbox.of(VACCINES).toFalse(),
                Click.on(REGISTER_BUTTON),
                WaitForLoading.toFinish()
        );
    }
}
