package com.platform.TournamentPlatform.controllers;

import com.platform.TournamentPlatform.dto.PlayerDTO;
import com.platform.TournamentPlatform.dto.TeamDTO;
import com.platform.TournamentPlatform.model.Team;
import com.platform.TournamentPlatform.services.TeamService;
import com.platform.TournamentPlatform.util.TeamValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/team")
public class TeamController {

    private final ModelMapper modelMapper;
    private final TeamService teamService;


    @Autowired
    public TeamController(ModelMapper modelMapper, TeamService teamService) {
        this.modelMapper = modelMapper;
        this.teamService = teamService;
    }


    @GetMapping
    public List<TeamDTO> getTeams() {
        return teamService.findAll().stream().map(this::convertToTeamDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public TeamDTO getTeam(@PathVariable int id) {
        return convertToTeamDTO(teamService.findById(id));
    }


    @PostMapping("/create/player/{playerId}")
    private ResponseEntity<HttpStatus> create(@RequestBody @Valid TeamDTO teamDTO, @PathVariable("playerId") int playerId) {

        teamService.save(convertToTeam(teamDTO), playerId);
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @PutMapping("/invite/{teamId}")
    private ResponseEntity<HttpStatus> invite(@RequestBody PlayerDTO playerDTO,
                                              @PathVariable("teamId") int teamId) {
        teamService.joinToTeam(teamId, playerDTO.getUsername());
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @DeleteMapping("/{playerId}")
    private ResponseEntity<HttpStatus> removePlayer(@PathVariable("playerId") int playerId) {
        teamService.removePlayer(playerId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private Team convertToTeam(TeamDTO teamDTO) {
        return modelMapper.map(teamDTO, Team.class);
    }

    private TeamDTO convertToTeamDTO(Team team) {
        return modelMapper.map(team, TeamDTO.class);
    }
}
