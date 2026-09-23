package com.YMhmD.amputatedragdolls.mixin;

import com.YMhmD.amputatedragdolls.gwo.GwoShotTracker;
import com.sgr792.gwo.entity.projectile.BulletEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = BulletEntity.class, remap = false)
public class GwoBulletMixin {
    @Inject(method = "isHeadshot(Lnet/minecraft/world/entity/Entity;Lcom/sgr792/gwo/entity/projectile/PlayerBodyHitboxResolver$BodyPart;Lnet/minecraft/world/phys/Vec3;)Z", at = @At("RETURN"))
    private void captureHit(Entity target, @Coerce Object bodyPart, Vec3 location, CallbackInfoReturnable<Boolean> callback) {
        GwoShotTracker.record((Entity) (Object) this, target, callback.getReturnValue());
    }

    @Inject(method = "onHitEntity(Lcom/sgr792/gwo/entity/projectile/BulletEntity$EntityImpact;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;)V", at = @At("RETURN"))
    private void clearHit(@Coerce Object impact, Vec3 segmentStart, Vec3 velocity, CallbackInfo callback) {
        GwoShotTracker.clear();
    }
}
