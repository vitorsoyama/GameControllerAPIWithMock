package project.ApiDio.gameapi.gameLibrary.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import project.ApiDio.gameapi.dto.mapper.GameMapper;
import project.ApiDio.gameapi.dto.request.GameDTO;
import project.ApiDio.gameapi.entities.Game;
import project.ApiDio.gameapi.exception.GameAlreadyRegisteredException;
import project.ApiDio.gameapi.exception.GameNotFoundException;
import project.ApiDio.gameapi.exception.GameStockExceededException;
import project.ApiDio.gameapi.gameLibrary.builder.GameDTOBuilder;
import project.ApiDio.gameapi.repositories.GameRepository;
import project.ApiDio.gameapi.services.GameService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class GameServiceTest {
    private static final long INVALID_GAME_ID = 1L;

    @Mock
    private GameRepository gameRepository;

    private GameMapper gameMapper = GameMapper.INSTANCE;

    @InjectMocks
    private GameService gameService;

    @Test
    void whenGameInformedThenItShouldBeCreated() throws GameAlreadyRegisteredException {
        // given
        GameDTO expectedGameDTO = GameDTOBuilder.builder().build().toGameDTO();
        Game expectedSavedGame = gameMapper.toModel(expectedGameDTO);

        // when
        when(gameRepository.findByName(expectedGameDTO.getGameName())).thenReturn(empty());
        when(gameRepository.save(expectedSavedGame)).thenReturn(expectedSavedGame);

        //then
        GameDTO createdGameDTO = gameService.createGame(expectedGameDTO);

        assertThat(createdGameDTO.getId(), is(equalTo(expectedGameDTO.getId())));
        assertThat(createdGameDTO.getGameName(), is(equalTo(expectedGameDTO.getGameName())));
        assertThat(createdGameDTO.getQuantity(), is(equalTo(expectedGameDTO.getQuantity())));
    }

    @Test
    void whenAlreadyRegisteredGameInformedThenAnExceptionShouldBeThrown() {
        // given
        GameDTO expectedGameDTO = GameDTOBuilder.builder().build().toGameDTO();
        Game duplicatedGame = gameMapper.toModel(expectedGameDTO);

        // when
        when(gameRepository.findByName(expectedGameDTO.getGameName())).thenReturn(Optional.of(duplicatedGame));

        // then
        assertThrows(GameAlreadyRegisteredException.class, () -> gameService.createGame(expectedGameDTO));
    }

    @Test
    void whenValidGameNameIsGivenThenReturnAGame() throws GameNotFoundException {
        // given
        GameDTO expectedFoundGameDTO = GameDTOBuilder.builder().build().toGameDTO();
        Game expectedFoundGame = gameMapper.toModel(expectedFoundGameDTO);

        // when
        when(gameRepository.findByName(expectedFoundGame.getGameName())).thenReturn(Optional.of(expectedFoundGame));

        // then
        GameDTO foundGameDTO = gameService.findByName(expectedFoundGameDTO.getGameName());

        assertThat(foundGameDTO, is(equalTo(expectedFoundGameDTO)));
    }

    @Test
    void whenNotRegisteredGameNameIsGivenThenThrowAnException() {
        // given
        GameDTO expectedFoundGameDTO = GameDTOBuilder.builder().build().toGameDTO();

        // when
        when(gameRepository.findByName(expectedFoundGameDTO.getGameName())).thenReturn(empty());

        // then
        assertThrows(GameNotFoundException.class, () -> gameService.findByName(expectedFoundGameDTO.getGameName()));
    }

    @Test
    void whenListGameIsCalledThenReturnAListOfGames() {
        // given
        GameDTO expectedFoundGameDTO = GameDTOBuilder.builder().build().toGameDTO();
        Game expectedFoundGame = gameMapper.toModel(expectedFoundGameDTO);

        //when
        when(gameRepository.findAll()).thenReturn(Collections.singletonList(expectedFoundGame));

        //then
        List<GameDTO> foundListGamesDTO = gameService.listGames();

        assertThat(foundListGamesDTO, is(not(empty())));
        assertThat(foundListGamesDTO.get(0), is(equalTo(expectedFoundGameDTO)));
    }

    @Test
    void whenListGameIsCalledThenReturnAnEmptyListOfGames() {
        //when
        when(gameRepository.findAll()).thenReturn(Collections.EMPTY_LIST);

        //then
        List<GameDTO> foundListGamesDTO = gameService.listGames();

        assertThat(foundListGamesDTO, is(empty()));
    }

    @Test
    void whenExclusionIsCalledWithValidIdThenAGameShouldBeDeleted() throws GameNotFoundException{
        // given
        GameDTO expectedDeletedGameDTO = GameDTOBuilder.builder().build().toGameDTO();
        Game expectedDeletedGame = gameMapper.toModel(expectedDeletedGameDTO);

        // when
        when(gameRepository.findById(expectedDeletedGameDTO.getId())).thenReturn(Optional.of(expectedDeletedGame));
        doNothing().when(gameRepository).deleteById(expectedDeletedGameDTO.getId());

        // then
        gameService.deleteById(expectedDeletedGameDTO.getId());

        verify(gameRepository, times(1)).findById(expectedDeletedGameDTO.getId());
        verify(gameRepository, times(1)).deleteById(expectedDeletedGameDTO.getId());
    }

    @Test
    void whenIncrementIsCalledThenIncrementGameStock() throws GameNotFoundException, GameStockExceededException {
        //given
        GameDTO expectedGameDTO = GameDTOBuilder.builder().build().toGameDTO();
        Game expectedGame = gameMapper.toModel(expectedGameDTO);

        //when
        when(gameRepository.findById(expectedGameDTO.getId())).thenReturn(Optional.of(expectedGame));
        when(gameRepository.save(expectedGame)).thenReturn(expectedGame);

        int quantityToIncrement = 10;
        int expectedQuantityAfterIncrement = expectedGameDTO.getQuantity() + quantityToIncrement;

        // then
        GameDTO incrementedGameDTO = gameService.increment(expectedGameDTO.getId(), quantityToIncrement);

        assertThat(expectedQuantityAfterIncrement, equalTo(incrementedGameDTO.getQuantity()));
        assertThat(expectedQuantityAfterIncrement, lessThan(expectedGameDTO.getMax()));
    }

    @Test
    void whenIncrementIsGreaterThanMaxThenThrowException() {
        GameDTO expectedGameDTO = GameDTOBuilder.builder().build().toGameDTO();
        Game expectedGame = gameMapper.toModel(expectedGameDTO);

        when(gameRepository.findById(expectedGameDTO.getId())).thenReturn(Optional.of(expectedGame));

        int quantityToIncrement = 80;
        assertThrows(GameStockExceededException.class, () -> gameService.increment(expectedGameDTO.getId(), quantityToIncrement));
    }

    @Test
    void whenIncrementAfterSumIsGreaterThanMaxThenThrowException() {
        GameDTO expectedGameDTO = GameDTOBuilder.builder().build().toGameDTO();
        Game expectedGame = gameMapper.toModel(expectedGameDTO);

        when(gameRepository.findById(expectedGameDTO.getId())).thenReturn(Optional.of(expectedGame));

        int quantityToIncrement = 45;
        assertThrows(GameStockExceededException.class, () -> gameService.increment(expectedGameDTO.getId(), quantityToIncrement));
    }

    @Test
    void whenIncrementIsCalledWithInvalidIdThenThrowException() {
        int quantityToIncrement = 10;

        when(gameRepository.findById(INVALID_GAME_ID)).thenReturn(empty());

        assertThrows(GameNotFoundException.class, () -> gameService.increment(INVALID_GAME_ID, quantityToIncrement));
    }
}
