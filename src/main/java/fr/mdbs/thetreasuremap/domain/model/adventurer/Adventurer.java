package fr.mdbs.thetreasuremap.domain.model.adventurer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public final class Adventurer implements Occupant {
    private final String name;
    private int colX, rowY;
    @Setter
    private Orientation orientation;

    public void setPosition(int x, int y) {
        this.colX = x;
        this.rowY = y;
    }
}
