package net.diana.licentamod.item;

import net.diana.licentamod.MythicalCreaturesMod;
import net.diana.licentamod.entity.ModEntityTypes;
import net.minecraft.world.item.Item;
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

    public static final RegistryObject<Item> MERMAID_CAKE = ITEMS.register("mermaid_cake",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.TUTORIAL_TAB)));
    public static final RegistryObject<Item> SLICE_CAKE = ITEMS.register("slice_cake",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.TUTORIAL_TAB).stacksTo(64)));

    public static final RegistryObject<Item> UNICORN_FLY_POTION = ITEMS.register("unicorn_fly_potion",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.TUTORIAL_TAB)));

    public static final RegistryObject<Item> CENTAUR_CHARM = ITEMS.register("centaur_charm",
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
