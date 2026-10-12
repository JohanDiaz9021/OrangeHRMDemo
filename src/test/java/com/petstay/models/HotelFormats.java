package com.petstay.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/** Formatos que usa PetStay en pantalla: fechas DD/MM/AAAA y pesos colombianos ($1.200.000). */
public final class HotelFormats {

    private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private HotelFormats() {
    }

    public static String date(LocalDate date) {
        return date.format(DATE);
    }

    public static String pesos(long amount) {
        String digits = String.format(Locale.ROOT, "%,d", Math.abs(amount)).replace(',', '.');
        return (amount < 0 ? "-$" : "$") + digits;
    }

    /** Convierte "$1.200.000" o "-$48.000" en 1200000 o -48000. */
    public static long amountOf(String pesos) {
        String digits = pesos.replaceAll("[^0-9]", "");
        long amount = digits.isEmpty() ? 0 : Long.parseLong(digits);
        return pesos.trim().startsWith("-") ? -amount : amount;
    }
}
