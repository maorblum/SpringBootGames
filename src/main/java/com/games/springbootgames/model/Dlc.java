package com.games.springbootgames.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Dlc {
    String name;
    String year;
    int score;
    List<String> platforms;
}
