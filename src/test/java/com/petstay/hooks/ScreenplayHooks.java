package com.petstay.hooks;

import io.cucumber.java.Before;
import io.cucumber.java.ParameterType;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;

import java.time.LocalDate;

public class ScreenplayHooks {

    @Before
    public void setTheStage() {
        // Cada escenario recibe un navegador nuevo, así que empieza sin mascotas ni reservas
        OnStage.setTheStage(new OnlineCast());
        // El Gherkin está en primera persona: este actor es el "yo" de los escenarios
        OnStage.theActorCalled("Dueño");
    }

    /** "+5" es hoy más 5 días; "-1" es ayer. Así los escenarios no dependen de una fecha fija. */
    @ParameterType("[+-]?\\d+")
    public LocalDate dia(String daysFromToday) {
        return LocalDate.now().plusDays(Integer.parseInt(daysFromToday));
    }

    /** "perro" o "gato" en el Gherkin, "Perro" o "Gato" en la aplicación. */
    @ParameterType("perro|gato")
    public String especie(String species) {
        return species.substring(0, 1).toUpperCase() + species.substring(1);
    }
}
