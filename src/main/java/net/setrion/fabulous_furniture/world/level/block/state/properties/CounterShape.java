package net.setrion.fabulous_furniture.world.level.block.state.properties;

import net.minecraft.util.StringRepresentable;

public enum CounterShape implements StringRepresentable {
    STRAIGHT("straight"),
    INNER_LEFT("inner_left"),
    INNER_RIGHT("inner_right"),
    OUTER_LEFT("outer_left"),
    OUTER_RIGHT("outer_right");

    private final String name;

    private CounterShape(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public String getSerializedName() {
        return this.name;
    }
}
