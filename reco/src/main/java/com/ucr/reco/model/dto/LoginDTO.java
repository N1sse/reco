package com.ucr.reco.model.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginDTO {
    @Email(message = "Correo incorrecto")
    @NotBlank(message = "No se puede dejar el espacio del correo en blanco")
    private String email;

    @NotBlank(message = "No se puede dejar el espacio de la contraseña en blanco") //no en blanco
    private String password;

    public LoginDTO() {
    }

    public LoginDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

