package com.games.springbootgames.mapper;

import com.games.springbootgames.model.dto.GameDTO;
import com.games.springbootgames.model.dto.request.GameCreateRequestDTO;
import com.games.springbootgames.model.dto.request.GameUpdateRequestDTO;
import com.games.springbootgames.model.dto.response.GameResponseDTO;
import com.games.springbootgames.model.entity.Game;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class GameDtoMapper {

    private final ModelMapper modelMapper;

    public GameDTO mapToGameDTO(GameCreateRequestDTO gameCreateRequestDTO) {
        return modelMapper.map(gameCreateRequestDTO, GameDTO.class);
    }

    public List<GameDTO> mapToGameDTOs(List<GameCreateRequestDTO> gameCreateRequestDTOs) {
        return gameCreateRequestDTOs.stream()
                .map(gameCreateRequestDTO -> modelMapper.map(gameCreateRequestDTO, GameDTO.class))
                .toList();
    }

    public Game mapToGameEntity(GameDTO gameDTO) {
        return modelMapper.map(gameDTO, Game.class);
    }

    public List<Game> mapToGameEntities(List<GameDTO> gameDTOs) {
        return gameDTOs.stream()
                .map(gameDTO -> modelMapper.map(gameDTO, Game.class))
                .toList();
    }

    public GameDTO mapToGameDTO(GameUpdateRequestDTO gameUpdateRequestDTO) {
        return modelMapper.map(gameUpdateRequestDTO, GameDTO.class);
    }

    public GameResponseDTO mapToGameResponseDTO(Game game) {
        return modelMapper.map(game, GameResponseDTO.class);
    }

    public List<GameResponseDTO> mapToGameResponseDTOs(List<Game> games) {
        return games.stream()
                .map(game -> modelMapper.map(game, GameResponseDTO.class))
                .toList();
    }

}
