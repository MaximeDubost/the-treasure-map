package fr.mdbs.thetreasuremap.adventurer;

import fr.mdbs.thetreasuremap.domain.model.adventurer.Adventurer;
import fr.mdbs.thetreasuremap.domain.model.adventurer.Orientation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AdventurerOrientationTest {

    @Test
    public void should_beOrientedToWest_when_isOrientedToNorth_and_turnLeft() {
        // Given
        Adventurer adventurer = new Adventurer("Max", Orientation.N);

        // When
        adventurer.turnLeft();

        // Then
        Assertions.assertEquals(Orientation.O, adventurer.getOrientation());
    }

    @Test
    public void should_beOrientedToSouth_when_isOrientedToWest_and_turnLeft() {
        // Given
        Adventurer adventurer = new Adventurer("Max", Orientation.O);

        // When
        adventurer.turnLeft();

        // Then
        Assertions.assertEquals(Orientation.S, adventurer.getOrientation());
    }

    @Test
    public void should_beOrientedToEast_when_isOrientedToSouth_and_turnLeft() {
        // Given
        Adventurer adventurer = new Adventurer("Max", Orientation.S);

        // When
        adventurer.turnLeft();

        // Then
        Assertions.assertEquals(Orientation.E, adventurer.getOrientation());
    }

    @Test
    public void should_beOrientedToNorth_when_isOrientedToEast_and_turnLeft() {
        // Given
        Adventurer adventurer = new Adventurer("Max", Orientation.E);

        // When
        adventurer.turnLeft();

        // Then
        Assertions.assertEquals(Orientation.N, adventurer.getOrientation());
    }

    @Test
    public void should_beOrientedToEast_when_isOrientedToNorth_and_turnRight() {
        // Given
        Adventurer adventurer = new Adventurer("Max", Orientation.N);

        // When
        adventurer.turnRight();

        // Then
        Assertions.assertEquals(Orientation.E, adventurer.getOrientation());
    }

    @Test
    public void should_beOrientedToSouth_when_isOrientedToEast_and_turnRight() {
        // Given
        Adventurer adventurer = new Adventurer("Max", Orientation.E);

        // When
        adventurer.turnRight();

        // Then
        Assertions.assertEquals(Orientation.S, adventurer.getOrientation());
    }

    @Test
    public void should_beOrientedToWest_when_isOrientedToSouth_and_turnRight() {
        // Given
        Adventurer adventurer = new Adventurer("Max", Orientation.S);

        // When
        adventurer.turnRight();

        // Then
        Assertions.assertEquals(Orientation.O, adventurer.getOrientation());
    }

    @Test
    public void should_beOrientedToNorth_when_isOrientedToWest_and_turnRight() {
        // Given
        Adventurer adventurer = new Adventurer("Max", Orientation.O);

        // When
        adventurer.turnRight();

        // Then
        Assertions.assertEquals(Orientation.N, adventurer.getOrientation());
    }
}
