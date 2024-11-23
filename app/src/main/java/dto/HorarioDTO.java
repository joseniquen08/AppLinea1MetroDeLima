package dto;

import java.util.Date;

public class HorarioDTO {
    private int id;
    private String hora; // Mantener como String para coincidir con el formato JSON

    // Constructor vacío
    public HorarioDTO() {
    }

    // Constructor con parámetros
    public HorarioDTO(int id, String hora) {
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