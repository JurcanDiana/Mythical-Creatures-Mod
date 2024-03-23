package net.diana.licentamod.entity.client;

import net.diana.licentamod.MythicalCreaturesMod;
import net.diana.licentamod.entity.custom.CentaurEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CentaurModel extends AnimatedGeoModel<CentaurEntity> {

    @Override
    public ResourceLocation getModelResource(CentaurEntity centaurEntity) {
        return new ResourceLocation(MythicalCreaturesMod.MOD_ID, "geo/centaur.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(CentaurEntity centaurEntity) {
        return new ResourceLocation(MythicalCreaturesMod.MOD_ID, "textures/entity/centaur_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(CentaurEntity centaurEntity) {
        return new ResourceLocation(MythicalCreaturesMod.MOD_ID, "animations/centaur.animation.json");
    }
}
