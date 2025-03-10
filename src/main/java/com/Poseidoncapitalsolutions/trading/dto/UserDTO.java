package com.poseidoncapitalsolutions.trading.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserDTO {

    private int id;

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Full name is required")
    private String fullname;

    @NotBlank(message = "Le mot de passe est requis")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[^a-zA-Z0-9])(?=\\S+$).{8,}$", message = "Le mot de passe doit contenir au moins 8 caractères, une lettre majuscule, un chiffre et un symbole")
    private String password;

    @NotBlank(message = "Role is required")
    @Pattern(regexp = "^(USER|ADMIN)$", message = "Role must be either 'USER' or 'ADMIN'")
    private String role;
}
