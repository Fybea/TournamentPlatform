package com.platform.TournamentPlatform.controllers;

import com.platform.TournamentPlatform.dto.TournamentDTO;
import com.platform.TournamentPlatform.model.Tournament;
import com.platform.TournamentPlatform.services.TournamentService;
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
@RequestMapping("/tournament")
public class TournamentController {

    private final ModelMapper modelMapper;
    private final TournamentService tournamentService;

    @Autowired
    public TournamentController(ModelMapper modelMapper, TournamentService tournamentService) {
        this.modelMapper = modelMapper;
        this.tournamentService = tournamentService;
    }


    @GetMapping
    public List<TournamentDTO> getTeams() {
        return tournamentService.findAll().stream().map(this::convertToTournamentDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public TournamentDTO getTeam(@PathVariable int id) {
        return convertToTournamentDTO(tournamentService.findById(id));
    }


    @PostMapping("/create")
    private ResponseEntity<HttpStatus> create(@RequestBody @Valid TournamentDTO tournamentDTO
            , BindingResult bindingResult) {
        tournamentService.save(convertToTournament(tournamentDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }


    private Tournament convertToTournament(TournamentDTO tournamentDTO) {
        return modelMapper.map(tournamentDTO, Tournament.class);
    }

    private TournamentDTO convertToTournamentDTO(Tournament tournament) {
        return modelMapper.map(tournament, TournamentDTO.class);
    }
}
