package fr.mdbs.thetreasuremap.domain.model.tilemap;

import fr.mdbs.thetreasuremap.domain.model.movement.Movement;
import fr.mdbs.thetreasuremap.domain.model.adventurer.Orientation;
import lombok.Getter;

import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public enum IOLineType {
    C("^C - (\\d+) - (\\d+)$"),
    M("^M - (\\d+) - (\\d+)$"),
    T("^T - (\\d+) - (\\d+) - (\\d+)$"),
    A(buildAdventurerPattern());

    @Getter
    private final Pattern pattern;

    IOLineType(String regex) {
        this.pattern = Pattern.compile(regex);
    }

    IOLineType(Pattern pattern) {
        this.pattern = pattern;
    }

    public static String formatC(int width, int height) {
        return "C - " + width + " - " + height;
    }

    public static String formatM(int x, int y) {
        return "M - " + x + " - " + y;
    }

    public static String formatT(int x, int y, int treasureCount) {
        return "T - " + x + " - " + y + " - " + treasureCount;
    }

    public static String formatA(String name, int x, int y, Orientation orientation, int treasureCount) {
        return "A - " + name + " - " + x + " - " + y + " - " + orientation.name() + " - " + treasureCount;
    }

    private static Pattern buildAdventurerPattern() {
        String orientations = Arrays.stream(Orientation.values())
                .map(Enum::name).collect(Collectors.joining("|"));
        String movements = Arrays.stream(Movement.values())
                .map(Enum::name).collect(Collectors.joining());
        return Pattern.compile("^A - ([^-]+) - (\\d+) - (\\d+) - (" + orientations + ") - ([" + movements + "]+)$");
    }
}

