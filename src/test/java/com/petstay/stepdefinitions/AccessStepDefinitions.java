package com.petstay.stepdefinitions;

import com.petstay.questions.TheMessage;
import com.petstay.tasks.Login;
import com.petstay.tasks.NavigateTo;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.screenplay.ensure.Ensure;

import static com.petstay.ui.LoginPage.LOGIN_BUTTON;
import static com.petstay.ui.PageMessage.PAGE_TITLE;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class AccessStepDefinitions {

    @Dado("que estoy en la pantalla de inicio de sesión")
    public void isOnTheLoginPage() {
        theActorInTheSpotlight().attemptsTo(NavigateTo.theLoginPage());
    }

    @Cuando("ingreso el usuario {string} y la contraseña {string}")
    public void logsIn(String username, String password) {
        theActorInTheSpotlight().attemptsTo(Login.withCredentials(username, password));
    }

    @Entonces("veo la pantalla principal del hotel")
    public void seesTheMainPage() {
        theActorInTheSpotlight().attemptsTo(Ensure.that(PAGE_TITLE).hasText("Mis mascotas"));
    }

    @Entonces("veo el mensaje {string}")
    public void seesTheMessage(String message) {
        theActorInTheSpotlight().attemptsTo(Ensure.that(TheMessage.shown()).isEqualTo(message));
    }

    @Entonces("sigo en la pantalla de inicio de sesión")
    public void staysOnTheLoginPage() {
        theActorInTheSpotlight().attemptsTo(Ensure.that(LOGIN_BUTTON).isDisplayed());
    }
}
