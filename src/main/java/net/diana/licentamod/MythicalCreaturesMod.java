package net.diana.licentamod;

import com.mojang.logging.LogUtils;
import net.diana.licentamod.block.ModBlocks;
import net.diana.licentamod.entity.ModEntityTypes;
import net.diana.licentamod.entity.client.CentaurRenderer;
import net.diana.licentamod.entity.client.MermaidRenderer;
import net.diana.licentamod.entity.client.UnicornRenderer;
import net.diana.licentamod.item.ModItems;
import net.diana.licentamod.sound.ModSounds;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import software.bernie.geckolib3.GeckoLib;

@Mod(MythicalCreaturesMod.MOD_ID)
public class MythicalCreaturesMod
{
    public static final String MOD_ID = "licentamod";
    private static final Logger LOGGER = LogUtils.getLogger();

    public MythicalCreaturesMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModSounds.register(modEventBus);
        ModEntityTypes.ENTITY_TYPES.register(modEventBus);

        GeckoLib.initialize();

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            SpawnPlacements.register(ModEntityTypes.UNICORN.get(),
                    SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Animal::checkAnimalSpawnRules);

            SpawnPlacements.register(ModEntityTypes.CENTAUR.get(),
                    SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Animal::checkAnimalSpawnRules);

            SpawnPlacements.register(ModEntityTypes.MERMAID.get(),
                    SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    AbstractFish::checkSurfaceWaterAnimalSpawnRules);
        });
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            EntityRenderers.register(ModEntityTypes.UNICORN.get(), UnicornRenderer::new);
            EntityRenderers.register(ModEntityTypes.MERMAID.get(), MermaidRenderer::new);
            EntityRenderers.register(ModEntityTypes.CENTAUR.get(), CentaurRenderer::new);
        }
    }
}
