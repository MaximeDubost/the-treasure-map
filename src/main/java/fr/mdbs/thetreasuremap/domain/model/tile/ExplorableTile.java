package fr.mdbs.thetreasuremap.domain.model.tile;

import fr.mdbs.thetreasuremap.domain.model.adventurer.Occupant;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
public abstract class ExplorableTile extends Tile implements Explorable, Lootable {
    @Builder.Default
    protected int treasureCount = 0;
    @Builder.Default
    protected Occupant occupant = null;

    public ExplorableTile(int colX, int rowY) {
        super(colX, rowY);
    }

    @Override
    public void clearOccupant() {
        this.occupant = null;
    }

    @Override
    public boolean isOccupied() {
        return this.occupant != null;
    }

    @Override
    public boolean hasAnyTreasure() {
        return this.treasureCount > 0;
    }

    @Override
    public void decreaseTreasureCount() {
        this.treasureCount--;
    }
}
