package com.platform.TournamentPlatform.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "Player")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username")
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 3, max = 50, message = "Name should be between 3 and 50 characters")
    private String username;

    @Column(name = "password")
    private String password;

    @Email
    @NotEmpty(message = "Email should not be empty")
    @Size(min = 10, max = 30, message = "Email should be between 10 and 30 characters")
    private String email;

    @ManyToOne
    private Team team;

}
