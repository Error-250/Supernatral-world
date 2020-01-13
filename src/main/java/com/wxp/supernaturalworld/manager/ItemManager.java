package com.wxp.supernaturalworld.manager;

import com.wxp.supernaturalworld.creativetab.SupernaturalWorldCreativeTab;
import com.wxp.supernaturalworld.item.SupernaturalRingItemImpl;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/** @author wxp */
public class ItemManager {
  public static SupernaturalRingItemImpl supernaturalRingItemImpl = new SupernaturalRingItemImpl();
  private static List<Item> items;

  public static void initItem() {
    items = new ArrayList<>();

    supernaturalRingItemImpl.setCreativeTab(SupernaturalWorldCreativeTab.INSTANCE);
    items.add(supernaturalRingItemImpl);
  }

  public static Collection<Item> getInitializedItem() {
    return items;
  }
}
