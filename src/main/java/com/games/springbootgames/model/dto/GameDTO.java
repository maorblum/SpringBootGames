package com.games.springbootgames.model.dto;

import com.games.springbootgames.model.Dlc;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = PRIVATE)
public class GameDTO {
    String name;
    int year;
    int score;
    List<String> platforms;
    List<Dlc> dlcs;
}
