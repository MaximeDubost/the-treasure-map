package fr.mdbs.thetreasuremap.domain.model.tilemap;

import fr.mdbs.thetreasuremap.domain.model.adventurer.Movement;
import fr.mdbs.thetreasuremap.domain.model.adventurer.Orientation;
import lombok.Getter;

import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public enum InputLineType {
    C("^C - (\\d+) - (\\d+)$"),
    M("^M - (\\d+) - (\\d+)$"),
    T("^T - (\\d+) - (\\d+) - (\\d+)$"),
    A(buildAdventurerPattern());

    @Getter
    private final Pattern pattern;

    InputLineType(String regex) {
        this.pattern = Pattern.compile(regex);
    }

    InputLineType(Pattern pattern) {
        this.pattern = pattern;
    }

    private static Pattern buildAdventurerPattern() {
        String orientations = Arrays.stream(Orientation.values())
                .map(Enum::name).collect(Collectors.joining("|"));
        String movements = Arrays.stream(Movement.values())
                .map(Enum::name).collect(Collectors.joining());
        return Pattern.compile("^A - ([^-]+) - (\\d+) - (\\d+) - (" + orientations + ") - ([" + movements + "]+)$");
    }
}

