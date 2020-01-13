package com.wxp.supernaturalworld.gui.slot;

import com.wxp.supernaturalworld.capability.SupernaturalEntityI;
import com.wxp.supernaturalworld.item.SupernaturalRingItemImpl;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

/** @author wxp */
public class SupernaturalRingSlot extends SlotItemHandler {
  private SupernaturalEntityI supernaturalEntity;
  private int index;

  public SupernaturalRingSlot(SupernaturalEntityI supernaturalEntity, int index) {
    this(supernaturalEntity.getSupernaturalRingHandler(), index, 8 + index * 18, 47);
    this.supernaturalEntity = supernaturalEntity;
    this.index = index;
  }

  public SupernaturalRingSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
    super(itemHandler, index, xPosition, yPosition);
  }

  @Override
  public boolean isEnabled() {
    return supernaturalEntity.getPlayerSupernaturalPowerLevel() >= (index + 1) * 10;
  }

  @Override
  public boolean isItemValid(@Nonnull ItemStack stack) {
    return !this.getHasStack() && stack.getItem() instanceof SupernaturalRingItemImpl;
  }
}
