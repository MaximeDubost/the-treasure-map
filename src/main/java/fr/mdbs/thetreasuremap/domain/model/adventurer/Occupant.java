package fr.mdbs.thetreasuremap.domain.model.adventurer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
public abstract class Occupant {
    protected int colX, rowY;

    public void setPosition(int x, int y) {
        this.colX = x;
        this.rowY = y;
    }
}
