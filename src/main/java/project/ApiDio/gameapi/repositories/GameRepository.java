package project.ApiDio.gameapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import project.ApiDio.gameapi.entities.Game;

public interface GameRepository extends JpaRepository<Game, Long> {
}

