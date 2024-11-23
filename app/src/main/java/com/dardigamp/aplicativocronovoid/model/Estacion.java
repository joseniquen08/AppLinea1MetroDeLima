package com.dardigamp.aplicativocronovoid.model;
import java.util.List;
public class Estacion {
    private int id;
    private String nombre;
    private List<Horario> horarios;
    private List<Usuario> usuarios;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Horario> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<Horario> horarios) {
        this.horarios = horarios;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public Estacion(int id, String nombre, List<Horario> horarios, List<Usuario> usuarios) {
        this.id = id;
        this.nombre = nombre;
        this.horarios = horarios;
        this.usuarios = usuarios;
    }
}
