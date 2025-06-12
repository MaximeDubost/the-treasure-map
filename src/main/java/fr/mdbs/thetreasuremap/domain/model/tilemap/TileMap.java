package fr.mdbs.thetreasuremap.domain.model.tilemap;

import fr.mdbs.thetreasuremap.domain.model.adventurer.Adventurer;
import fr.mdbs.thetreasuremap.domain.model.adventurer.Occupant;
import fr.mdbs.thetreasuremap.domain.model.tile.ExplorableTile;
import fr.mdbs.thetreasuremap.domain.model.tile.MountainTile;
import fr.mdbs.thetreasuremap.domain.model.tile.PlainTile;
import fr.mdbs.thetreasuremap.domain.model.tile.Tile;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

public final class TileMap {
    @Getter
    private final int width;
    @Getter
    private final int height;
    private final Tile[][] tiles;

    public TileMap(int width, int height) {
        this.width = Math.max(width, 1);
        this.height = Math.max(height, 1);
        this.tiles = new Tile[height][width];

        for (int rowY = 0; rowY < height; rowY++) {
            for (int colX = 0; colX < width; colX++) {
                tiles[rowY][colX] = new PlainTile(colX, rowY);
            }
        }
    }

    public Tile getTile(int colX, int rowY) {
        return tiles[rowY][colX];
    }

    public void setTile(Tile tile) {
        try { tiles[tile.getRowY()][tile.getColX()] = tile; }
        catch (IndexOutOfBoundsException ignored) {}

    }

    public Optional<ExplorableTile> getExplorableTile(int colX, int rowY) {
        try {
            if(tiles[rowY][colX] instanceof ExplorableTile explorableTile)
                return Optional.of(explorableTile);
        }
        catch (IndexOutOfBoundsException ignored) {}
        return Optional.empty();
    }

    public ExplorableTile getAdventurerTile(Adventurer adventurer) {
        return (ExplorableTile) tiles[adventurer.getRowY()][adventurer.getColX()];
    }

    public void placeOccupantIfExplorableTile(Occupant occupant) {
        try {
            Tile tile = tiles[occupant.getRowY()][occupant.getColX()];
            if(tile instanceof ExplorableTile explorableTile) {
                explorableTile.setOccupant(occupant);
            }
        }
        catch (IndexOutOfBoundsException ignored) {}
    }

    public boolean hasAnyOccupant() {
        return Arrays.stream(tiles)
                .flatMap(Arrays::stream)
                .filter(tile -> tile instanceof ExplorableTile)
                .map(tile -> (ExplorableTile) tile)
                .anyMatch(ExplorableTile::isOccupied);
    }

    public int getTotalTreasureCount() {
        return Arrays.stream(tiles)
                .flatMap(Arrays::stream)
                .filter(tile -> tile instanceof ExplorableTile)
                .map(tile -> (ExplorableTile) tile)
                .mapToInt(ExplorableTile::getTreasureCount)
                .sum();
    }


    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("\n");

        for (int rowY = 0; rowY < height; rowY++) {
            for (int colX = 0; colX < width; colX++) {
                Tile tile = getTile(colX, rowY);
                if (tile != null) {
                    if(tile instanceof PlainTile plainTile) {
                        if(plainTile.isOccupied())
                            result.append(" A ");
                        else if (plainTile.getTreasureCount() > 0)
                            result.append(" T ");
                        else
                            result.append(" . ");
                    }
                    if(tile instanceof MountainTile) {
                        result.append(" M ");
                    }
                }
            }
            result.append("\n");
        }
        return result.toString();
    }

}
