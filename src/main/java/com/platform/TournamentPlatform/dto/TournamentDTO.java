package com.platform.TournamentPlatform.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TournamentDTO {

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 3, max = 30, message = "Name should be between 3 and 30 characters")
    private String tournamentName;

    private int prizePool;

    private int capacity;

    //json input should be like this format: "2024-01-21" - "YYYY-MM-dd"
    private LocalDate tournamentStartDate;

    //json input should be like this format: "2024-01-21" - "YYYY-MM-dd"
    private LocalDate tournamentEndDate;

}
