package com.platform.TournamentPlatform.repositories;

import com.platform.TournamentPlatform.model.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Integer> {

    Optional<Tournament> findByTournamentName(String tournamentName);

    List<Tournament> findByTournamentStartDate(LocalDateTime time);
}
