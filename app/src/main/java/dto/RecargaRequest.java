package dto;

public class RecargaRequest {
    private double monto;
    private int iduser;
    private int idtarjeta;

    public RecargaRequest(double monto, int iduser, int idtarjeta) {
        this.monto = monto;
        this.iduser = iduser;
        this.idtarjeta = idtarjeta;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public int getIdtarjeta() {
        return idtarjeta;
    }

    public void setIdtarjeta(int idtarjeta) {
        this.idtarjeta = idtarjeta;
    }
}
