package com.wxp.supernaturalworld.gui.slot;

import com.wxp.supernaturalworld.capability.BindingEntityI;
import com.wxp.supernaturalworld.item.SupernaturalRingItemImpl;
import com.wxp.supernaturalworld.manager.CapabilityManager;
import com.wxp.supernaturalworld.manager.ShopMenuManager;
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
    if (stack.hasCapability(CapabilityManager.bindingEntityICapability, null)) {
      BindingEntityI bindingEntityI =
          stack.getCapability(CapabilityManager.bindingEntityICapability, null);
      if (bindingEntityI.getBindingPlayerUuid() != null) {
        return Boolean.FALSE;
      }
    }
    return ShopMenuManager.getInitializedShopMenu().stream()
        .anyMatch(
            shopMenu ->
                shopMenu.getBuyItem().getItem() == stack.getItem()
                    && shopMenu.getBuyItem().getMetadata() == stack.getMetadata());
  }
}
