package fr.mdbs.thetreasuremap.infrastructure.handler;

import fr.mdbs.thetreasuremap.application.exception.UnexistingTileMapException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UnexistingTileMapException.class)
    public ResponseEntity<String> handleUnexistingTileMap(UnexistingTileMapException e) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
    }

}

