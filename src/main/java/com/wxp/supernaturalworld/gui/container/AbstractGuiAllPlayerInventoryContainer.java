package com.wxp.supernaturalworld.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/** @author wxp */
public abstract class AbstractGuiAllPlayerInventoryContainer extends Container {
  public AbstractGuiAllPlayerInventoryContainer(EntityPlayer entityPlayer) {
    for (int i = 0; i < 9; i++) {
      this.addSlotToContainer(new Slot(entityPlayer.inventory, i, 8 + i * 18, 132));
    }

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 9; j++) {
        this.addSlotToContainer(
            new Slot(entityPlayer.inventory, j + i * 9 + 9, 8 + j * 18, 74 + i * 18));
      }
    }
  }
  /**
   * 返回Gui新添加的slot列表
   *
   * @return slot列表
   */
  abstract int getGuiSlotSize();
  /**
   * 是否允許打開container
   *
   * @param playerIn 玩家
   * @return 是否允許
   */
  @Override
  public abstract boolean canInteractWith(EntityPlayer playerIn);

  @Override
  public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
    Slot slot = inventorySlots.get(index);

    if (slot == null || !slot.getHasStack()) {
      return ItemStack.EMPTY;
    }

    ItemStack newStack = slot.getStack();
    ItemStack oldStack = newStack.copy();

    boolean isMerged = false;

    if (index < 9) {
      if (getGuiSlotSize() > 0) {
        isMerged = mergeItemStack(newStack, 36, 36 + getGuiSlotSize(), false);
      }
      isMerged = isMerged || mergeItemStack(newStack, 9, 36, false);
    } else if (index < 36) {
      if (getGuiSlotSize() > 0) {
        isMerged = mergeItemStack(newStack, 36, 36 + getGuiSlotSize(), false);
      }
      isMerged = isMerged || mergeItemStack(newStack, 0, 9, false);
    } else {
      isMerged = mergeItemStack(newStack, 0, 36, false);
    }

    if (!isMerged) {
      return ItemStack.EMPTY;
    }

    if (newStack.getCount() == 0) {
      slot.putStack(ItemStack.EMPTY);
    } else {
      slot.onSlotChanged();
    }

    slot.onTake(playerIn, newStack);

    return oldStack;
  }
}
