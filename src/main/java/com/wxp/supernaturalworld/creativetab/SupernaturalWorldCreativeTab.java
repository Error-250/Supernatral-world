package com.wxp.supernaturalworld.creativetab;

import com.wxp.supernaturalworld.config.SupernaturalConfig;
import com.wxp.supernaturalworld.manager.BlockManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

/**
 * @author wxp
 */
public class SupernaturalWorldCreativeTab extends CreativeTabs {
  public static SupernaturalWorldCreativeTab INSTANCE = new SupernaturalWorldCreativeTab();

  public SupernaturalWorldCreativeTab() {
    super(SupernaturalConfig.MOD_ID);
  }

  @Override
  public ItemStack getTabIconItem() {
    return new ItemStack(BlockManager.supernaturalShopBlock);
  }
}
