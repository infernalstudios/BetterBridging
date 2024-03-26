package org.infernalstudios.betterbridging.mixin;

import net.minecraft.core.Direction;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.infernalstudios.betterbridging.access.LivingEntityAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements LivingEntityAccess {

    @Unique
    private static final EntityDataAccessor<Direction> DIRECTION = SynchedEntityData.defineId(Player.class, EntityDataSerializers.DIRECTION);

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    private void betterbridging$defineSynchedData(CallbackInfo ci) {
        this.entityData.define(DIRECTION, Direction.NORTH);
    }

    @Override
    public Direction getDirection() {
        return this.entityData.get(DIRECTION);
    }

    @Override
    public void setDirection(Direction direction) {
        this.entityData.set(DIRECTION, direction);
    }
}