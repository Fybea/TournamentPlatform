package com.platform.TournamentPlatform.repositories;

import com.platform.TournamentPlatform.model.Team;
import com.platform.TournamentPlatform.model.TeamTournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamTournamentRepository extends JpaRepository<TeamTournament, Integer> {

    @Query("SELECT tt.team FROM TeamTournament tt WHERE tt.tournament.id = :tournamentId")
    List<Team> findTeamsByTournamentId(int tournamentId);
}

