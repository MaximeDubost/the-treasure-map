package fr.mdbs.thetreasuremap.application.exception;

public class UnexistingTileMapException extends RuntimeException {
    public UnexistingTileMapException(String message) {
        super(message);
    }
}
