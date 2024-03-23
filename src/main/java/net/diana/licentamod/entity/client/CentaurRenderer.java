package net.diana.licentamod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.diana.licentamod.MythicalCreaturesMod;
import net.diana.licentamod.entity.custom.CentaurEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class CentaurRenderer extends GeoEntityRenderer<CentaurEntity> {
    public CentaurRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new CentaurModel());
        this.shadowRadius = 0.3f;
    }

    @Override
    public ResourceLocation getTextureLocation(CentaurEntity instance) {
        return new ResourceLocation(MythicalCreaturesMod.MOD_ID, "textures/entity/centaur_texture.png");
    }

    @Override
    public RenderType getRenderType(CentaurEntity animatable, float partialTicks, PoseStack stack,
                                    @Nullable MultiBufferSource renderTypeBuffer,
                                    @Nullable VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        stack.scale(0.8f, 0.8f, 0.8f);
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }

    @Override
    public void render(CentaurEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);

        ItemStack itemStack = entity.getMainHandItem();
        if (!itemStack.isEmpty()) {
            renderHeldItem(entity, itemStack, ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, poseStack, bufferSource, packedLight);
        }
    }

    private void renderHeldItem(CentaurEntity entity, ItemStack itemStack, ItemTransforms.TransformType transformType,
                                PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();

        poseStack.translate(0.0D, 1.0D, -0.8D);
        poseStack.scale(0.75F, 0.75F, 0.75F);
        poseStack.mulPose(Vector3f.XP.rotationDegrees(-15));


        Minecraft.getInstance().getItemRenderer().renderStatic(itemStack, transformType, packedLight, OverlayTexture.NO_OVERLAY, poseStack, bufferSource, entity.getId());

        poseStack.popPose();
    }

}
