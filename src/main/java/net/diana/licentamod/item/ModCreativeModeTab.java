package net.diana.licentamod.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab {
    public static final CreativeModeTab TUTORIAL_TAB = new CreativeModeTab("tutorialtab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.MERMAID_CROWN.get());
        }
    };

   /* public static final CreativeModeTab TUTORIAL_TAB_2 = new CreativeModeTab("tutorialtab_2") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.RAW_ZIRCON.get());
        }
    };*/
}
