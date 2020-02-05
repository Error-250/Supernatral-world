package com.wxp.supernaturalworld.gui.slot;

import com.wxp.supernaturalworld.capability.BindingEntityI;
import com.wxp.supernaturalworld.capability.SupernaturalEntityI;
import com.wxp.supernaturalworld.item.SupernaturalRingItemImpl;
import com.wxp.supernaturalworld.manager.CapabilityManager;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

/** @author wxp */
public class SupernaturalRingSlot extends SlotItemHandler {
  private EntityPlayer entityPlayer;
  private SupernaturalEntityI supernaturalEntity;
  private int index;

  public SupernaturalRingSlot(
      EntityPlayer entityPlayer, SupernaturalEntityI supernaturalEntity, int index) {
    this(supernaturalEntity.getSupernaturalRingHandler(), index, 8 + index * 18, 47);
    this.entityPlayer = entityPlayer;
    this.supernaturalEntity = supernaturalEntity;
    this.index = index;
  }

  public SupernaturalRingSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
    super(itemHandler, index, xPosition, yPosition);
  }

  @Override
  public boolean isEnabled() {
    return supernaturalEntity.getPlayerActualSupernaturalPowerLevel() >= (index + 1) * 10;
  }

  @Override
  public boolean isItemValid(@Nonnull ItemStack stack) {
    boolean isSlotEmpty = !this.getHasStack();
    boolean isSupernaturalRing = stack.getItem() instanceof SupernaturalRingItemImpl;
    boolean isBindThisPlayer = false;
    if (isSupernaturalRing) {
      if (stack.hasCapability(CapabilityManager.bindingEntityICapability, null)) {
        BindingEntityI bindingEntityI =
            stack.getCapability(CapabilityManager.bindingEntityICapability, null);
        if (bindingEntityI != null) {
          if (bindingEntityI.getBindingPlayerUuid() == null) {
            isBindThisPlayer = true;
          } else {
            isBindThisPlayer =
                bindingEntityI.getBindingPlayerUuid().equals(entityPlayer.getUniqueID());
          }
        }
      }
    }
    return isSlotEmpty && isSupernaturalRing && isBindThisPlayer;
  }

  @Override
  public ItemStack onTake(EntityPlayer thePlayer, ItemStack stack) {
    return super.onTake(thePlayer, stack);
  }

  @Override
  public void putStack(@Nonnull ItemStack stack) {
    if (stack.getItem() instanceof SupernaturalRingItemImpl) {
      if (stack.hasCapability(CapabilityManager.bindingEntityICapability, null)) {
        BindingEntityI bindingEntity =
            stack.getCapability(CapabilityManager.bindingEntityICapability, null);
        if (bindingEntity != null && bindingEntity.getBindingPlayerUuid() == null) {
          bindingEntity.bindPlayer(entityPlayer.getUniqueID());
        }
      }
    }
    super.putStack(stack);
  }
}
