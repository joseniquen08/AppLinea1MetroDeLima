package dto;

public class UsuarioCreateDTO {

    private int id;
    private String username;
    private String password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
