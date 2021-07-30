package project.ApiDio.gameapi.gameLibrary.builder;

import lombok.Builder;
import project.ApiDio.gameapi.dto.request.GameDTO;

@Builder
public class GameDTOBuilder {

    @Builder.Default
    private final Long id = 1L;

    @Builder.Default
    private final String gameName = "Super Smash Bros. Ultimate";

    @Builder.Default
    private final Integer gameYear = 2018;

    @Builder.Default
    private final String gameProducer = "Nintendo";

    @Builder.Default
    private final String gameConsole = "Nintendo Switch";

    @Builder.Default
    private final int max = 20;

    @Builder.Default
    private final int quantity = 10;

    public GameDTO toGameDTO(){
        return new GameDTO(id,
                gameName,
                gameYear,
                gameProducer,
                gameConsole,
                max,
                quantity);
    }

}
