package project.ApiDio.gameapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import project.ApiDio.gameapi.entities.Game;

import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Long> {
    Optional<Game> findByName(String gameName);
}

