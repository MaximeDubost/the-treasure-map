package fr.mdbs.thetreasuremap.domain.model.movement.command;

import fr.mdbs.thetreasuremap.domain.model.adventurer.Adventurer;
import fr.mdbs.thetreasuremap.domain.model.tilemap.TileMap;

public interface SpatialMovementExecutable extends Executable {
    void execute(Adventurer adventurer, TileMap tileMap);
}
