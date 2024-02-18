package com.platform.TournamentPlatform.services;

import com.platform.TournamentPlatform.model.Tournament;
import com.platform.TournamentPlatform.repositories.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class TournamentService {

    private final TournamentRepository tournamentRepository;

    @Autowired
    public TournamentService(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    public List<Tournament> findAll() {
        return tournamentRepository.findAll();
    }

    public Tournament findById(int id) {
        Optional<Tournament> tournament = tournamentRepository.findById(id);
        return tournament.orElse(null);
    }

    @Transactional
    public void save(Tournament tournament) {
        tournamentRepository.save(tournament);
    }
}