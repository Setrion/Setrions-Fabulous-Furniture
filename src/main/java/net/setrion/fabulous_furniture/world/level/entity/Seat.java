package net.setrion.fabulous_furniture.world.level.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.setrion.fabulous_furniture.registry.SFFEntityTypes;

import javax.annotation.Nullable;

public class Seat extends Entity {

    public Seat(EntityType<? extends Seat> type, Level level) {
        super(type, level);
    }

    private Seat(Level level, BlockPos pos, double seatHeight, float seatYaw) {
        this(SFFEntityTypes.SEAT.get(), level);
        this.setPos(Vec3.atBottomCenterOf(pos).add(0, seatHeight, 0));
        this.setRot(seatYaw, 0);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {}

    @Override
    public void tick() {
        super.tick();
        Level level = level();
        if(!level.isClientSide()) {
            BlockPos pos = this.blockPosition();
            if(this.getPassengers().isEmpty() || level.isEmptyBlock(pos)) {
                this.discard();
                level.updateNeighbourForOutputSignal(pos, level.getBlockState(pos).getBlock());
            }
        }
    }

    @Override
    public boolean hurtServer(ServerLevel serverLevel, DamageSource damageSource, float v) {
        return false;
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {}

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {}

    @Override
    protected void addPassenger(Entity entity) {
        super.addPassenger(entity);
        entity.setYRot(this.getYRot());
    }

    @Override
    public void onPassengerTurned(Entity entity) {
        this.clampPassengerYaw(entity);
    }

    @Override
    public Vec3 getDismountLocationForPassenger(LivingEntity entity) {
        Direction front = this.getDirection();
        Direction[] sides = {front, front.getClockWise(), front.getCounterClockWise(), front.getOpposite()};
        for(Direction side : sides) {
            Vec3 pos = DismountHelper.findSafeDismountLocation(entity.getType(), this.level(), this.blockPosition().relative(side), false);
            if(pos != null) {
                return pos.add(0, 0.25, 0);
            }
        }
        return super.getDismountLocationForPassenger(entity);
    }

    private void clampPassengerYaw(Entity entity) {
        entity.setYBodyRot(this.getYRot());
        float wrappedYaw = Mth.wrapDegrees(entity.getYRot() - this.getYRot());
        float clampedYaw = Mth.clamp(wrappedYaw, -120, 120);
        entity.yRotO += clampedYaw - wrappedYaw;
        entity.setYRot(entity.getYRot() + clampedYaw - wrappedYaw);
        entity.setYHeadRot(entity.getYRot());
    }

    public static boolean sit(Player player, BlockPos pos, double seatHeight, @Nullable Direction direction) {
        Level level = player.level();
        if(!level.isClientSide() && availableAt(level, pos)) {
            float seatYaw = direction != null ? direction.toYRot() : player.getYRot();
            Seat seat = new Seat(level, pos, seatHeight, seatYaw);
            level.addFreshEntity(seat);
            return player.startRiding(seat);
        }
        return false;
    }
    public static boolean availableAt(Level level, BlockPos pos) {
        return level.getEntitiesOfClass(Seat.class, new AABB(pos)).isEmpty();
    }
}