package com.platform.TournamentPlatform.controllers;

import com.platform.TournamentPlatform.dto.TeamDTO;
import com.platform.TournamentPlatform.dto.TournamentDTO;
import com.platform.TournamentPlatform.model.Team;
import com.platform.TournamentPlatform.model.Tournament;
import com.platform.TournamentPlatform.services.TeamTournamentService;
import com.platform.TournamentPlatform.services.TournamentService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/tournament")
public class TournamentController {
    private final ModelMapper modelMapper;
    private final TournamentService tournamentService;
    private final TeamTournamentService teamTournamentService;

    @Autowired
    public TournamentController(ModelMapper modelMapper,
                                TournamentService tournamentService,
                                TeamTournamentService teamTournamentService) {

        this.modelMapper = modelMapper;
        this.tournamentService = tournamentService;
        this.teamTournamentService = teamTournamentService;
    }

    @GetMapping
    public List<TournamentDTO> getTournaments() {
        return tournamentService.findAll().stream().map(this::convertToTournamentDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public TournamentDTO getTournament(@PathVariable int id) {
        return convertToTournamentDTO(tournamentService.findById(id));
    }

    @PostMapping("/create")
    private ResponseEntity<HttpStatus> create(@RequestBody @Valid TournamentDTO tournamentDTO) {
        tournamentService.save(convertToTournament(tournamentDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/registration/{tournamentId}")
    public ResponseEntity<HttpStatus> enterToTournament(@PathVariable("tournamentId") int tournamentId,
                                                        @RequestBody Team team) {
        teamTournamentService.save(tournamentId, team.getId());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/{tournamentId}/teams")
    public List<TeamDTO> getAllRegisteredTeams(@PathVariable("tournamentId") int tournamentId) {
        return teamTournamentService.findAllByTournamentId(tournamentId).stream().map(this::convertToTeamDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{tournamentId}/participants")
    public int getParticipantsNumber(@PathVariable("tournamentId") int tournamentId) {
        return teamTournamentService.getParticipantsNumber(tournamentId);
    }

    @PatchMapping("/{tournamentId}")
    private ResponseEntity<HttpStatus> cancelTournament (@PathVariable("tournamentId") int tournamentId) {
        tournamentService.cancelTournament(tournamentId);
        return ResponseEntity.ok(HttpStatus.OK);
    }




    private Tournament convertToTournament(TournamentDTO tournamentDTO) {
        return modelMapper.map(tournamentDTO, Tournament.class);
    }

    private TeamDTO convertToTeamDTO(Team team) {
        return modelMapper.map(team, TeamDTO.class);
    }

    private TournamentDTO convertToTournamentDTO(Tournament tournament) {
        return modelMapper.map(tournament, TournamentDTO.class);
    }
}