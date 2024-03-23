package net.diana.licentamod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.diana.licentamod.MythicalCreaturesMod;
import net.diana.licentamod.entity.custom.MermaidEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class MermaidRenderer extends GeoEntityRenderer<MermaidEntity> {

    public MermaidRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new MermaidModel());
        this.shadowRadius = 0.3f;
    }

    @Override
    public ResourceLocation getTextureLocation(MermaidEntity instance) {
        return new ResourceLocation(MythicalCreaturesMod.MOD_ID, "textures/entity/mermaid_texture.png");
    }

    @Override
    public RenderType getRenderType(MermaidEntity animatable, float partialTicks, PoseStack stack,
                                    @Nullable MultiBufferSource renderTypeBuffer,
                                    @Nullable VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        stack.scale(0.8f, 0.8f, 0.8f);
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}
