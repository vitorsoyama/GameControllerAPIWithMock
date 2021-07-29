package project.ApiDio.gameapi.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import project.ApiDio.gameapi.dto.request.GameDTO;
import project.ApiDio.gameapi.dto.response.MessageResponseDTO;
import project.ApiDio.gameapi.exception.GameNotFoundException;
import project.ApiDio.gameapi.services.GameService;


import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/games")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class GameController {

    private final GameService gameService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponseDTO create(@RequestBody @Valid GameDTO gameDTO) {
        return gameService.create(gameDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GameDTO findById(@PathVariable Long id) throws GameNotFoundException {
        return gameService.findById(id);
    }

    @GetMapping
    public List<GameDTO> listAll() {
        return gameService.listAll();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponseDTO update(@PathVariable Long id, @RequestBody @Valid GameDTO gameDTO) throws GameNotFoundException {
        return gameService.update(id, gameDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) throws GameNotFoundException {
        gameService.delete(id);
    }
}

