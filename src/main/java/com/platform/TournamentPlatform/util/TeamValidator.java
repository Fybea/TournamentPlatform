package com.platform.TournamentPlatform.util;

import com.platform.TournamentPlatform.dto.TeamDTO;
import com.platform.TournamentPlatform.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class TeamValidator implements Validator {

    private final TeamService teamService;

    @Autowired
    public TeamValidator(TeamService teamService) {
        this.teamService = teamService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return TeamDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        TeamDTO teamDTO = (TeamDTO) target;

        if(teamService.findByName(teamDTO.getName()).isPresent()) {
            errors.rejectValue("name", "", "Team with this name is already exists");
        }
    }
}
