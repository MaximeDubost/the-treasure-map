package fr.mdbs.thetreasuremap.tilemap;

import fr.mdbs.thetreasuremap.domain.model.tile.PlainTile;
import fr.mdbs.thetreasuremap.domain.model.tile.Tile;
import fr.mdbs.thetreasuremap.domain.model.tilemap.TileMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TileMapTest {

    @Test
    public void should_createTileMap() {
        TileMap tileMap = new TileMap(2, 2);

        Tile expectedTile1 = new PlainTile(0, 0);
        Tile expectedTile2 = new PlainTile(0, 1);
        Tile expectedTile3 = new PlainTile(1, 0);
        Tile expectedTile4 = new PlainTile(1, 1);

        Assertions.assertEquals(tileMap.getTile(0, 0), expectedTile1);
        Assertions.assertEquals(tileMap.getTile(0, 1), expectedTile2);
        Assertions.assertEquals(tileMap.getTile(1, 0), expectedTile3);
        Assertions.assertEquals(tileMap.getTile(1, 1), expectedTile4);
    }
}
