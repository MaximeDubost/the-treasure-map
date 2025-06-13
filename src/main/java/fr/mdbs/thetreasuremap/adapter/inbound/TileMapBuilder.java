package fr.mdbs.thetreasuremap.adapter.inbound;

import fr.mdbs.thetreasuremap.domain.model.adventurer.Adventurer;
import fr.mdbs.thetreasuremap.domain.model.movement.Movement;
import fr.mdbs.thetreasuremap.domain.model.adventurer.Orientation;
import fr.mdbs.thetreasuremap.domain.model.tile.MountainTile;
import fr.mdbs.thetreasuremap.domain.model.tile.PlainTile;
import fr.mdbs.thetreasuremap.domain.model.tilemap.IOLineType;
import fr.mdbs.thetreasuremap.domain.model.tilemap.TileMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.regex.Matcher;

public final class TileMapBuilder {
    private static final Logger log = LoggerFactory.getLogger(TileMapBuilder.class);

    private TileMap tileMap;

    public void acceptLine(String rawLine) {
        String line = rawLine.trim();
        if (line.isEmpty() || line.startsWith("#")) return;

        IOLineType type;
        try {
            type = IOLineType.valueOf(line.substring(0, 1));
        } catch (IllegalArgumentException e) {
            log.warn("Invalid input line: {}", line);
            return;
        }

        Matcher m = type.getPattern().matcher(line);
        if (!m.matches()) return;

        switch (type) {
            case C -> tileMap = new TileMap(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)));
            case M -> ensureMapThenRun(() -> tileMap.setTile(
                    MountainTile.builder()
                            .colX(Integer.parseInt(m.group(1)))
                            .rowY(Integer.parseInt(m.group(2)))
                            .build()));
            case T -> ensureMapThenRun(() -> {
                if(tileMap.getExplorableTile(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2))).isPresent()) {
                    tileMap.setTile(PlainTile.builder()
                            .colX(Integer.parseInt(m.group(1)))
                            .rowY(Integer.parseInt(m.group(2)))
                            .treasureCount(Integer.parseInt(m.group(3)))
                            .build());
                } else {
                    log.warn("Unable to place a treasure in an unexplorable tile");
                }
            });
            case A -> ensureMapThenRun(() -> {
                tileMap.placeOccupantIfExplorableTile(Adventurer.builder()
                        .name(m.group(1))
                        .colX(Integer.parseInt(m.group(2)))
                        .rowY(Integer.parseInt(m.group(3)))
                        .orientation(Orientation.valueOf(m.group(4)))
                        .movementQueue(Movement.queue(m.group(5)))
                        .build());
            });
        }
    }

    public Optional<TileMap> build() {
        if (tileMap == null) {
            log.error("Unable to build TileMap");
            return Optional.empty();
        }
        return Optional.of(tileMap);
    }

    private void ensureMapThenRun(Runnable action) {
        if (tileMap == null)
            log.warn("Unable to run action : no tile map found");
        else
            action.run();
    }
}
