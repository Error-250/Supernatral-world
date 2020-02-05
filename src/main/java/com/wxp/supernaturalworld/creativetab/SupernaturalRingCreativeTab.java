package com.wxp.supernaturalworld.creativetab;

import com.wxp.supernaturalworld.config.SupernaturalConfig;
import com.wxp.supernaturalworld.manager.ItemManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** @author wxp */
public class SupernaturalRingCreativeTab extends CreativeTabs {
  public static SupernaturalRingCreativeTab INSTANCE = new SupernaturalRingCreativeTab();

  public SupernaturalRingCreativeTab() {
    super(SupernaturalConfig.MOD_ID + ".supernatural_ring");
  }

  @SideOnly(Side.CLIENT)
  @Override
  public ItemStack getTabIconItem() {
    return new ItemStack(ItemManager.supernaturalRingItemImpl, 1, 1100);
  }
}
