package net.diana.licentamod.entity;

import net.diana.licentamod.TutorialMod;
import net.diana.licentamod.entity.custom.ChomperEntity;
import net.diana.licentamod.entity.custom.UnicornEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, TutorialMod.MOD_ID);

    public static final RegistryObject<EntityType<ChomperEntity>> CHOMPER =
            ENTITY_TYPES.register("chomper",
                    () -> EntityType.Builder.of(ChomperEntity::new, MobCategory.MONSTER)
                            .sized(0.4f, 1.5f)
                            .build(new ResourceLocation(TutorialMod.MOD_ID, "chomper").toString()));

    public static final RegistryObject<EntityType<UnicornEntity>> UNICORN =
            ENTITY_TYPES.register("unicorn",
                    () -> EntityType.Builder.of(UnicornEntity::new, MobCategory.CREATURE)
                            .sized(1.0f, 2.0f)
                            .build(new ResourceLocation(TutorialMod.MOD_ID, "unicorn").toString()));


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}