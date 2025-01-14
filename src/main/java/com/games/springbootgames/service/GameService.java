package com.games.springbootgames.service;

import com.games.springbootgames.mapper.GameDtoMapper;
import com.games.springbootgames.model.dto.GameDTO;
import com.games.springbootgames.model.dto.GamePatchDTO;
import com.games.springbootgames.model.dto.response.GameResponseDTO;
import com.games.springbootgames.model.entity.Game;
import com.games.springbootgames.repository.GameRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Service
public class GameService {

    final GameDtoMapper gameDtoMapper;
    final GameRepository gameRepository;

    public String createGame(GameDTO gameDTO) {
        var game = gameDtoMapper.mapToGameEntity(gameDTO);
        var createdGame = gameRepository.save(game);
        return createdGame.getId();
    }

    public List<String> createGames(List<GameDTO> gameDTOs) {
        var games = gameDtoMapper.mapToGameEntities(gameDTOs);
        var createdGames =  gameRepository.saveAll(games);
        return createdGames.stream().map(Game::getId).toList();
    }

    public String updateGame(String id, GameDTO gameDTO) {
        var game = gameDtoMapper.mapToGameEntity(gameDTO);

        if (gameRepository.existsById(id)) {
            game.setId(id);
            gameRepository.save(game);
            return id;
        }

        log.debug("game with id: {} doesn't exist", id);
        return null;
    }

    public String updateGamePatch(String id, GamePatchDTO gamePatchDTO) {
        Optional<Game> gameOptional = gameRepository.findById(id);
        if (gameOptional.isEmpty()) {
            log.debug("game with id: {} doesn't exist", id);
            return null;
        }

        Game game = gameOptional.get();

        // Only update fields that are not null in the patch DTO
        if (gamePatchDTO.getName() != null) {
            game.setName(gamePatchDTO.getName());
        }
        if (gamePatchDTO.getYear() != 0) {
            game.setYear(gamePatchDTO.getYear());
        }
        if (gamePatchDTO.getScore() != 0) {
            game.setScore(gamePatchDTO.getScore());
        }
        if (gamePatchDTO.getPlatforms() != null) {
            game.setPlatforms(gamePatchDTO.getPlatforms());
        }
        if (gamePatchDTO.getDlcs() != null) {
            game.setDlcs(gamePatchDTO.getDlcs());
        }

        var updatedPatchGame = gameRepository.save(game);
        return updatedPatchGame.getId();
    }

    public List<GameResponseDTO> getAllGames() {
        List<Game> games = gameRepository.findAll();
        return gameDtoMapper.mapToGameResponseDTOs(games);
    }

    public GameResponseDTO getGameById(String id) {
        Optional<Game> gameOptional = gameRepository.findById(id);
        return gameOptional.map(gameDtoMapper::mapToGameResponseDTO).orElse(null);

    }

    public void deleteGames(Set<String> ids) {
        gameRepository.deleteAllById(ids);
    }

}
