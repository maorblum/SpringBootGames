package com.games.springbootgames.service;

import com.games.springbootgames.model.dto.GamePatchDTO;
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

    final GameRepository gameRepository;

    public String createGame(Game game) {
        var createdGame =  gameRepository.save(game);
        return createdGame.getId();
    }

    public List<String> createGames(List<Game> games) {
        var createdGames =  gameRepository.saveAll(games);
        return createdGames.stream().map(Game::getId).toList();
    }

    public String updateGame(String id, Game game) {
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

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    public Optional<Game> getGameById(String id) {
        return gameRepository.findById(id);
    }

    public void deleteGames(Set<String> ids) {
        gameRepository.deleteAllById(ids);
    }

}
