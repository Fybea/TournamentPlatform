package com.platform.TournamentPlatform.services;

import com.platform.TournamentPlatform.repositories.PlayerRepository;
import com.platform.TournamentPlatform.security.TournamentPlayerDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PlayerDetailsService implements UserDetailsService {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerDetailsService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return playerRepository.findByUsername(username)
                .map(TournamentPlayerDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
