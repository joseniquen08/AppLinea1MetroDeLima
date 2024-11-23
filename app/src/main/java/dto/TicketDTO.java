package dto;

import java.time.LocalDateTime;
import java.util.Date;

public class TicketDTO {
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

    private int id;
    private LocalDateTime fecha;
    private double saldoanterior;
    private double importe;
    private double nuevosaldo;
}