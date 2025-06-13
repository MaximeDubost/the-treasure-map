package fr.mdbs.thetreasuremap.domain.model.movement.command;

import fr.mdbs.thetreasuremap.domain.model.adventurer.Adventurer;
import fr.mdbs.thetreasuremap.domain.model.tile.ExplorableTile;
import fr.mdbs.thetreasuremap.domain.model.tile.Tile;
import fr.mdbs.thetreasuremap.domain.model.tilemap.TileMap;

public final class MoveForwardCommand implements SpatialMovementExecutable {
    @Override
    public void execute(Adventurer adventurer, TileMap tileMap) {
        int targetX = adventurer.getColX() + adventurer.getOrientation().getDx();
        int targetY = adventurer.getRowY() + adventurer.getOrientation().getDy();

        if (!tileMap.hasTileAt(targetX, targetY)) return;

        ExplorableTile currentTile = tileMap.getAdventurerTile(adventurer);
        Tile targetTile = tileMap.getTile(targetX, targetY);

        if(targetTile instanceof ExplorableTile targetExplorableTile) {
            currentTile.clearOccupant();
            targetExplorableTile.setOccupant(adventurer);
            adventurer.setPosition(targetX, targetY);

            if (targetExplorableTile.hasAnyTreasure()) {
                targetExplorableTile.decreaseTreasureCount();
                adventurer.loot();
            }
        }
    }
}
