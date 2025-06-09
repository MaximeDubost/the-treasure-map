package fr.mdbs.thetreasuremap.domain.model.adventurer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
public final class Adventurer implements Occupant {
    private final String name;
    private int colX, rowY;
    @Setter
    private Orientation orientation;

    public Adventurer(String name, int colX, int rowY, Orientation orientation) {
        this.name = name;
        this.colX = Math.max(colX, 0);
        this.rowY = Math.max(rowY, 0);
        this.orientation = orientation;
    }

    public void setPosition(int x, int y) {
        this.colX = x;
        this.rowY = y;
    }
}
