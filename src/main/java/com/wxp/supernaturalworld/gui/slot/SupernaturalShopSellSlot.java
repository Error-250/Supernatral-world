package com.wxp.supernaturalworld.gui.slot;

import com.wxp.supernaturalworld.manager.ItemManager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

/** @author wxp */
public class SupernaturalShopSellSlot extends SlotItemHandler {
  public SupernaturalShopSellSlot(
      IItemHandler itemHandler, int index, int xPosition, int yPosition) {
    super(itemHandler, index, xPosition, yPosition);
  }

  @Override
  public boolean isItemValid(@Nonnull ItemStack stack) {
    boolean isSupernaturalRing = stack.getItem() == ItemManager.supernaturalRingItemImpl;
    boolean isMinecraftMoney =
        stack.getItem() == Items.DIAMOND
            || stack.getItem() == Items.EMERALD
            || stack.getItem() == Items.BRICK
            || stack.getItem() == Items.IRON_INGOT
            || stack.getItem() == Items.GOLD_INGOT;
    return super.isItemValid(stack);
  }
}
