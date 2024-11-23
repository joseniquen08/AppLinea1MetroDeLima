package com.dardigamp.aplicativocronovoid.model;

import com.google.gson.annotations.Expose;

public class Usuario {
    private String username;
    private String contrasena;
    private Tarjeta tarjeta;
    private Persona persona;

    // Getters y setters
    public Usuario(String username, String contrasena, Tarjeta tarjeta, Persona persona) {
        this.username = username;
        this.contrasena = contrasena;
        this.tarjeta = tarjeta;
        this.persona = persona;
    }

    public Usuario() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Tarjeta getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(Tarjeta tarjeta) {
        this.tarjeta = tarjeta;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }
}