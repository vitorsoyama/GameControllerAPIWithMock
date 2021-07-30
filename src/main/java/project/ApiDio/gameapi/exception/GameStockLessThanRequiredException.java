package project.ApiDio.gameapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static java.lang.String.format;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class GameStockLessThanRequiredException extends Throwable {
    public GameStockLessThanRequiredException(Long id, int quantityToDecrement){
        super(format("Game with %s ID to increment informed exceeds the stock available: %s",
                id,
                quantityToDecrement));
    }
}
