package com.petstay.interactions;

import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * PetStay simula demoras aleatorias (0,3 a 1,5 s) mostrando la capa "#capa-cargando".
 * WaitUntil de Serenity agotaba siempre su tiempo máximo con esta capa, así que se pregunta
 * directamente al navegador si la capa ya está oculta (o si no existe en la página nueva).
 */
public class WaitForLoading {

    private static final String LOADING_FINISHED =
            "const capa = document.getElementById('capa-cargando'); return !capa || capa.hidden;";

    public static Performable toFinish() {
        return Interaction.where("{0} espera a que termine de cargar", actor -> {
            WebDriverWait wait = new WebDriverWait(BrowseTheWeb.as(actor).getDriver(), Duration.ofSeconds(15));
            // Mientras el navegador cambia de página el script puede fallar: se reintenta
            wait.ignoring(WebDriverException.class)
                    .until(driver -> Boolean.TRUE.equals(((JavascriptExecutor) driver).executeScript(LOADING_FINISHED)));
        });
    }
}
