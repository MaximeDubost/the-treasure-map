package fr.mdbs.thetreasuremap.domain.model.tile;

public interface Lootable {
    boolean hasAnyTreasure();

    void decreaseTreasureCount();
}
