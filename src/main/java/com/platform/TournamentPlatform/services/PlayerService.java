package com.platform.TournamentPlatform.services;

import com.platform.TournamentPlatform.exception.NotCreatedException;
import com.platform.TournamentPlatform.exception.NotFoundException;
import com.platform.TournamentPlatform.model.Player;
import com.platform.TournamentPlatform.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PlayerService(PlayerRepository playerRepository, PasswordEncoder passwordEncoder) {
        this.playerRepository = playerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Player> findAll() {
        return playerRepository.findAll();
    }

    public Player findOne(int id) {
        return playerRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found with id: " + id));
    }


    @Transactional
    public void save(Player player) {
        if (playerRepository.findByUsername(player.getUsername()).isPresent()) {
            throw new NotCreatedException("User with this name: " + player.getUsername() + " already exists.");
        }

        if (playerRepository.findByEmail(player.getEmail()).isPresent()) {
            throw new NotCreatedException("User with this email: " + player.getEmail() + " already exists.");
        }
        player.setPassword(passwordEncoder.encode(player.getPassword()));
        playerRepository.save(player);
    }
}
