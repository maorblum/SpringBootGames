package com.games.springbootgames.controller;

import com.games.springbootgames.model.dto.GamePatchDTO;
import com.games.springbootgames.model.entity.Game;
import com.games.springbootgames.repository.GameRepository;
import com.games.springbootgames.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.games.springbootgames.util.constant.ConstantAPI.ENDPOINT_GAMES;

@Slf4j
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@RestController
@RequestMapping(ENDPOINT_GAMES)
public class GameController {

    final GameService gameService;
    final GameRepository gameRepository;

    @Operation(
            summary = "Create a new game",
            description = "Creates a new game with the provided details"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Game created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public String createGame(@RequestBody Game game) {
        log.debug("create game: {}", game);
        var gameId = gameService.createGame(game);
        log.debug("game created successfully. game id: {}", gameId);
        return gameId;
    }

    @Operation(
            summary = "Creates new games",
            description = "Creates new games with the provided details"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Games created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/batch")
    public List<String> createGames(@RequestBody List<Game> games) {
        log.debug("create games: {}", games);
        var gameIds = gameService.createGames(games);
        log.debug("games created successfully. game ids: {}", gameIds);
        return gameIds;
    }

    @Operation(
            summary = "Update a game",
            description = "Update a game with the provided details"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Game updated successfully"),
            @ApiResponse(responseCode = "404", description = "Game not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<String> updateGame(@PathVariable String id, @RequestBody Game game) {
        String gameId = gameService.updateGame(id, game);
        if (gameId == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(gameId);
    }

    @Operation(
            summary = "Update game patch",
            description = "Update game patch with the provided details"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Game updated successfully"),
            @ApiResponse(responseCode = "404", description = "Game not found")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<String> updateGamePatch(@PathVariable String id, @RequestBody GamePatchDTO gamePatchDTO) {
        String gameId = gameService.updateGamePatch(id, gamePatchDTO);
        if (gameId == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(gameId);
    }



    @Operation(
            summary = "Get all games",
            description = "Get all available games"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Games fetched successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @GetMapping
    public List<Game> getAllGames() {
        return gameService.getAllGames();
    }

    @Operation(
            summary = "Get game by ID",
            description = "Get game by ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Game fetched successfully"),
            @ApiResponse(responseCode = "404", description = "Game not found")
    })
//    @GetMapping("/get")
//    public ResponseEntity<Game> getGameById(@RequestParam String id) {
//        Optional<Game> optionalGame = gameService.getGameById(id);
//        return optionalGame.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//    }
    @GetMapping("/{id}")
    public ResponseEntity<Game> getGameById(@PathVariable String id) {
        Optional<Game> optionalGame = gameService.getGameById(id);
        return optionalGame.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Delete games",
            description = "Delete games by IDs"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Game fetched successfully"),
            @ApiResponse(responseCode = "404", description = "Game not found")
    })
    @DeleteMapping
    public ResponseEntity<String> deleteGames(@RequestBody Set<String> ids) {
        gameService.deleteGames(ids);
        return ResponseEntity.ok("The games were deleted successfully");
    }

}
