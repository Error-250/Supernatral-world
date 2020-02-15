package com.wxp.supernaturalworld.gui.container;

import com.wxp.supernaturalworld.capability.SupernaturalEntityI;
import com.wxp.supernaturalworld.gui.slot.SupernaturalRingSlot;
import com.wxp.supernaturalworld.gui.slot.SupernaturalShopSellSlot;
import com.wxp.supernaturalworld.manager.CapabilityManager;
import com.wxp.supernaturalworld.manager.ShopMenuManager;
import com.wxp.supernaturalworld.register.NetworkRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

/** @author wxp */
public class GuiSupernaturalShopContainer extends AbstractGuiAllPlayerInventoryContainer {
  private EntityPlayer entityPlayer;
  private ItemStackHandler sellSlot = new ItemStackHandler(2);
  private SupernaturalShopSellSlot supernaturalShopSellSlot;
  private SupernaturalRingSlot supernaturalShopBuySlot;
  private int index = 0;

  public GuiSupernaturalShopContainer(EntityPlayer entityPlayer) {
    super(entityPlayer);

    this.entityPlayer = entityPlayer;
    supernaturalShopSellSlot = new SupernaturalShopSellSlot(sellSlot, 0, 53, 31);
    supernaturalShopBuySlot = new SupernaturalRingSlot(sellSlot, 1, 53, 52);
    this.addSlotToContainer(supernaturalShopSellSlot);
    this.addSlotToContainer(supernaturalShopBuySlot);
    supernaturalShopBuySlot.putStack(ShopMenuManager.getSellItem(index).getSellItem());
  }

  @Override
  int getGuiSlotSize() {
    return 2;
  }

  @Override
  public boolean canInteractWith(EntityPlayer playerIn) {
    return true;
  }

  public EntityPlayer getEntityPlayer() {
    return entityPlayer;
  }

  public SupernaturalShopSellSlot getSupernaturalShopSellSlot() {
    return supernaturalShopSellSlot;
  }

  public void updateSellItem(int index) {
    this.index = index;
    supernaturalShopBuySlot.putStack(ShopMenuManager.getSellItem(this.index).getSellItem());
  }

  public void sellItem(int index) {
    SupernaturalEntityI supernaturalEntityI =
        entityPlayer.getCapability(CapabilityManager.supernaturalEntityICapability, null);
    if (supernaturalEntityI == null) {
      return;
    }
    Long oldMoney = supernaturalEntityI.getPlayerSupernaturalMoney();
    Long sellMoney = ShopMenuManager.getSellItem(index).getBuyMoney();
    oldMoney += sellMoney;
    supernaturalEntityI.setPlayerSupernaturalMoney(oldMoney);
    supernaturalShopSellSlot.getStack().shrink(1);
    NetworkRegister.syncSupernaturalEntityMessage(
        supernaturalEntityI, (EntityPlayerMP) entityPlayer);
  }

  public void buyItem(int index) {
    SupernaturalEntityI supernaturalEntityI =
        entityPlayer.getCapability(CapabilityManager.supernaturalEntityICapability, null);
    if (supernaturalEntityI == null) {
      return;
    }
    Long sellMoney = ShopMenuManager.getSellItem(index).getSellMoney();
    if (supernaturalEntityI.getPlayerSupernaturalMoney() < sellMoney) {
      return;
    }
    supernaturalEntityI.setPlayerSupernaturalMoney(
        supernaturalEntityI.getPlayerSupernaturalMoney() - sellMoney);
    ItemStack sellItem = ShopMenuManager.getSellItem(index).getSellItem().copy();
    entityPlayer.inventory.addItemStackToInventory(sellItem);
    NetworkRegister.syncSupernaturalEntityMessage(
        supernaturalEntityI, (EntityPlayerMP) entityPlayer);
  }
}
