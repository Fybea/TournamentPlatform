package com.platform.TournamentPlatform.services;

import com.platform.TournamentPlatform.exception.NotFoundException;
import com.platform.TournamentPlatform.model.Player;
import com.platform.TournamentPlatform.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class PlayerService {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> findAll() {
        return playerRepository.findAll();
    }

    public Player findOne(int id) {
        return playerRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found with id: " + id));
    }

    @Transactional
    public void save(Player player) {
        player.setLocalDateTime(LocalDateTime.now());
        playerRepository.save(player);
    }
}
