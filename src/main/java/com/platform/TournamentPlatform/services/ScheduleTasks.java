package com.platform.TournamentPlatform.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduleTasks {
    private final TournamentService tournamentService;
    @Autowired
    public ScheduleTasks(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @Scheduled(cron = "00 * * * * *")
    public void updateTournamentStatusHourly() {
        tournamentService.updateTournamentStatus();
    }

}
