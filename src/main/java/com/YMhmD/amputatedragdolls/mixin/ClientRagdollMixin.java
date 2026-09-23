package com.YMhmD.amputatedragdolls.mixin;

import com.YMhmD.amputatedragdolls.AmputationHelper;
import com.YMhmD.amputatedragdolls.AmputationTracker;
import com.raiiiden.ragdollified.RagdollPart;
import com.raiiiden.ragdollified.client.ClientPhysicsWorld;
import com.raiiiden.ragdollified.client.ClientRagdoll;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ClientRagdoll.class, remap = false)
public class ClientRagdollMixin {
    @Shadow private int originalEntityId;

    @Inject(method = "<init>(Lcom/raiiiden/ragdollified/client/ClientRagdoll$SpawnData;Lcom/raiiiden/ragdollified/client/ClientPhysicsWorld;)V", at = @At("RETURN"))
    private void applyAmputation(ClientRagdoll.SpawnData data, ClientPhysicsWorld world, CallbackInfo callback) {
        Integer mask = AmputationTracker.MISSING_LIMBS.get(data.originalEntityId);
        if (mask == null) return;
        ClientRagdoll ragdoll = (ClientRagdoll) (Object) this;
        for (RagdollPart part : RagdollPart.values()) {
            if (AmputationHelper.isLimbAmputated(mask, part)) ragdoll.severPart(part);
        }
    }

    @Inject(method = "destroy", at = @At("HEAD"))
    private void cleanUp(CallbackInfo callback) {
        AmputationTracker.MISSING_LIMBS.remove(originalEntityId);
    }
}
