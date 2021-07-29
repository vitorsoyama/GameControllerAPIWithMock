package project.ApiDio.gameapi.dto.mapper;



import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import project.ApiDio.gameapi.dto.request.GameDTO;
import project.ApiDio.gameapi.entities.Game;

@Mapper(componentModel = "spring")
public interface GameMapper {
    Game toModel(GameDTO dto);

    GameDTO toDTO(Game dto);
}