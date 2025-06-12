package fr.mdbs.thetreasuremap.adapter.inbound;

import fr.mdbs.thetreasuremap.domain.model.tilemap.TileMap;

import java.util.*;

public abstract class FileParser {

    public static Optional<TileMap> parseLines(List<String> lines) {
        TileMapBuilder builder = new TileMapBuilder();
        lines.forEach(builder::acceptLine);
        return builder.build();
    }

}

