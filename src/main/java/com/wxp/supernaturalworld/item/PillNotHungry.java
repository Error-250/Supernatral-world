package com.wxp.supernaturalworld.item;

import com.wxp.supernaturalworld.manager.PotionManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

/** @author wxp */
public class PillNotHungry extends AbstractPill {
  private static String name = "pill_not_hungry";

  public PillNotHungry() {
    super(20, 0.5f);
  }

  @Override
  String getItemName() {
    return name;
  }

  @Override
  protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
    PillLevel pillLevel = getPillLevel(stack);
    if (PillLevel.MIDDLE.equals(pillLevel)) {
      player.addPotionEffect(PotionManager.potionNotHungry.getPotionEffect(20 * 60 * 3));
    }
    if (PillLevel.SENIOR.equals(pillLevel)) {
      player.addPotionEffect(PotionManager.potionNotHungry.getPotionEffect(20 * 60 * 8));
    }
    super.onFoodEaten(stack, worldIn, player);
  }

  @Override
  boolean allowEat(EntityPlayer entityPlayer, ItemStack itemStack) {
    PotionEffect effect = entityPlayer.getActivePotionEffect(PotionManager.potionNotHungry);
    return effect == null;
  }
}
