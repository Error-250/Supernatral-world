package com.wxp.supernaturalworld.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;

/** @author wxp */
public abstract class AbstractGuiQuakeInventoryContainer extends AbstractGuiContainer {
  public AbstractGuiQuakeInventoryContainer(EntityPlayer entityPlayer) {
    for (int i = 0; i < 9; i++) {
      this.addSlotToContainer(new Slot(entityPlayer.inventory, i, 8 + i * 18, 132));
    }
  }

  @Override
  boolean isContainsQuakeInventory() {
    return true;
  }

  @Override
  boolean isContainsPlayerInventory() {
    return false;
  }
}
