package com.wxp.supernaturalworld.creativetab;

import com.wxp.supernaturalworld.config.SupernaturalConfig;
import com.wxp.supernaturalworld.manager.ItemManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** @author wxp */
public class SupernaturalWorldCreativeTab extends CreativeTabs {
  public static SupernaturalWorldCreativeTab INSTANCE = new SupernaturalWorldCreativeTab();

  public SupernaturalWorldCreativeTab() {
    super(SupernaturalConfig.MOD_ID);
  }

  @SideOnly(Side.CLIENT)
  @Override
  public ItemStack getTabIconItem() {
    return new ItemStack(ItemManager.supernaturalRingItemImpl, 1, 1000);
  }
}
