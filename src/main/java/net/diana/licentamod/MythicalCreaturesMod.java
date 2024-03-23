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
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import software.bernie.geckolib3.GeckoLib;

// The value here should match an entry in the META-INF/mods.toml file
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

    private void commonSetup(final FMLCommonSetupEvent event) {}

    // can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
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
