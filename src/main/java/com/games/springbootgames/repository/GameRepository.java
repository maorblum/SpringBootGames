package com.games.springbootgames.repository;

import com.games.springbootgames.model.entity.Game;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameRepository extends MongoRepository<Game, String> {
}
