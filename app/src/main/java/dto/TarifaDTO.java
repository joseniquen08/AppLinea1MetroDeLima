package dto;

import com.google.gson.annotations.SerializedName;

public class TarifaDTO {
    @SerializedName("id")
    private int id;

    @SerializedName("tipo")
    private String tipo;

    @SerializedName("descripcion")
    private String descripcion;

    @SerializedName("monto")
    private double monto;

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }
}