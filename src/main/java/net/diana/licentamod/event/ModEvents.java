package net.diana.licentamod.event;

import net.diana.licentamod.MythicalCreaturesMod;
import net.diana.licentamod.entity.ModEntityTypes;
import net.diana.licentamod.entity.custom.CentaurEntity;
import net.diana.licentamod.entity.custom.MermaidEntity;
import net.diana.licentamod.entity.custom.UnicornEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ModEvents {

    @Mod.EventBusSubscriber(modid = MythicalCreaturesMod.MOD_ID)
    public static class ForgeEvents {

    }

    @Mod.EventBusSubscriber(modid = MythicalCreaturesMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEventBusEvents {
        @SubscribeEvent
        public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
            event.put(ModEntityTypes.UNICORN.get(), UnicornEntity.setAttributes());
            event.put(ModEntityTypes.MERMAID.get(), MermaidEntity.setAttributes());
            event.put(ModEntityTypes.CENTAUR.get(), CentaurEntity.setAttributes());
        }
    }
}
