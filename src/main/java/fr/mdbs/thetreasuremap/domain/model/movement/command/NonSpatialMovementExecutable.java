package fr.mdbs.thetreasuremap.domain.model.movement.command;

import fr.mdbs.thetreasuremap.domain.model.adventurer.Adventurer;

public interface NonSpatialMovementExecutable extends Executable {
    void execute(Adventurer adventurer);
}
