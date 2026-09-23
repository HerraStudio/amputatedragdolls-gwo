package com.YMhmD.amputatedragdolls.mixin;

import com.YMhmD.amputatedragdolls.AmputationHelper;
import com.YMhmD.amputatedragdolls.AmputationTracker;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.raiiiden.ragdollified.RagdollPart;
import com.raiiiden.ragdollified.RagdollTransform;
import com.raiiiden.ragdollified.client.ClientRagdoll;
import com.raiiiden.ragdollified.client.ClientRagdollRenderer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ClientRagdollRenderer.class, remap = false)
public class ClientRagdollRendererMixin {
    private static int amputatedragdolls$currentEntityId = -1;

    @Inject(method = "renderMobRagdoll", at = @At("HEAD"))
    private static void captureEntityId(ClientRagdoll ragdoll, ClientRagdoll.TransformSnapshot snapshot, PoseStack pose,
                                        MultiBufferSource buffer, int light, float partialTick, double distance, CallbackInfo callback) {
        amputatedragdolls$currentEntityId = ragdoll.getOriginalEntityId();
    }

    @Inject(method = "renderMobRagdoll", at = @At("RETURN"))
    private static void clearEntityId(ClientRagdoll ragdoll, ClientRagdoll.TransformSnapshot snapshot, PoseStack pose,
                                      MultiBufferSource buffer, int light, float partialTick, double distance, CallbackInfo callback) {
        amputatedragdolls$currentEntityId = -1;
    }

    @Inject(method = "renderHumanoidPartPhysics(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;Lnet/minecraft/client/model/geom/ModelPart;Lcom/raiiiden/ragdollified/RagdollTransform;Lcom/raiiiden/ragdollified/RagdollTransform;ILcom/raiiiden/ragdollified/RagdollPart;)V", at = @At("HEAD"), cancellable = true)
    private static void hidePart(PoseStack pose, VertexConsumer consumer, ModelPart model, RagdollTransform transform,
                                 RagdollTransform torso, int light, RagdollPart part, CallbackInfo callback) {
        if (isAmputated(part)) callback.cancel();
    }

    @Inject(method = "renderHumanoidPartPhysics(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;Lnet/minecraft/client/model/geom/ModelPart;Lcom/raiiiden/ragdollified/RagdollTransform;Lcom/raiiiden/ragdollified/RagdollTransform;ILcom/raiiiden/ragdollified/RagdollPart;Lcom/raiiiden/ragdollified/client/ClientRagdollRenderer$HumanoidScale;)V", at = @At("HEAD"), cancellable = true)
    private static void hideScaledPart(PoseStack pose, VertexConsumer consumer, ModelPart model, RagdollTransform transform,
                                       RagdollTransform torso, int light, RagdollPart part, @Coerce Object scale, CallbackInfo callback) {
        if (isAmputated(part)) callback.cancel();
    }

    @Inject(method = "renderHumanoidPartPhysicsTinted(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;Lnet/minecraft/client/model/geom/ModelPart;Lcom/raiiiden/ragdollified/RagdollTransform;Lcom/raiiiden/ragdollified/RagdollTransform;ILcom/raiiiden/ragdollified/RagdollPart;FFFF)V", at = @At("HEAD"), cancellable = true)
    private static void hideTintedPart(PoseStack pose, VertexConsumer consumer, ModelPart model, RagdollTransform transform,
                                       RagdollTransform torso, int light, RagdollPart part, float red, float green, float blue,
                                       float alpha, CallbackInfo callback) {
        if (isAmputated(part)) callback.cancel();
    }

    @Inject(method = "renderHumanoidPartPhysicsTinted(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;Lnet/minecraft/client/model/geom/ModelPart;Lcom/raiiiden/ragdollified/RagdollTransform;Lcom/raiiiden/ragdollified/RagdollTransform;ILcom/raiiiden/ragdollified/RagdollPart;FFFFLcom/raiiiden/ragdollified/client/ClientRagdollRenderer$HumanoidScale;)V", at = @At("HEAD"), cancellable = true)
    private static void hideScaledTintedPart(PoseStack pose, VertexConsumer consumer, ModelPart model, RagdollTransform transform,
                                             RagdollTransform torso, int light, RagdollPart part, float red, float green, float blue,
                                             float alpha, @Coerce Object scale, CallbackInfo callback) {
        if (isAmputated(part)) callback.cancel();
    }

    private static boolean isAmputated(RagdollPart part) {
        Integer mask = AmputationTracker.MISSING_LIMBS.get(amputatedragdolls$currentEntityId);
        return mask != null && AmputationHelper.isLimbAmputated(mask, part);
    }
}
