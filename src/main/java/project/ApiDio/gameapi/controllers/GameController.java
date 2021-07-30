package project.ApiDio.gameapi.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import project.ApiDio.gameapi.dto.request.GameDTO;
import project.ApiDio.gameapi.dto.request.QuantityDTO;
import project.ApiDio.gameapi.exception.GameAlreadyRegisteredException;
import project.ApiDio.gameapi.exception.GameNotFoundException;
import project.ApiDio.gameapi.exception.GameStockExceededException;
import project.ApiDio.gameapi.services.GameService;


import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/games")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class GameController implements GameControllerDocs {

    private final GameService gameService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GameDTO createGame(@RequestBody @Valid GameDTO gameDTO) throws GameAlreadyRegisteredException {
        return gameService.createGame(gameDTO);
    }

    @GetMapping("/{name}")
    public GameDTO findByName(@PathVariable String name) throws GameNotFoundException {
        return gameService.findByName(name);
    }

    @GetMapping
    public List<GameDTO> listGames() {

        return gameService.listGames();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) throws GameNotFoundException {
        gameService.deleteById(id);

    }
    @PatchMapping("/{id}/increment")
    public GameDTO increment(@PathVariable Long id, @RequestBody @Valid QuantityDTO quantityDTO) throws GameNotFoundException, GameStockExceededException {
        return gameService.increment(id, quantityDTO.getQuantity());
    }
}

