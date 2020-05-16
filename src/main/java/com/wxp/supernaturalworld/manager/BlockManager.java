package com.wxp.supernaturalworld.manager;

import com.wxp.supernaturalworld.block.AlchemyFurnaceBlock;
import com.wxp.supernaturalworld.block.SupernaturalNormalBlockI;
import com.wxp.supernaturalworld.block.SupernaturalShopBlock;
import com.wxp.supernaturalworld.creativetab.SupernaturalWorldCreativeTab;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/** @author wxp */
public class BlockManager {
  public static SupernaturalShopBlock supernaturalShopBlock;
  public static AlchemyFurnaceBlock alchemyFurnaceBlock = new AlchemyFurnaceBlock();

  private static List<SupernaturalNormalBlockI> supernaturalNormalBlockIS;

  public static void initBlock() {
    supernaturalNormalBlockIS = new ArrayList<>();

    supernaturalShopBlock = new SupernaturalShopBlock();
    supernaturalShopBlock.setCreativeTab(SupernaturalWorldCreativeTab.INSTANCE);
    supernaturalNormalBlockIS.add(supernaturalShopBlock);
    alchemyFurnaceBlock.setCreativeTab(SupernaturalWorldCreativeTab.INSTANCE);
    supernaturalNormalBlockIS.add(alchemyFurnaceBlock);
  }

  public static Collection<SupernaturalNormalBlockI> getInitializedBlock() {
    return supernaturalNormalBlockIS;
  }
}
