package fr.mdbs.thetreasuremap.domain.model.adventurer;

import fr.mdbs.thetreasuremap.domain.model.movement.Movement;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.LinkedList;
import java.util.Queue;

@Getter
@SuperBuilder
public final class Adventurer extends Occupant implements Rotatable, Looter {
    private final String name;
    @Setter
    private Orientation orientation;
    @Builder.Default
    private Queue<Movement> movementQueue = new LinkedList<>();
    @Setter
    @Builder.Default
    private int treasureCount = 0;

    public Adventurer(String name, int colX, int rowY, Orientation orientation, String movementSequence) {
        super(Math.max(colX, 0), Math.max(rowY, 0));
        this.name = name;
        this.orientation = orientation;
        this.movementQueue = Movement.queue(movementSequence);
    }

    public Adventurer(String name, Orientation orientation, String movementSequence) {
        super(0, 0);
        this.name = name;
        this.orientation = orientation;
        this.movementQueue = Movement.queue(movementSequence);
    }

    public Adventurer(String name, Orientation orientation) {
        super(0, 0);
        this.name = name;
        this.orientation = orientation;
    }

    @Override
    public void turnLeft() {
        this.orientation = this.orientation.turnLeft();
    }

    @Override
    public void turnRight() {
        this.orientation = this.orientation.turnRight();
    }

    @Override
    public void loot() {
        this.treasureCount++;
    }
}
