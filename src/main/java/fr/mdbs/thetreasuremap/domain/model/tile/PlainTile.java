package fr.mdbs.thetreasuremap.domain.model.tile;

import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public final class PlainTile extends ExplorableTile {
    public PlainTile(int colX, int rowY) {
        super(colX, rowY);
    }
}
