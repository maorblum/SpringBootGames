package com.games.springbootgames.model.dto.response;

import com.games.springbootgames.model.Dlc;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GameResponseDTO {
    String id;
    String name;
    int year;
    int score;
    List<String> platforms;
    List<Dlc> dlcs;
}
