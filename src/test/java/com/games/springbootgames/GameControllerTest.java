package com.games.springbootgames;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.games.springbootgames.model.entity.Game;
import com.games.springbootgames.service.GameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static com.games.springbootgames.util.constant.ConstantAPI.ENDPOINT_GAMES;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GameService gameService;

    @Test
    void shouldGetGame() throws Exception {
        // Given
        Game game = Game.builder()
                .id("1")
                .name("testGame")
                .year(2024)
                .score(85)
                .build();

        Optional<Game> gameOptional = Optional.of(game);

        when(gameService.getGameById("1")).thenReturn(gameOptional);

        // When & Then
        mockMvc.perform(get(ENDPOINT_GAMES + "/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("testGame"))
                .andExpect(jsonPath("$.year").value("2024"))
                .andExpect(jsonPath("$.score").value("85"));
    }

    @Test
    void shouldCreateGame() throws Exception {
        // Given
        String gameId = "1";
        Game game = Game.builder()
                .id(gameId)
                .name("testGame")
                .year(2024)
                .score(85)
                .build();

        when(gameService.createGame(any(Game.class))).thenReturn(gameId);

        // When & Then
        mockMvc.perform(post(ENDPOINT_GAMES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(game)))
                .andExpect(status().isCreated())
                .andExpect(content().string(gameId));
    }
}
