package dto;

import com.google.gson.annotations.SerializedName;

public class TarjetaDTO {

    @SerializedName("id")
    private int id;

    @SerializedName("saldo")
    private double saldo;

    @SerializedName("nrotarjeta")
    private int nrotarjeta;

    @SerializedName("tarifaDTO")
    private TarifaDTO tarifaDTO;

    // Constructor vacío necesario para Retrofit
    public TarjetaDTO() {}

    // Constructor adicional (si realmente lo necesitas)
    public TarjetaDTO(int nrotarjeta) {
        this.nrotarjeta = nrotarjeta;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public int getNrotarjeta() {
        return nrotarjeta;
    }

    public void setNrotarjeta(int nrotarjeta) {
        this.nrotarjeta = nrotarjeta;
    }

    public TarifaDTO getTarifaDTO() {
        return tarifaDTO;
    }

    public void setTarifaDTO(TarifaDTO tarifaDTO) {
        this.tarifaDTO = tarifaDTO;
    }
}
