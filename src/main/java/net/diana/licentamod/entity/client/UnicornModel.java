package net.diana.licentamod.entity.client;

import net.diana.licentamod.MythicalCreaturesMod;
import net.diana.licentamod.entity.custom.UnicornEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class UnicornModel extends AnimatedGeoModel<UnicornEntity> {
    @Override
    public ResourceLocation getModelResource(UnicornEntity unicornEntity) {
        return new ResourceLocation(MythicalCreaturesMod.MOD_ID, "geo/unicorn.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(UnicornEntity unicornEntity) {
        return new ResourceLocation(MythicalCreaturesMod.MOD_ID, "textures/entity/unicorn_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(UnicornEntity unicornEntity) {
        return new ResourceLocation(MythicalCreaturesMod.MOD_ID, "animations/unicorn.animation.json");
    }
}
