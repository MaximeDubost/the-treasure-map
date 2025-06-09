package fr.mdbs.thetreasuremap.domain.model.tile;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public final class PlainTile extends ExplorableTile {
    public PlainTile(int colX, int rowY) {
        super(colX, rowY);
    }
}
