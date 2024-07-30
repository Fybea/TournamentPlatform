package com.platform.TournamentPlatform.services;

import com.platform.TournamentPlatform.exception.NotFoundException;
import com.platform.TournamentPlatform.exception.TournamentException;
import com.platform.TournamentPlatform.model.Tournament;
import com.platform.TournamentPlatform.model.TournamentStatus;
import com.platform.TournamentPlatform.repositories.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
        return tournamentRepository.findById(id).orElseThrow(() -> new NotFoundException("Tournament not found with id: " + id));
    }

    @Transactional
    public void save(Tournament tournament) {
        if (tournamentRepository.findByTournamentName(tournament.getTournamentName()).isPresent()) {
            throw new TournamentException("Tournament with this name: " + tournament.getTournamentName()
                    + " already exists");
        }
        tournament.setCreatedAt(LocalDateTime.now());
        tournament.setStatus(TournamentStatus.UPCOMING);
        tournamentRepository.save(tournament);
    }

    @Transactional
    public void cancelTournament(int tournamentId) {
        Tournament tournament = tournamentRepository.findById(tournamentId).orElseThrow(() ->
                new NotFoundException("Tournament with this id: " + tournamentId + " not found"));

        tournament.setStatus(TournamentStatus.CANCELLED);
    }

    @Transactional
    public void updateTournamentStatus() {
        LocalDateTime currentDateTime = LocalDateTime.now().withSecond(0).withNano(0);
//        currentDateTime = currentDateTime.withSecond(0).withNano(0);

        List<Tournament> tournaments = tournamentRepository.findByTournamentStartDate(currentDateTime);

        for (Tournament t : tournaments) {
            /* TODO: Добавить в БД колонку max_participants и если участников меньше в два раза чем максимально
                допустимое количество участников то ставить статус турнира CANCELED  */
//            if (t.getParticipants() < (t.getMaxParticipants / 2))
            t.setStatus(TournamentStatus.ACTIVE);
            tournamentRepository.save(t);
        }
    }
}
