package net.diana.licentamod.item;

import net.diana.licentamod.MythicalCreaturesMod;
import net.diana.licentamod.block.ModBlocks;
import net.diana.licentamod.entity.ModEntityTypes;
import net.diana.licentamod.item.custom.EightBallItem;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MythicalCreaturesMod.MOD_ID);

    public static final RegistryObject<Item> MERMAID_CROWN = ITEMS.register("mermaid_crown",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.TUTORIAL_TAB)));

    public static final RegistryObject<Item> UNICORN_SPAWN_EGG = ITEMS.register("unicorn_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityTypes.UNICORN, 0xffc0cb, 0xff69b4,
                    new Item.Properties().tab(ModCreativeModeTab.TUTORIAL_TAB)));

    public static final RegistryObject<Item> MERMAID_SPAWN_EGG = ITEMS.register("mermaid_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityTypes.MERMAID, 0xff8c00, 0xffa07a,
                    new Item.Properties().tab(ModCreativeModeTab.TUTORIAL_TAB)));

    public static final RegistryObject<Item> CENTAUR_SPAWN_EGG = ITEMS.register("centaur_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityTypes.CENTAUR, 0x8b4513, 0x000000,
                    new Item.Properties().tab(ModCreativeModeTab.TUTORIAL_TAB)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
