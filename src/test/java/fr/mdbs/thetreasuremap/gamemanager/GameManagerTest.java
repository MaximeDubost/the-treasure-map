package fr.mdbs.thetreasuremap.gamemanager;

import fr.mdbs.thetreasuremap.application.GameManager;
import fr.mdbs.thetreasuremap.domain.model.adventurer.Adventurer;
import fr.mdbs.thetreasuremap.domain.model.adventurer.Orientation;
import fr.mdbs.thetreasuremap.domain.model.tilemap.TileMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

@SpringBootTest
public class GameManagerTest {

    static Stream<Arguments> provideOrientationAndCoords() {
        return Stream.of(
                Arguments.of(new TileMap(1, 2), Orientation.S, 0, 0, 0, 1),
                Arguments.of(new TileMap(2, 1), Orientation.E, 0, 0, 1, 0),
                Arguments.of(new TileMap(1, 2), Orientation.N, 0, 1, 0, 0),
                Arguments.of(new TileMap(2, 1), Orientation.O, 1, 0, 0, 0)
        );
    }

    @ParameterizedTest(name = "Should move forward ({0})")
    @MethodSource("provideOrientationAndCoords")
    public void should_moveForward(TileMap tileMap, Orientation orientation,
                                   int initialColX, int initialRowY, int expectedColX, int expectedRowY) {
        // Given
        GameManager gameManager = new GameManager(tileMap);
        Adventurer adventurer = new Adventurer("Max", initialColX, initialRowY, orientation);
        gameManager.addAdventurerIfExplorableTile(adventurer);
        boolean isOccupiedBefore = tileMap.getAdventurerTile(adventurer).isOccupied();

        // When
        gameManager.moveForward(adventurer);

        // Then
        Assertions.assertEquals(expectedColX, adventurer.getColX());
        Assertions.assertEquals(expectedRowY, adventurer.getRowY());

        boolean isOccupiedAfter = tileMap.getExplorableTile(initialColX, initialRowY).orElseThrow().isOccupied();
        Assertions.assertNotEquals(isOccupiedBefore, isOccupiedAfter);
        Assertions.assertFalse(isOccupiedAfter);
    }

    @Test
    public void should_notExitBounds() {
        // Given
        TileMap tileMap = new TileMap(1, 1);
        GameManager gameManager = new GameManager(tileMap);
        Adventurer adventurer = new Adventurer("Max", 0, 0, Orientation.N);
        gameManager.addAdventurerIfExplorableTile(adventurer);

        // When
        gameManager.moveForward(adventurer);

        // Then
        Assertions.assertEquals(0, adventurer.getColX());
        Assertions.assertEquals(0, adventurer.getRowY());
    }

}
