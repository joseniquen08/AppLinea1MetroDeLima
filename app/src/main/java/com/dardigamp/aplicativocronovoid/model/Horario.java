package com.dardigamp.aplicativocronovoid.model;

public class Horario {
    private int id;
    private String hora; // Mantener como String para coincidir con el formato JSON

    // Constructor vacío
    public Horario() {
    }

    // Constructor con parámetros
    public Horario(int id, String hora) {
        this.id = id;
        this.hora = hora;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    @Override
    public String toString() {
        return "Horario{" +
                "id=" + id +
                ", hora='" + hora + '\'' +
                '}';
    }
}