package com.wxp.supernaturalworld.manager;

import com.wxp.supernaturalworld.block.SupernaturalNormalBlockI;
import com.wxp.supernaturalworld.block.SupernaturalShopBlock;
import com.wxp.supernaturalworld.creativetab.SupernaturalWorldCreativeTab;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BlockManager {
  public static SupernaturalShopBlock supernaturalShopBlock;

  private static List<SupernaturalNormalBlockI> supernaturalNormalBlockIS;

  public static void initBlock() {
    supernaturalNormalBlockIS = new ArrayList<>();

    supernaturalShopBlock = new SupernaturalShopBlock();
    supernaturalShopBlock.setCreativeTab(SupernaturalWorldCreativeTab.INSTANCE);
    supernaturalNormalBlockIS.add(supernaturalShopBlock);
  }

  public static Collection<SupernaturalNormalBlockI> getInitializedBlock() {
    return supernaturalNormalBlockIS;
  }
}
