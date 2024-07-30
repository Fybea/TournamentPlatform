package com.platform.TournamentPlatform.services;

import com.platform.TournamentPlatform.dto.TeamDTO;
import com.platform.TournamentPlatform.exception.NotFoundException;
import com.platform.TournamentPlatform.exception.TournamentException;
import com.platform.TournamentPlatform.model.Team;
import com.platform.TournamentPlatform.model.TeamTournament;
import com.platform.TournamentPlatform.model.Tournament;
import com.platform.TournamentPlatform.repositories.TeamRepository;
import com.platform.TournamentPlatform.repositories.TeamTournamentRepository;
import com.platform.TournamentPlatform.repositories.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TeamTournamentService {

    private final TeamTournamentRepository teamTournamentRepository;
    private final TeamRepository teamRepository;
    private final TournamentRepository tournamentRepository;

    @Autowired
    public TeamTournamentService(TeamTournamentRepository teamTournamentRepository,
                                 TeamRepository teamRepository,
                                 TournamentRepository tournamentRepository) {
        this.teamTournamentRepository = teamTournamentRepository;
        this.teamRepository = teamRepository;
        this.tournamentRepository = tournamentRepository;
    }


    public List<TeamTournament> findAll() {
        return teamTournamentRepository.findAll();
    }

    public TeamTournament findById(int id) {
        return teamTournamentRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found"));
    }


    @Transactional
    public void save(int tournamentId, int teamId) {
        TeamTournament teamTournament = new TeamTournament();

        Tournament tournament = tournamentRepository.findById(tournamentId).orElseThrow(() ->
                new NotFoundException("Tournament with this id: " + tournamentId + " not found"));

        Team team = teamRepository.findById(teamId).orElseThrow(() ->
                new NotFoundException("Team with this id: " + teamId + " not found"));

        List<Team> teams = findAllByTournamentId(tournamentId);

        for (Team t : teams) {
            if (t.getId() == teamId) {
                            throw new TournamentException("This team: " + team.getName() +
                    " already participating in this tournament: " + tournament.getTournamentName());
            }
        }

        if (tournament.getCapacity() == tournament.getParticipants()) {
            throw new TournamentException("Tournament is full");
        }

        tournament.setParticipants(tournament.getParticipants() + 1);
        teamTournament.setTournament(tournament);
        teamTournament.setTeam(team);

        teamTournamentRepository.save(teamTournament);
    }

    public int getParticipantsNumber(int tournamentId) {
        Tournament tournament = tournamentRepository.findById(tournamentId).orElseThrow(() ->
                new NotFoundException("Tournament with this id: " + tournamentId + " not found"));

        return teamTournamentRepository.findTeamsByTournamentId(tournament.getId()).size();
    }

    public List<Team> findAllByTournamentId(int tournamentId) {
        List<Team> teams = teamTournamentRepository.findTeamsByTournamentId(tournamentId);

//        if (teams.isEmpty()) {
//            throw new NotFoundException("Any team not found");
//        }
        return teamTournamentRepository.findTeamsByTournamentId(tournamentId);
    }
}
