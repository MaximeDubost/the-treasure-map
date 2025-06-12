package fr.mdbs.thetreasuremap.fileparser;

import fr.mdbs.thetreasuremap.adapter.inbound.FileParser;
import fr.mdbs.thetreasuremap.domain.model.tile.ExplorableTile;
import fr.mdbs.thetreasuremap.domain.model.tile.MountainTile;
import fr.mdbs.thetreasuremap.domain.model.tile.PlainTile;
import fr.mdbs.thetreasuremap.domain.model.tilemap.TileMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@SpringBootTest
public class FileParserTest {

    static Stream<Arguments> badPatternLines() {
        return Stream.of(
                Arguments.of("C- 2 -2"),
                Arguments.of("X - 2 - 2"),
                Arguments.of("C - -1 - -1"),
                Arguments.of("A - Max - 0 - 0 - A - BCEFHIJK"),
                Arguments.of("A - Max - 0 - 0 - N"),
                Arguments.of("T - 0 - 0"),
                Arguments.of("T - 0 - 0 - X")
        );
    }

    @ParameterizedTest(name = "Should not create TileMap (bad pattern: {0})")
    @MethodSource("badPatternLines")
    public void should_notCreateTileMap_when_badPattern(String inputLine) {
        // Given
        List<String> lines = List.of(inputLine);

        // When
        Optional<TileMap> tileMap = FileParser.parseLines(lines);

        // Then
        Assertions.assertTrue(tileMap.isEmpty());
    }

    static Stream<Arguments> noTileMapLines() {
        return Stream.of(
                Arguments.of("M - 1 - 1"),
                Arguments.of("A - Max - 1 - 1 - N - ADG"),
                Arguments.of("T - 1 - 1 - 23")
        );
    }

    @ParameterizedTest(name = "Should ignore when there is no TileMap ({0})")
    @MethodSource("noTileMapLines")
    public void should_ignoreMountainTileLine_when_outOfTileMapCoords(String noTileMapLines) {
        // Given
        List<String> lines = List.of(noTileMapLines);

        // When
        Optional<TileMap> tileMap = FileParser.parseLines(lines);

        // Then
        Assertions.assertFalse(tileMap.isPresent());
    }

    @Test
    public void should_ignoreMountainTileLine_when_outOfTileMapCoords() {
        // Given
        List<String> lines = List.of("C - 1 - 1", "M - 1 - 1");

        // When
        Optional<TileMap> tileMap = FileParser.parseLines(lines);

        // Then
        Assertions.assertTrue(tileMap.isPresent());
        Assertions.assertInstanceOf(PlainTile.class, tileMap.get().getTile(0, 0));
    }

    @Test
    public void should_ignoreAdventurerLine_when_outOfTileMapCoords() {
        // Given
        List<String> lines = List.of("C - 1 - 1", "A - Max - 1 - 1 - N - ADG");

        // When
        Optional<TileMap> tileMap = FileParser.parseLines(lines);

        // Then
        Assertions.assertTrue(tileMap.isPresent());
        Assertions.assertFalse(tileMap.get().hasAnyOccupant());
    }

    @Test
    public void should_ignoreTreasureLine_when_outOfTileMapCoords() {
        // Given
        List<String> lines = List.of("C - 1 - 1", "T - 1 - 1 - 23");

        // When
        Optional<TileMap> tileMap = FileParser.parseLines(lines);

        // Then
        Assertions.assertTrue(tileMap.isPresent());
        Assertions.assertEquals(0, tileMap.get().getTotalTreasureCount());
    }

    @Test
    public void should_ignoreMountainTileLine_when_instructionBeforeTileMapCreation() {
        // Given
        List<String> lines = List.of("M - 0 - 0", "C - 1 - 1");

        // When
        Optional<TileMap> tileMap = FileParser.parseLines(lines);

        // Then
        Assertions.assertTrue(tileMap.isPresent());
        Assertions.assertInstanceOf(PlainTile.class, tileMap.get().getTile(0, 0));
    }

    @Test
    public void should_ignoreAdventurerLine_when_instructionBeforeTileMapCreation() {
        // Given
        List<String> lines = List.of("A - Max - 0 - 0 - N - ADG", "C - 1 - 1");

        // When
        Optional<TileMap> tileMap = FileParser.parseLines(lines);

        // Then
        Assertions.assertTrue(tileMap.isPresent());
        Assertions.assertFalse(tileMap.get().hasAnyOccupant());
    }

    @Test
    public void should_ignoreTreasureLine_when_instructionBeforeTileMapCreation() {
        // Given
        List<String> lines = List.of("T - 0 - 0 - 1", "C - 1 - 1");

        // When
        Optional<TileMap> tileMap = FileParser.parseLines(lines);

        // Then
        Assertions.assertTrue(tileMap.isPresent());
        Assertions.assertEquals(0, tileMap.get().getTotalTreasureCount());
    }

    @Test
    public void should_ignoreLine_when_onlyCommentaryLine() {
        // Given
        List<String> lines = List.of("# C - 2 - 2");

        // When
        Optional<TileMap> tileMap = FileParser.parseLines(lines);

        // Then
        Assertions.assertTrue(tileMap.isEmpty());
    }

    @Test
    public void should_create2x2TileMap() {
        // Given
        List<String> lines = List.of("C - 2 - 2");

        // When
        Optional<TileMap> tileMap = FileParser.parseLines(lines);

        // Then
        Assertions.assertTrue(tileMap.isPresent());
        Assertions.assertEquals(2, tileMap.get().getWidth());
        Assertions.assertEquals(2, tileMap.get().getHeight());
        Assertions.assertInstanceOf(PlainTile.class, tileMap.get().getTile(0, 0));
        Assertions.assertInstanceOf(PlainTile.class, tileMap.get().getTile(0, 1));
        Assertions.assertInstanceOf(PlainTile.class, tileMap.get().getTile(1, 0));
        Assertions.assertInstanceOf(PlainTile.class, tileMap.get().getTile(1, 1));
    }

    @Test
    public void should_create10x100TileMap() {
        // Given
        List<String> lines = List.of("C - 10 - 100");

        // When
        Optional<TileMap> tileMap = FileParser.parseLines(lines);

        // Then
        Assertions.assertTrue(tileMap.isPresent());
        Assertions.assertEquals(10, tileMap.get().getWidth());
        Assertions.assertEquals(100, tileMap.get().getHeight());
    }

    @Test
    public void should_create2x2TileMap_with_mountainTileAtZeroZero() {
        // Given
        List<String> lines = List.of("C - 2 - 2", "M - 0 - 0");

        // When
        Optional<TileMap> tileMap = FileParser.parseLines(lines);

        // Then
        Assertions.assertTrue(tileMap.isPresent());
        Assertions.assertEquals(2, tileMap.get().getWidth());
        Assertions.assertEquals(2, tileMap.get().getHeight());
        Assertions.assertInstanceOf(MountainTile.class, tileMap.get().getTile(0, 0));
        Assertions.assertInstanceOf(PlainTile.class, tileMap.get().getTile(0, 1));
        Assertions.assertInstanceOf(PlainTile.class, tileMap.get().getTile(1, 0));
        Assertions.assertInstanceOf(PlainTile.class, tileMap.get().getTile(1, 1));
    }

    @Test
    public void should_notPlaceAdventurer_when_startPointIsMountainTile() {
        // Given
        List<String> lines = List.of("C - 2 - 2", "M - 0 - 0", "A - Max - 0 - 0 - E - ADG");

        // When
        Optional<TileMap> tileMap = FileParser.parseLines(lines);

        // Then
        Assertions.assertTrue(tileMap.isPresent());
        Assertions.assertInstanceOf(MountainTile.class, tileMap.get().getTile(0, 0));
        Assertions.assertFalse(tileMap.get().getTile(0, 0) instanceof ExplorableTile);
    }

    @Test
    public void should_placeTreasure() {
        // Given
        List<String> lines = List.of("C - 1 - 1", "T - 0 - 0 - 1");

        // When
        Optional<TileMap> tileMap = FileParser.parseLines(lines);

        // Then
        Assertions.assertTrue(tileMap.isPresent());
        Assertions.assertInstanceOf(PlainTile.class, tileMap.get().getTile(0, 0));
        Assertions.assertTrue(tileMap.get().getExplorableTile(0, 0).isPresent());
        Assertions.assertEquals(1, tileMap.get().getExplorableTile(0, 0).get().getTreasureCount());
    }

    @Test
    public void should_eraseTreasure_when_overwriteWithMountainTile() {
        // Given
        List<String> lines = List.of("C - 1 - 1", "T - 0 - 0 - 1", "M - 0 - 0");

        // When
        Optional<TileMap> tileMap = FileParser.parseLines(lines);

        // Then
        Assertions.assertTrue(tileMap.isPresent());
        Assertions.assertInstanceOf(MountainTile.class, tileMap.get().getTile(0, 0));
        Assertions.assertFalse(tileMap.get().getTile(0, 0) instanceof ExplorableTile);
        Assertions.assertFalse(tileMap.get().getExplorableTile(0, 0).isPresent());
        Assertions.assertEquals(0, tileMap.get().getTotalTreasureCount());
    }

    // TODO : test ignore everything before map creation

    @Test
    public void should_notPlaceTreasure_when_isMountainTile() {
        // Given
        List<String> lines = List.of("C - 1 - 1", "M - 0 - 0", "T - 0 - 0 - 1");

        // When
        Optional<TileMap> tileMap = FileParser.parseLines(lines);

        // Then
        Assertions.assertTrue(tileMap.isPresent());
        Assertions.assertInstanceOf(MountainTile.class, tileMap.get().getTile(0, 0));
        Assertions.assertFalse(tileMap.get().getTile(0, 0) instanceof ExplorableTile);
        Assertions.assertFalse(tileMap.get().getExplorableTile(0, 0).isPresent());
        Assertions.assertEquals(0, tileMap.get().getTotalTreasureCount());
    }

}
