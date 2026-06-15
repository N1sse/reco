package com.ucr.reco.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UserDTO {
    @NotBlank(message = "El nombre no puede venir en blanco")
    private String name;

    @Email(message = "El correo no es valido")
    @NotBlank(message = "El espacio del correo no puede quedar vacio")
    private  String email;

    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[.,+?!*#$@]).{8,}$",
            message = "La contraseña debe tener mínimo 8 caracteres, una mayúscula, un número y un carácter especial como . , + ? ! * # $."
    )
    private String password;

    @NotBlank(message = "No se puede dejar este espacio Vacío")
    private String role;

    public UserDTO() {
    }

    public UserDTO(String name, String email, String password, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
