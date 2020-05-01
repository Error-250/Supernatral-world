package com.wxp.supernaturalworld.manager;

import com.wxp.supernaturalworld.creativetab.SupernaturalRingCreativeTab;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

/** @author wxp */
public class OreDictionaryManager {
  public static void initOreDictionary() {
    NonNullList<ItemStack> subItems = NonNullList.create();
    ItemManager.supernaturalRingItemImpl.getSubItems(
        SupernaturalRingCreativeTab.INSTANCE, subItems);

    for (ItemStack itemStack : subItems) {
      OreDictionary.registerOre("supernatural_ring", itemStack);
    }
  }
}
