package com.games.springbootgames.model.entity;

import com.games.springbootgames.model.Dlc;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = "games")
public class Game {
    @Id
    String id;
    String name;
    int year;
    int score;
    List<String> platforms;
    List<Dlc> dlcs;
}
