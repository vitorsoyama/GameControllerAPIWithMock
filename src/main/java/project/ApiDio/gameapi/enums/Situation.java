package project.ApiDio.gameapi.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Situation {
    LIKE("Liked"),
    DISLIKE("Dislike"),
    PLAYING("Playing"),
    WANTPLAY("Want to Play");

    private final String description;
}
