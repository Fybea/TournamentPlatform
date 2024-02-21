package com.platform.TournamentPlatform.controllers;

import com.platform.TournamentPlatform.dto.PlayerDTO;
import com.platform.TournamentPlatform.exception.NotCreatedException;
import com.platform.TournamentPlatform.model.Player;
import com.platform.TournamentPlatform.services.PlayerService;
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
@RequestMapping("/player")
public class PlayerController {

    private final PlayerService playerService;
    private final ModelMapper modelMapper;

    @Autowired
    public PlayerController(PlayerService playerService, ModelMapper modelMapper) {
        this.playerService = playerService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public List<PlayerDTO> getSensors() {
        return playerService.findAll().stream().map(this::convertToPlayerDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public PlayerDTO getUser(@PathVariable("id") int id) {
        return convertToPlayerDTO(playerService.findOne(id));
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> createPlayer(@RequestBody @Valid PlayerDTO playerDTO,
                                                   BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new NotCreatedException("Bad request");
        }

        this.playerService.save(convertToPlayer(playerDTO));
        return ResponseEntity.ok(HttpStatus.CREATED);
    }


    private Player convertToPlayer(PlayerDTO playerDTO) {
        return modelMapper.map(playerDTO, Player.class);
    }

    private PlayerDTO convertToPlayerDTO(Player player) {
        return modelMapper.map(player, PlayerDTO.class);
    }
}
