package fr.mdbs.thetreasuremap.domain.model.tile;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public abstract class Tile {
    protected final int colX, rowY;
}
