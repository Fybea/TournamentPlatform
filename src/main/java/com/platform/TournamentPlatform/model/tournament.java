package com.platform.TournamentPlatform.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "tournament")
public class tournament {

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
    private LocalDateTime tournamentStartDate;

    @Column(name = "end_date")
    private LocalDateTime tournamentEndDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public int getPrizePool() {
        return prizePool;
    }

    public void setPrizePool(int prizePool) {
        this.prizePool = prizePool;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public LocalDateTime getTournamentStartDate() {
        return tournamentStartDate;
    }

    public void setTournamentStartDate(LocalDateTime tournamentStartDate) {
        this.tournamentStartDate = tournamentStartDate;
    }

    public LocalDateTime getTournamentEndDate() {
        return tournamentEndDate;
    }

    public void setTournamentEndDate(LocalDateTime tournamentEndDate) {
        this.tournamentEndDate = tournamentEndDate;
    }
}
