package com.petstay.models;

import java.util.Arrays;

/** Usuarios de práctica de PetStay (datos tomados de la historia de usuario HU-02). */
public enum HotelUser {

    DUENO("dueno", "huellitas123");

    private final String username;
    private final String password;

    HotelUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String username() {
        return username;
    }

    public String password() {
        return password;
    }

    public static HotelUser named(String username) {
        return Arrays.stream(values())
                .filter(user -> user.username.equals(username))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Usuario de prueba desconocido: " + username));
    }
}
