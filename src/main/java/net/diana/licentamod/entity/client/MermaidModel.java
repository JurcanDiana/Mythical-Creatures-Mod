package net.diana.licentamod.entity.client;

import net.diana.licentamod.MythicalCreaturesMod;
import net.diana.licentamod.entity.custom.MermaidEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MermaidModel extends AnimatedGeoModel<MermaidEntity> {

    @Override
    public ResourceLocation getModelResource(MermaidEntity mermaidEntity) {
        return new ResourceLocation(MythicalCreaturesMod.MOD_ID, "geo/mermaid.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MermaidEntity mermaidEntity) {
        return new ResourceLocation(MythicalCreaturesMod.MOD_ID, "textures/entity/mermaid_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(MermaidEntity mermaidEntity) {
        return new ResourceLocation(MythicalCreaturesMod.MOD_ID, "animations/mermaid.animation.json");
    }
}
