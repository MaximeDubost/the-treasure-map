package fr.mdbs.thetreasuremap.domain.model.tile;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class ExplorableTile extends Tile implements Explorable {
    protected int treasureCount = 0;
    protected boolean isOccupied = false;

    public ExplorableTile(int colX, int rowY) {
        super(colX, rowY);
    }
}
