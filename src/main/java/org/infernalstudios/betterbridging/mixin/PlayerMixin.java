package org.infernalstudios.betterbridging.mixin;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.infernalstudios.betterbridging.access.LivingEntityAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements LivingEntityAccess {
    @Shadow public abstract boolean isCreative();

    @Unique
    private static final EntityDataAccessor<Integer> SHRINK_COUNT = SynchedEntityData.defineId(Player.class, EntityDataSerializers.INT);

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    private void betterbridging$defineSynchedData(CallbackInfo ci) {
        this.entityData.define(SHRINK_COUNT, 0);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void betterbridging$tick(CallbackInfo ci) {
        if (!this.level.isClientSide) {
            if (this.getShrinkCount() > 0) {
                this.getMainHandItem().shrink(this.getShrinkCount());
                this.setShrinkCount(0);
            }
        }
    }

    @Override
    public int getShrinkCount() {
        return this.entityData.get(SHRINK_COUNT);
    }

    @Override
    public void setShrinkCount(int count) {
        if (!this.isCreative()) this.entityData.set(SHRINK_COUNT, count);
    }
}
