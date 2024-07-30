package com.platform.TournamentPlatform.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tournament")
@Getter
@Setter
@NoArgsConstructor
public class Tournament {

    @Id
    @Column(name = "tournament_id")
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

    @Enumerated(EnumType.STRING)
    private TournamentStatus status;

    @Column(name = "participants")
    private int participants;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "start_date")
    private LocalDateTime tournamentStartDate;

    @Column(name = "end_date")
    private LocalDateTime tournamentEndDate;
}
