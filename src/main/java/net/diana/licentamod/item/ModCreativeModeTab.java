package net.diana.licentamod.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab {
    public static final CreativeModeTab MYTHICAL_CREATURES_TAB = new CreativeModeTab("mythicalcreaturestab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.MERMAID_CROWN.get());
        }
    };
}
