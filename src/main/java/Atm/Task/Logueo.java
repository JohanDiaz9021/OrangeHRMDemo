package Atm.Task;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.SendKeys;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static Atm.PO.PaginaLogin.BtonLogin;
import static Atm.PO.PaginaLogin.contrasena;
import static Atm.PO.PaginaLogin.forgotPass;
import static Atm.PO.PaginaLogin.usuario;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class Logueo implements Task {

    private final String usu;
    private final String contras;

    public Logueo(String usu, String contras) {
        this.usu = usu;
        this.contras = contras;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(usuario, isVisible()).forNoMoreThan(60).seconds().then(SendKeys.of(usu).into(usuario)),
                WaitUntil.the(contrasena, isVisible()).forNoMoreThan(60).seconds().then(SendKeys.of(contras).into(contrasena)),
                Check.whether(BtonLogin.resolveFor(actor).isVisible()).andIfSo(Click.on(BtonLogin)).otherwise(Click.on(forgotPass))
        );
        System.out.println("Se realiza login");
    }

    public static Logueo go(String usuario, String pass) {
        return instrumented(Logueo.class, usuario, pass);
    }
}
