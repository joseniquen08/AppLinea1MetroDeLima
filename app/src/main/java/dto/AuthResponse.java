package dto;

import com.google.gson.annotations.SerializedName;

public class AuthResponse {

    @SerializedName("iduser") // Mapea el campo JSON iduser
    private int id;

    @SerializedName("username")
    private String username;

    @SerializedName("message")
    private String message;

    @SerializedName("jwt")
    private String jwt;

    @SerializedName("status")
    private boolean status;

    public AuthResponse(int id, String username, String message, String jwt, boolean status) {
        this.id = id;
        this.username = username;
        this.message = message;
        this.jwt = jwt;
        this.status = status;
    }

    // Getters y setters
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}