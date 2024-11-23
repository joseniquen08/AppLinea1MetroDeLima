package com.dardigamp.aplicativocronovoid.model;

import com.google.gson.annotations.Expose;

public class Tarjeta {
    private int nrotarjeta;

    // Constructor
    public Tarjeta(int nrotarjeta) {
        this.nrotarjeta = nrotarjeta;
    }

    public int getNrotarjeta() {
        return nrotarjeta;
    }

    public void setNrotarjeta(int nrotarjeta) {
        this.nrotarjeta = nrotarjeta;
    }
}