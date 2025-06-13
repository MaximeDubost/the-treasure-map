package fr.mdbs.thetreasuremap.application;

import fr.mdbs.thetreasuremap.domain.model.adventurer.Adventurer;
import fr.mdbs.thetreasuremap.domain.model.adventurer.Occupant;
import fr.mdbs.thetreasuremap.domain.model.movement.Movement;
import fr.mdbs.thetreasuremap.domain.model.movement.MovementCommandFactory;
import fr.mdbs.thetreasuremap.domain.model.movement.command.Executable;
import fr.mdbs.thetreasuremap.domain.model.movement.command.NonSpatialMovementExecutable;
import fr.mdbs.thetreasuremap.domain.model.movement.command.SpatialMovementExecutable;
import fr.mdbs.thetreasuremap.domain.model.tile.ExplorableTile;
import fr.mdbs.thetreasuremap.domain.model.tile.MountainTile;
import fr.mdbs.thetreasuremap.domain.model.tile.Tile;
import fr.mdbs.thetreasuremap.domain.model.tilemap.IOLineType;
import fr.mdbs.thetreasuremap.domain.model.tilemap.TileMap;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public final class GameManager {
    @Getter
    private final TileMap tileMap;
    private final List<Adventurer> adventurers = new ArrayList<>();

    private static final Logger log = LoggerFactory.getLogger(GameManager.class);

    public GameManager(TileMap tileMap) {
        this.tileMap = tileMap;

        for (int rowY = 0; rowY < tileMap.getHeight(); rowY++) {
            for (int colX = 0; colX < tileMap.getWidth(); colX++) {
                Tile tile = tileMap.getTile(colX, rowY);
                if (tile instanceof ExplorableTile explorableTile) {
                    Occupant occupant = explorableTile.getOccupant();
                    if (occupant instanceof Adventurer adventurer) {
                        adventurers.add(adventurer);
                    }
                }
            }
        }
    }

    public ByteArrayResource process() {
        log.debug("===== TileMap before processing =====");
        log.debug(this.tileMap.toString());
        this.play();
        log.debug("===== TileMap after processing =====");
        log.debug(this.tileMap.toString());
        return this.getFinalResult();
    }

    public void play() {
        boolean hasRemainingActions;
        do {
            hasRemainingActions = false;

            for (Adventurer adventurer : adventurers) {
                if (!adventurer.getMovementQueue().isEmpty()) {
                    hasRemainingActions = true;

                    Movement movement = adventurer.getMovementQueue().poll();
                    Executable command = MovementCommandFactory.getCommand(movement);

                    if (command instanceof SpatialMovementExecutable spatialCommand) {
                        spatialCommand.execute(adventurer, this.tileMap);
                    } else if (command instanceof NonSpatialMovementExecutable nonSpatialCommand) {
                        nonSpatialCommand.execute(adventurer);
                    }
                }
            }
        } while (hasRemainingActions);
    }

    private ByteArrayResource getFinalResult() {
        List<String> lines = new ArrayList<>();
        lines.add(IOLineType.formatC(tileMap.getWidth(), tileMap.getHeight()));
        for (int y = 0; y < tileMap.getHeight(); y++) {
            for (int x = 0; x < tileMap.getWidth(); x++) {
                Tile tile = tileMap.getTile(x, y);
                if (tile instanceof MountainTile) {
                    lines.add(IOLineType.formatM(x, y));
                } else if (tile instanceof ExplorableTile explorable && explorable.getTreasureCount() > 0) {
                    lines.add(IOLineType.formatT(x, y, explorable.getTreasureCount()));
                }
            }
        }
        for (Adventurer adventurer : adventurers) {
            lines.add(IOLineType.formatA(
                    adventurer.getName(),
                    adventurer.getColX(),
                    adventurer.getRowY(),
                    adventurer.getOrientation(),
                    adventurer.getTreasureCount()
            ));
        }
        return new ByteArrayResource(String.join("\n", lines).getBytes(StandardCharsets.UTF_8));
    }

    public void placeOccupantIfExplorableTile(Adventurer adventurer) {
        Tile tile = tileMap.getTile(adventurer.getColX(), adventurer.getRowY());
        if(tile instanceof ExplorableTile explorableTile) {
            adventurers.add(adventurer);
            explorableTile.setOccupant(adventurer);
        }
    }
}
