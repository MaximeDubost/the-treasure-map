package fr.mdbs.thetreasuremap.domain.model.tile;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@SuperBuilder
public abstract class Tile {
    protected final int colX, rowY;
}
