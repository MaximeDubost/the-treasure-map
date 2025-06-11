package fr.mdbs.thetreasuremap.adapter.inbound;

import fr.mdbs.thetreasuremap.domain.model.adventurer.Adventurer;
import fr.mdbs.thetreasuremap.domain.model.adventurer.Movement;
import fr.mdbs.thetreasuremap.domain.model.adventurer.Orientation;
import fr.mdbs.thetreasuremap.domain.model.tile.MountainTile;
import fr.mdbs.thetreasuremap.domain.model.tile.PlainTile;
import fr.mdbs.thetreasuremap.domain.model.tilemap.InputLineType;
import fr.mdbs.thetreasuremap.domain.model.tilemap.TileMap;

import java.util.regex.Matcher;

public class TileMapBuilder {
    private TileMap tileMap;

    public void acceptLine(String rawLine) {
        String line = rawLine.trim();
        if (line.isEmpty() || line.startsWith("#")) return;

        InputLineType type = InputLineType.valueOf(line.substring(0, 1)); // TODO : handle IllegalArgumentException
        Matcher matcher = type.getPattern().matcher(line);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("Line does not match pattern for type " + type + ": " + line);
        }

        switch (type) {
            case C -> tileMap = new TileMap(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
            case M -> ensureMapThenRun(() -> tileMap.setTile(
                    MountainTile.builder()
                            .colX(Integer.parseInt(matcher.group(1)))
                            .rowY(Integer.parseInt(matcher.group(2)))
                            .build()));
            case T -> ensureMapThenRun(() -> tileMap.setTile(
                    PlainTile.builder()
                            .colX(Integer.parseInt(matcher.group(1)))
                            .rowY(Integer.parseInt(matcher.group(2)))
                            .treasureCount(Integer.parseInt(matcher.group(3)))
                            .build()));
            case A -> ensureMapThenRun(() -> {
                Adventurer adventurer = Adventurer.builder()
                        .name(matcher.group(1))
                        .colX(Integer.parseInt(matcher.group(2)))
                        .rowY(Integer.parseInt(matcher.group(3)))
                        .orientation(Orientation.valueOf(matcher.group(4)))
                        .movementQueue(Movement.queue(matcher.group(5)))
                        .build();
                tileMap.placeOccupantIfExplorableTile(adventurer);
            });
        }
    }

    public TileMap build() {
        if (tileMap == null)
            throw new IllegalStateException("No map defined in input");
        return tileMap;
    }

    private void ensureMapThenRun(Runnable action) {
        if (tileMap == null)
            throw new IllegalStateException("TileMap must be defined before this line");
        action.run();
    }
}
