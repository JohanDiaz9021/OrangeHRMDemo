package stepdefinitions;

import Atm.Task.Logueo;
import io.cucumber.java.Before;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.questions.Text;

import static Atm.PO.PaginaInicio.tituloDashboard;
import static Atm.Utils.RutasYConstantes.URL_ORANGE;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.Matchers.equalTo;

public class MyStepdefs {

    @Before
    public void prepararEscenario() {
        OnStage.setTheStage(new OnlineCast());
    }

    @Dado("que el usuario abre la pagina de OrangeHRM")
    public void abrirPagina() {
        theActorCalled("Usuario").wasAbleTo(Open.url(URL_ORANGE));
    }

    @Cuando("inicia sesion con usuario {string} y contrasena {string}")
    public void iniciarSesion(String usuario, String contrasena) {
        theActorInTheSpotlight().attemptsTo(Logueo.go(usuario, contrasena));
    }

    @Entonces("deberia ver el Dashboard")
    public void verDashboard() {
        theActorInTheSpotlight().should(seeThat(Text.of(tituloDashboard), equalTo("Dashboard")));
    }
}
