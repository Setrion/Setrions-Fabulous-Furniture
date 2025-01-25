package net.setrion.fabulous_furniture.util;

import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

/*
    Original Author: XFactHD
 */

public class VoxelShapeUtils {

    public static VoxelShape orUnoptimized(VoxelShape first, VoxelShape second) {
        return Shapes.joinUnoptimized(first, second, BooleanOp.OR);
    }

    public static VoxelShape orUnoptimized(VoxelShape first, VoxelShape... others) {
        for (VoxelShape shape : others) {
            first = VoxelShapeUtils.orUnoptimized(first, shape);
        }
        return first;
    }

    public static VoxelShape rotateShapeAroundY(Direction from, Direction to, VoxelShape shape) {
        return rotateShapeUnoptimizedAroundY(from, to, shape).optimize();
    }

    public static VoxelShape rotateShapeUnoptimizedAroundY(Direction from, Direction to, VoxelShape shape) {
        if (isY(from) || isY(to)) {
            throw new IllegalArgumentException("Invalid Direction!");
        }
        if (from == to) {
            return shape;
        }

        List<AABB> sourceBoxes = shape.toAabbs();
        VoxelShape rotatedShape = Shapes.empty();
        int times = (to.get2DDataValue() - from.get2DDataValue() + 4) % 4;
        for (AABB box : sourceBoxes) {
            for (int i = 0; i < times; i++) {
                box = new AABB(1 - box.maxZ, box.minY, box.minX, 1 - box.minZ, box.maxY, box.maxX);
            }
            rotatedShape = orUnoptimized(rotatedShape, Shapes.create(box));
        }

        return rotatedShape;
    }

    public static boolean isX(Direction dir) {
        return dir.getAxis() == Direction.Axis.X;
    }

    public static boolean isY(Direction dir) {
        return dir.getAxis() == Direction.Axis.Y;
    }

    public static boolean isZ(Direction dir) {
        return dir.getAxis() == Direction.Axis.Z;
    }
}