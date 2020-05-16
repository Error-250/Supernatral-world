package com.wxp.supernaturalworld.item;

import com.wxp.supernaturalworld.SupernaturalMod;
import com.wxp.supernaturalworld.capability.SupernaturalEntityI;
import com.wxp.supernaturalworld.config.SupernaturalConfig;
import com.wxp.supernaturalworld.manager.CapabilityManager;
import com.wxp.supernaturalworld.register.NetworkRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/** @author wxp */
public class PillSupernaturalPower extends AbstractPill {
  public PillSupernaturalPower() {
    super(0, 0);
    setAlwaysEdible();
  }

  @Override
  String getItemName() {
    return "pill_up_supernatural_power";
  }

  @Override
  protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
    SupernaturalEntityI supernaturalEntityI =
        player.getCapability(CapabilityManager.supernaturalEntityICapability, null);
    if (supernaturalEntityI != null) {
      if (!worldIn.isRemote) {
        supernaturalEntityI.setPlayerSupernaturalPowerMaxLimit(
            supernaturalEntityI.getPlayerSupernaturalPowerMaxLimit() + getUpPower(stack));
        NetworkRegister.syncSupernaturalEntityMessage(supernaturalEntityI, (EntityPlayerMP) player);
      }
    }

    super.onFoodEaten(stack, worldIn, player);
  }

  @Override
  boolean allowEat(EntityPlayer entityPlayer, ItemStack itemStack) {
    if (!entityPlayer.hasCapability(CapabilityManager.supernaturalEntityICapability, null)) {
      return false;
    }
    SupernaturalEntityI supernaturalEntityI =
        entityPlayer.getCapability(CapabilityManager.supernaturalEntityICapability, null);
    if (supernaturalEntityI == null) {
      return false;
    }
    if (!supernaturalEntityI
        .getPlayerCurrentSupernaturalPower()
        .equals(supernaturalEntityI.getPlayerSupernaturalPowerMaxLimit())) {
      return false;
    }
    PillLevel level = getPillLevel(itemStack);
    switch (level) {
      case PRIMARY:
        return supernaturalEntityI.getPlayerActualSupernaturalPowerLevel() < 40;
      case MIDDLE:
        return supernaturalEntityI.getPlayerActualSupernaturalPowerLevel() < 80;
      case SENIOR:
        return supernaturalEntityI.getPlayerActualSupernaturalPowerLevel() < 90;
      default:
        return false;
    }
  }

  private int getUpPower(ItemStack itemStack) {
    PillLevel level = getPillLevel(itemStack);
    switch (level) {
      case PRIMARY:
        return SupernaturalConfig.supernaturalPowerConfig.powerBetween11and20;
      case MIDDLE:
        return SupernaturalConfig.supernaturalPowerConfig.powerBetween41and50;
      case SENIOR:
        return SupernaturalConfig.supernaturalPowerConfig.powerBetween51and60;
      default:
        return 0;
    }
  }
}
