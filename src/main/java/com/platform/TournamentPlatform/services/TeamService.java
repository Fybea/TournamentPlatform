package com.platform.TournamentPlatform.services;

import com.platform.TournamentPlatform.model.Team;
import com.platform.TournamentPlatform.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class TeamService {

    private final TeamRepository teamRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    public Team findById(int id) {
        Optional<Team> team = teamRepository.findById(id);
        return team.orElse(null);
    }

    public Optional<Team> findByName(String name) {
        return teamRepository.findByName(name);
    }

    @Transactional
    public void save(Team team) {
        teamRepository.save(team);
    }
}
