package com.games.springbootgames;

import com.games.springbootgames.model.entity.Game;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.List;

import static com.games.springbootgames.util.constant.ConstantAPI.ENDPOINT_GAMES;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

// Integration tests (starts application at random port)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringBootGamesApplicationTests {

    // the actual port
    @LocalServerPort
    private int port;

    // can also create a new RestTemplate (without using TestRestTemplate)
    @Autowired
    private TestRestTemplate restTemplate;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    public void testGetAllGames_restTemplate_exchange() {
        // GET request example
        // restTemplate.exchange(): Sends an HTTP request and maps the response to the specified type.
        ResponseEntity<List<Game>> responseEntity = restTemplate.exchange(
                getRootUrl() + ENDPOINT_GAMES,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );

        List<Game> games = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(games);
    }

    @Test
    public void testGetAllGames_restTemplate_getForObject() {
        // Make the GET request and parse the response as an array of User objects
        Game[] gameArray = restTemplate.getForObject(
                getRootUrl() + ENDPOINT_GAMES,
                Game[].class);

        // Convert the array to a list
        List<Game> games = Arrays.asList(gameArray);

        assertNotNull(games);
    }

    @Test
    public void testPostGameAndGetGame() {
        Game game = Game.builder()
                .name("testGame")
                .year(2024)
                .score(85)
                .build();

        // Post request example
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(
                getRootUrl() + ENDPOINT_GAMES,
                game,
                String.class
        );

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        String gameId = responseEntity.getBody();
        assertNotNull(gameId);

        // fetch game by game ID
        // Make the GET request and map the response to the Game object
        Game fetchedGame = restTemplate.getForObject(
                getRootUrl() + ENDPOINT_GAMES + "/{id}",
                Game.class,
                gameId);

        assertNotNull(fetchedGame);
        assertEquals(fetchedGame.getId(), gameId);
        assertEquals(fetchedGame.getName(), game.getName());
        assertEquals(fetchedGame.getYear(), game.getYear());
        assertEquals(fetchedGame.getScore(), game.getScore());
    }
}
