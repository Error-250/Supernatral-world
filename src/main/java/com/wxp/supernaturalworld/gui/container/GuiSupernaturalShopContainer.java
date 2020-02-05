package com.wxp.supernaturalworld.gui.container;

import com.wxp.supernaturalworld.gui.slot.SupernaturalShopSellSlot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.items.ItemStackHandler;

/** @author wxp */
public class GuiSupernaturalShopContainer extends AbstractGuiQuakeInventoryContainer {
  private EntityPlayer entityPlayer;
  private ItemStackHandler sellSlot = new ItemStackHandler(1);
  private SupernaturalShopSellSlot supernaturalShopSellSlot;

  public GuiSupernaturalShopContainer(EntityPlayer entityPlayer) {
    super(entityPlayer);

    this.entityPlayer = entityPlayer;
    supernaturalShopSellSlot = new SupernaturalShopSellSlot(sellSlot, 0, 25, 20);
    this.addSlotToContainer(supernaturalShopSellSlot);
  }

  @Override
  int getGuiSlotSize() {
    return 1;
  }

  @Override
  public boolean canInteractWith(EntityPlayer playerIn) {
    //    return playerIn.getDistanceSq(this.supernaturalShopBlock.getPos()) <= 64;
    return true;
  }
}
