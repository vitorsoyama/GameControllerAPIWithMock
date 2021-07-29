package project.ApiDio.gameapi.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.ApiDio.gameapi.dto.mapper.GameMapper;
import project.ApiDio.gameapi.dto.request.GameDTO;
import project.ApiDio.gameapi.dto.response.MessageResponseDTO;
import project.ApiDio.gameapi.entities.Game;
import project.ApiDio.gameapi.exception.GameNotFoundException;
import project.ApiDio.gameapi.repositories.GameRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class GameService {

    private final GameRepository gameRepository;

    private final GameMapper gameMapper;

    public MessageResponseDTO create(GameDTO gameDTO) {
        Game game = gameMapper.toModel(gameDTO);
        Game savedGame = gameRepository.save(game);

        MessageResponseDTO messageResponse = createMessageResponse("Game successfully created with ID ", savedGame.getId());

        return messageResponse;
    }

    public GameDTO findById(Long id) throws GameNotFoundException {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new GameNotFoundException(id));

        return gameMapper.toDTO(game);
    }

    public List<GameDTO> listAll() {
        List<Game> people = gameRepository.findAll();
        return people.stream()
                .map(gameMapper::toDTO)
                .collect(Collectors.toList());
    }

    public MessageResponseDTO update(Long id, GameDTO gameDTO) throws GameNotFoundException {
        gameRepository.findById(id)
                .orElseThrow(() -> new GameNotFoundException(id));

        Game updatedGame = gameMapper.toModel(gameDTO);
        Game savedGame = gameRepository.save(updatedGame);

        MessageResponseDTO messageResponse = createMessageResponse("Game successfully updated with ID ", savedGame.getId());

        return messageResponse;
    }

    public void delete(Long id) throws GameNotFoundException {
        gameRepository.findById(id)
                .orElseThrow(() -> new GameNotFoundException(id));

        gameRepository.deleteById(id);
    }

    private MessageResponseDTO createMessageResponse(String s, Long id2) {
        return MessageResponseDTO.builder()
                .message(s + id2)
                .build();
    }
}