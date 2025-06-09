package fr.mdbs.thetreasuremap.domain.model.tile;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public final class MountainTile extends Tile {
    public MountainTile(int colX, int rowY) {
        super(colX, rowY);
    }
}
