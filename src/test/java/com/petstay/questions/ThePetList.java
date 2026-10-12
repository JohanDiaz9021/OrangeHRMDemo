package com.petstay.questions;

import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;
import net.serenitybdd.screenplay.questions.CurrentVisibility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.petstay.ui.PetsPage.EMPTY_LIST;
import static com.petstay.ui.PetsPage.PET_NAMES;
import static com.petstay.ui.PetsPage.PET_ROW_CELLS;

public class ThePetList {

    private static final int NAME = 0;
    private static final int SPECIES = 1;
    private static final int WEIGHT = 2;
    private static final int AGE = 3;
    private static final int SIZE = 4;

    public static Question<Collection<String>> names() {
        return Question.about("los nombres de la lista de mascotas")
                .answeredBy(actor -> new ArrayList<>(Text.ofEach(PET_NAMES).answeredBy(actor)));
    }

    /** Nombres de la lista que coinciden con el dado sin distinguir mayúsculas. */
    public static Question<Collection<String>> namesLike(String name) {
        return Question.about("las mascotas llamadas " + name + " (sin distinguir mayúsculas)")
                .answeredBy(actor -> names().answeredBy(actor).stream()
                        .filter(listed -> listed.equalsIgnoreCase(name))
                        .collect(Collectors.toList()));
    }

    /**
     * Nombre, especie, peso, edad y tamaño de la mascota, sin unidades:
     * la pantalla muestra "10,01 kg" y "5 años", y se devuelve "10,01" y "5".
     */
    public static Question<Collection<String>> detailsOf(String name) {
        return Question.about("los datos de " + name + " en la lista de mascotas")
                .answeredBy(actor -> {
                    List<String> cells = new ArrayList<>(Text.ofEach(PET_ROW_CELLS.of(name)).answeredBy(actor));
                    if (cells.size() <= SIZE) {
                        return cells;
                    }
                    return List.of(
                            cells.get(NAME),
                            cells.get(SPECIES),
                            cells.get(WEIGHT).replace(" kg", ""),
                            cells.get(AGE).replaceAll(" años?$", ""),
                            cells.get(SIZE));
                });
    }

    public static Question<String> sizeOf(String name) {
        return Question.about("el tamaño de " + name)
                .answeredBy(actor -> {
                    List<String> cells = new ArrayList<>(Text.ofEach(PET_ROW_CELLS.of(name)).answeredBy(actor));
                    return cells.size() > SIZE ? cells.get(SIZE) : "(no está en la lista)";
                });
    }

    public static Question<Boolean> isEmpty() {
        return Question.about("si la lista de mascotas está vacía")
                .answeredBy(actor -> CurrentVisibility.of(EMPTY_LIST).answeredBy(actor));
    }
}
