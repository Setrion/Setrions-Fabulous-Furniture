package net.setrion.fabulous_furniture.world.level.block.state.properties;

import net.minecraft.util.StringRepresentable;

public enum BedShape implements StringRepresentable {
    SINGLE("single"),
    LEFT("left"),
    RIGHT("right"),
    MIDDLE("middle");

    private final String name;

    private BedShape(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public String getSerializedName() {
        return this.name;
    }
}
