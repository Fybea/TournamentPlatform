package com.platform.TournamentPlatform.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PlayerDTO {


    @NotEmpty(message = "Name should not be empty")
    @Size(min = 3, max = 50, message = "Name should be between 3 and 50 characters")
    private String username;

    @Email(message = "Email address is not valid")
    @NotEmpty(message = "Email should not be empty")
    private String email;

    @NotEmpty(message = "Role should not be empty")
    private String role;

    private String password;
}
