package com.platform.TournamentPlatform.services;

import com.platform.TournamentPlatform.exception.NotFoundException;
import com.platform.TournamentPlatform.model.Roaster;
import com.platform.TournamentPlatform.model.Player;
import com.platform.TournamentPlatform.model.Team;
import com.platform.TournamentPlatform.repositories.RoasterRepository;
import com.platform.TournamentPlatform.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoasterService {

    private final RoasterRepository roasterRepository;
    private final PlayerRepository playerRepository;

    @Autowired
    public RoasterService(RoasterRepository roasterRepository, PlayerRepository playerRepository) {
        this.roasterRepository = roasterRepository;
        this.playerRepository = playerRepository;
    }


    public List<Player> findAllUsersByTeamId(Team team) {
        List<Roaster> roaster = roasterRepository.findAllByTeamId(team);
        List<Player> members = new ArrayList<>();
        for (Roaster r : roaster) {
            members.add(r.getMember());
        }
        return members;
    }

    public Player findByTeamId(int teamId) {
        Roaster roaster = roasterRepository.findById(teamId).orElseThrow(() -> new NotFoundException("Not found"));
        return roaster.getMember();
    }
}
