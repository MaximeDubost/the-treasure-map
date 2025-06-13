package fr.mdbs.thetreasuremap.domain.model.movement;

import java.util.*;
import java.util.stream.Collectors;

public enum Movement {
    A,
    G,
    D;

    public static Queue<Movement> queue(String input) {
        Set<Character> movementSet = Arrays.stream(Movement.values())
                .map(m -> m.name().charAt(0))
                .collect(Collectors.toSet());

        return input.replaceAll("\\s", "")
                .chars()
                .mapToObj(c -> (char) c)
                .filter(movementSet::contains)
                .map(c -> Movement.valueOf(String.valueOf(c)))
                .collect(Collectors.toCollection(LinkedList::new));
    }

}
