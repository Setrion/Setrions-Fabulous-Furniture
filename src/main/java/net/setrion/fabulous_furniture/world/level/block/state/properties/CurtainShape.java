package net.setrion.fabulous_furniture.world.level.block.state.properties;

import net.minecraft.util.StringRepresentable;

public enum CurtainShape implements StringRepresentable {
    SINGLE("single"),
    TOP("top"),
    BOTTOM("bottom");

    private final String name;

    private CurtainShape(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public String getSerializedName() {
        return this.name;
    }
}
