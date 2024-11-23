package dto;

public class UsuarioDTO {
    private int id;
    private String username;
    private TarjetaDTO tarjetaDTO;
    private PersonaDTO personaDTO;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public TarjetaDTO getTarjetaDTO() {
        return tarjetaDTO;
    }

    public void setTarjetaDTO(TarjetaDTO tarjetaDTO) {
        this.tarjetaDTO = tarjetaDTO;
    }

    public PersonaDTO getPersonaDTO() {
        return personaDTO;
    }

    public void setPersonaDTO(PersonaDTO personaDTO) {
        this.personaDTO = personaDTO;
    }
}
