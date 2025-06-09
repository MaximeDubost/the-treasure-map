package fr.mdbs.thetreasuremap.application;

import fr.mdbs.thetreasuremap.domain.model.adventurer.Adventurer;
import fr.mdbs.thetreasuremap.domain.model.tile.ExplorableTile;
import fr.mdbs.thetreasuremap.domain.model.tile.Tile;
import fr.mdbs.thetreasuremap.domain.model.tilemap.TileMap;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class GameManager {
    @Getter
    private final TileMap tileMap;
    private final List<Adventurer> adventurers = new ArrayList<>();

    public GameManager(TileMap tileMap) {
        this.tileMap = tileMap;
    }

    public void addAdventurerIfExplorableTile(Adventurer adventurer) {
        Tile tile = tileMap.getTile(adventurer.getColX(), adventurer.getRowY());
        if(tile instanceof ExplorableTile explorableTile) {
            adventurers.add(adventurer);
            explorableTile.setOccupied(true);
        }
    }

    public void moveForward(Adventurer adventurer) {
        int targetX = adventurer.getColX() + adventurer.getOrientation().getDx();
        int targetY = adventurer.getRowY() + adventurer.getOrientation().getDy();

        if (!isInBounds(targetX, targetY)) return;

        ExplorableTile currentTile = tileMap.getAdventurerTile(adventurer);
        Tile targetTile = tileMap.getTile(targetX, targetY);

        if(targetTile instanceof ExplorableTile targetExplorableTile) {
            currentTile.setOccupied(false);
            targetExplorableTile.setOccupied(true);
            adventurer.setPosition(targetX, targetY);
        }
    }

    private boolean isInBounds(int x, int y) {
        return x >= 0 && y >= 0 && x < tileMap.getWidth() && y < tileMap.getHeight();
    }
}
