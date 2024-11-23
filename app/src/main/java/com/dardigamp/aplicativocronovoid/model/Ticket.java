package com.dardigamp.aplicativocronovoid.model;
import java.time.LocalDateTime;
import java.util.Date;
public class Ticket {
    private int id;
    private LocalDateTime  fecha;
    private double saldoanterior;
    private double importe;
    private double nuevosaldo;
    private Tarjeta tarjeta;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime  getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime  fecha) {
        this.fecha = fecha;
    }

    public double getSaldoanterior() {
        return saldoanterior;
    }

    public void setSaldoanterior(double saldoanterior) {
        this.saldoanterior = saldoanterior;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public double getNuevosaldo() {
        return nuevosaldo;
    }

    public void setNuevosaldo(double nuevosaldo) {
        this.nuevosaldo = nuevosaldo;
    }

    public Tarjeta getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(Tarjeta tarjeta) {
        this.tarjeta = tarjeta;
    }

    public Ticket(int id, LocalDateTime fecha, double saldoanterior, double importe, double nuevosaldo, Tarjeta tarjeta) {
        this.id = id;
        this.fecha = fecha;
        this.saldoanterior = saldoanterior;
        this.importe = importe;
        this.nuevosaldo = nuevosaldo;
        this.tarjeta = tarjeta;
    }

}