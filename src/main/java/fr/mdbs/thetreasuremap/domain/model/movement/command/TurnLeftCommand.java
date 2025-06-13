package fr.mdbs.thetreasuremap.domain.model.movement.command;

import fr.mdbs.thetreasuremap.domain.model.adventurer.Adventurer;

public final class TurnLeftCommand implements NonSpatialMovementExecutable {
    @Override
    public void execute(Adventurer adventurer) {
        adventurer.turnLeft();
    }
}
