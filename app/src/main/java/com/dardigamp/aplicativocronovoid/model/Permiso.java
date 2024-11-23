package com.dardigamp.aplicativocronovoid.model;

public class Permiso {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Permiso(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
