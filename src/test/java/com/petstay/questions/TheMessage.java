package com.petstay.questions;

import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;
import net.serenitybdd.screenplay.questions.CurrentVisibility;

import static com.petstay.ui.PageMessage.MESSAGE;

public class TheMessage {

    public static Question<String> shown() {
        return Question.about("el mensaje de la pantalla")
                .answeredBy(actor -> Text.of(MESSAGE).answeredBy(actor).trim());
    }

    /** PetStay pinta los rechazos con la clase "error" (mensaje rojo). */
    public static Question<Boolean> isAnError() {
        return Question.about("si la pantalla muestra un mensaje de error")
                .answeredBy(actor -> CurrentVisibility.of(MESSAGE).answeredBy(actor)
                        && MESSAGE.resolveFor(actor).getDomAttribute("class").contains("error"));
    }
}
