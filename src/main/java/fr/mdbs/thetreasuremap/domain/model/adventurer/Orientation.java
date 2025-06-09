package fr.mdbs.thetreasuremap.domain.model.adventurer;

import lombok.Getter;

@Getter
public enum Orientation {
    N(0, -1),
    E(1, 0),
    S(0, 1),
    O(-1, 0);

    private final int dx;
    private final int dy;

    Orientation(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public Orientation turnLeft() {
        return values()[(this.ordinal() + 3) % 4];
    }

    public Orientation turnRight() {
        return values()[(this.ordinal() + 1) % 4];
    }
}
