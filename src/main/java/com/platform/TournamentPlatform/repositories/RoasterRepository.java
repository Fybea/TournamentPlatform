package com.platform.TournamentPlatform.repositories;

import com.platform.TournamentPlatform.model.Roaster;
import com.platform.TournamentPlatform.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoasterRepository extends JpaRepository<Roaster, Integer> {

    List<Roaster> findAllByTeamId(Team team);
}
