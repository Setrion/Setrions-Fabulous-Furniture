package net.setrion.fabulous_furniture.world.level.block.state.properties;

import net.minecraft.util.StringRepresentable;

public enum ShelfShape implements StringRepresentable {
    SINGLE("single"),
    LEFT("left"),
    RIGHT("right"),
    MIDDLE("middle");

    private final String name;

    private ShelfShape(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public String getSerializedName() {
        return this.name;
    }
}
