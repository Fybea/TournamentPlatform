package com.platform.TournamentPlatform.controllers;

import com.platform.TournamentPlatform.dto.TeamDTO;
import com.platform.TournamentPlatform.exception.NotCreatedException;
import com.platform.TournamentPlatform.util.TeamValidator;
import com.platform.TournamentPlatform.model.Team;
import com.platform.TournamentPlatform.services.TeamService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/team")
public class TeamController {

    private final ModelMapper modelMapper;
    private final TeamService teamService;

    private final TeamValidator teamValidator;

    @Autowired
    public TeamController(ModelMapper modelMapper, TeamService teamService, TeamValidator teamValidator) {
        this.modelMapper = modelMapper;
        this.teamService = teamService;
        this.teamValidator = teamValidator;
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


    @PostMapping("/create")
    private ResponseEntity<HttpStatus> create(@RequestBody @Valid TeamDTO teamDTO
                                              , BindingResult bindingResult) {

        teamValidator.validate(teamDTO, bindingResult);

        if(bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError fieldError: errors) {
                errorMsg.append(fieldError.getField())
                        .append("-").append(fieldError.getDefaultMessage())
                        .append(";");
            }
            throw new NotCreatedException(errorMsg.toString());
        }

        teamService.save(convertToTeam(teamDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }

//    @ExceptionHandler
//    private ResponseEntity<ErrorResponse> handleException(NotCreatedException e) {
//        ErrorResponse errorResponse = new ErrorResponse(
//                e.getMessage(),
//                System.currentTimeMillis()
//        );
//        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler
//    private ResponseEntity<ErrorResponse> handleException(NotFoundException e) {
//        ErrorResponse errorResponse = new ErrorResponse(
//                "Team with this id wasn't found",
//                System.currentTimeMillis()
//        );
//        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
//    }


    private Team convertToTeam(TeamDTO teamDTO) {
        return modelMapper.map(teamDTO, Team.class);
    }

    private TeamDTO convertToTeamDTO(Team team) {
        return modelMapper.map(team, TeamDTO.class);
    }
}
