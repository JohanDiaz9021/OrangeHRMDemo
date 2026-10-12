package com.petstay.stepdefinitions;

import com.petstay.models.HotelUser;
import com.petstay.questions.TheMessage;
import com.petstay.questions.ThePetList;
import com.petstay.tasks.Login;
import com.petstay.tasks.RegisterPet;
import com.petstay.tasks.ResetData;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.screenplay.ensure.Ensure;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class PetStepDefinitions {

    @Dado("que inicié sesión como {string}")
    public void isLoggedInAs(String username) {
        theActorInTheSpotlight().attemptsTo(Login.as(HotelUser.named(username)));
    }

    @Dado("no tengo mascotas registradas")
    public void hasNoPets() {
        theActorInTheSpotlight().attemptsTo(
                ResetData.ofTheHotel(),
                Ensure.that(ThePetList.isEmpty()).isTrue()
        );
    }

    @Dado("que tengo registrada la mascota {string}")
    public void hasThePet(String name) {
        // Los demás datos son válidos a propósito: el escenario solo depende del nombre
        theActorInTheSpotlight().attemptsTo(
                RegisterPet.with(name, "Perro", "8", "2", true),
                Ensure.that(ThePetList.names()).contains(name)
        );
    }

    @Cuando("registro una mascota {string}, {especie}, {word} kg, {word} años, vacunas {string}")
    public void registersAPet(String name, String species, String weight, String age, String vaccines) {
        boolean vaccinated = vaccines.equalsIgnoreCase("sí") || vaccines.equalsIgnoreCase("si");
        theActorInTheSpotlight().attemptsTo(RegisterPet.with(name, species, weight, age, vaccinated));
    }

    @Entonces("{string} no aparece en mi lista de mascotas")
    public void petIsNotListed(String name) {
        theActorInTheSpotlight().attemptsTo(Ensure.that(ThePetList.names()).doesNotContain(name));
    }

    @Entonces("la lista muestra {string}, {string}, {string}, {string} y tamaño {string}")
    public void listShows(String name, String species, String weight, String age, String size) {
        theActorInTheSpotlight().attemptsTo(
                Ensure.that(ThePetList.detailsOf(name)).containsExactly(name, species, weight, age, size)
        );
    }

    @Entonces("el registro es rechazado")
    public void registrationIsRejected() {
        theActorInTheSpotlight().attemptsTo(Ensure.that(TheMessage.isAnError()).isTrue());
    }

    @Entonces("la lista de mascotas sigue vacía")
    public void petListIsStillEmpty() {
        theActorInTheSpotlight().attemptsTo(Ensure.that(ThePetList.isEmpty()).isTrue());
    }

    @Entonces("la lista tiene una sola mascota llamada {string}")
    public void listHasOnlyOnePetNamed(String name) {
        theActorInTheSpotlight().attemptsTo(Ensure.that(ThePetList.namesLike(name)).containsExactly(name));
    }
}
