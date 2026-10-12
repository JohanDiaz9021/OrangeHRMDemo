package com.petstay.tasks;

import com.petstay.interactions.WaitForLoading;
import com.petstay.models.HotelUser;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;

import static com.petstay.ui.LoginPage.LOGIN_BUTTON;
import static com.petstay.ui.LoginPage.PASSWORD;
import static com.petstay.ui.LoginPage.USERNAME;

public class Login {

    public static Performable withCredentials(String username, String password) {
        return Task.where("{0} inicia sesión con el usuario " + username,
                Enter.theValue(username).into(USERNAME),
                Enter.theValue(password).into(PASSWORD),
                Click.on(LOGIN_BUTTON),
                WaitForLoading.toFinish()
        );
    }

    /** Abre el login e ingresa con un usuario de prueba conocido. */
    public static Performable as(HotelUser user) {
        return Task.where("{0} inicia sesión como " + user.username(),
                NavigateTo.theLoginPage(),
                withCredentials(user.username(), user.password())
        );
    }
}
