package net.diana.licentamod.entity;

import net.diana.licentamod.MythicalCreaturesMod;
import net.diana.licentamod.entity.custom.CentaurEntity;
import net.diana.licentamod.entity.custom.MermaidEntity;
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
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MythicalCreaturesMod.MOD_ID);
    public static final RegistryObject<EntityType<UnicornEntity>> UNICORN =
            ENTITY_TYPES.register("unicorn",
                    () -> EntityType.Builder.of(UnicornEntity::new, MobCategory.CREATURE)
                            .sized(1.0f, 2.0f)
                            .build(new ResourceLocation(MythicalCreaturesMod.MOD_ID, "unicorn").toString()));

    public static final RegistryObject<EntityType<MermaidEntity>> MERMAID =
            ENTITY_TYPES.register("mermaid",
                    () -> EntityType.Builder.of(MermaidEntity::new, MobCategory.CREATURE)
                            .sized(1.0f, 2.0f)
                            .build(new ResourceLocation(MythicalCreaturesMod.MOD_ID, "mermaid").toString()));

    public static final RegistryObject<EntityType<CentaurEntity>> CENTAUR =
            ENTITY_TYPES.register("centaur",
                    () -> EntityType.Builder.of(CentaurEntity::new, MobCategory.CREATURE)
                            .sized(1.0f, 2.0f)
                            .build(new ResourceLocation(MythicalCreaturesMod.MOD_ID, "centaur").toString()));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
