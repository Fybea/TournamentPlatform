package com.platform.TournamentPlatform.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tournament")
@Getter
@Setter
@NoArgsConstructor
public class Tournament {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "tournament_name")
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 3, max = 30, message = "Name should be between 3 and 30 characters")
    private String tournamentName;

    @Column(name = "prize_pool")
    private int prizePool;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "start_date")
    private LocalDate tournamentStartDate;

    @Column(name = "end_date")
    private LocalDate tournamentEndDate;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "tournament")
    private List<Team> teams;

}
