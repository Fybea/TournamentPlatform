package com.platform.TournamentPlatform.Configuration;

import com.platform.TournamentPlatform.services.PlayerDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final String[] endpoints = {"/api/v1/player", "/api/v1/player/registration", "/api/v1/player/{id}",
            "/api/v1/team", "/api/v1/team/create/player/{playerId}", "/api/v1/team/invite/{teamId}",
            "/api/v1/team/{id}", "/api/v1/team/{playerId}", "/api/v1/tournament", "/api/v1/tournament/create",
            "/api/v1/tournament/registration/{tournamentId}", "/api/v1/tournament/{id}",
            "/api/v1/tournament/{tournamentId}", "/api/v1/tournament/participants",
            "/api/v1/tournament/{tournamentId}/teams"};

    private final PlayerDetailsService playerDetailsService;

    @Autowired
    public SecurityConfig(PlayerDetailsService playerDetailsService) {
        this.playerDetailsService = playerDetailsService;
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(playerDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
       return http
               .csrf().disable()
               .authorizeHttpRequests((auth) -> auth
//                        .requestMatchers( "/api/v1/player/registration", "/api/v1/player/{id}").permitAll()
//                        .requestMatchers(endpoints).hasRole("ADMIN")
//                        .requestMatchers("/api/v1/team/{playerId}").hasRole("CAPTAIN")
                        .anyRequest().hasAnyRole("USER", "ADMIN")).build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class).build();
    }


}
