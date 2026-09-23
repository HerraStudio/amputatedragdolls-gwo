package com.YMhmD.amputatedragdolls.mixin;

import com.YMhmD.amputatedragdolls.AmputationTracker;
import com.astryxion.mobamputation.client.core.EventHandlerClient;
import com.astryxion.mobamputation.common.Limb;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EventHandlerClient.class, remap = false)
public class AmputationBridgeMixin {
    @Inject(method = "handleDetachPacket(II)V", at = @At("HEAD"))
    private static void onLimbDetached(int entityId, int limbId, CallbackInfo callback) {
        Limb limb = Limb.fromIndex(limbId);
        if (limb != null) {
            AmputationTracker.MISSING_LIMBS.merge(entityId, limb.bit(), (oldMask, newBit) -> oldMask | newBit);
        }
    }
}
