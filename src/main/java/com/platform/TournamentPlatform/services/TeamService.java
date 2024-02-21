package com.platform.TournamentPlatform.services;

import com.platform.TournamentPlatform.exception.NotFoundException;
import com.platform.TournamentPlatform.exception.TeamException;
import com.platform.TournamentPlatform.model.Player;
import com.platform.TournamentPlatform.model.Team;
import com.platform.TournamentPlatform.repositories.TeamRepository;
import com.platform.TournamentPlatform.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class TeamService {

    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository, PlayerRepository playerRepository) {
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
    }

    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    public Team findById(int id) {
        return teamRepository.findById(id).orElseThrow(() -> new NotFoundException("Team not found with id: " + id));
    }

    public Optional<Team> findByName(String name) {
        return teamRepository.findByName(name);
    }

    @Transactional
    public void save(Team team, int userId) {
        Player owner = playerRepository.findById(userId).orElseThrow(() -> new NotFoundException("User with this id: " + userId + ", not found"));
        if (owner.getTeam() != null) {
            throw new TeamException("User with this id: " + userId + ", already has a team");
        }
        owner.setTeam(team);
        team.setLocalDateTime(LocalDateTime.now());
        team.setCaptainUsername(owner.getUsername());
        teamRepository.save(team);
    }

    @Transactional
    public void joinToTeam(int teamId, String username) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new NotFoundException("Team with this id: " + teamId + ", not found"));
        Player player = playerRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User with this name: " + username + ", not found"));
        player.setTeam(team);
        playerRepository.save(player);
    }

    @Transactional
    public void removePlayerFromTeamByUsername(String username) {
        Player player = playerRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User with this name: " + username + ", not found"));
        player.setTeam(null);
        playerRepository.save(player);
    }


}
