package com.dardigamp.aplicativocronovoid;

public class ModeloRetorno {
    private int id;
    private String hora;

    public ModeloRetorno() {
    }


    public ModeloRetorno(int id, String hora) {
        this.id = id;
        this.hora = hora;
    }

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
        return "ModeloRetorno{" +
                "id=" + id +
                ", hora='" + hora + '\'' +
                '}';
    }
}
