package com.wxp.supernaturalworld.manager;

import com.wxp.supernaturalworld.creativetab.SupernaturalRingCreativeTab;
import com.wxp.supernaturalworld.domain.SupernaturalLevel;
import com.wxp.supernaturalworld.domain.SupernaturalRingInfo;
import com.wxp.supernaturalworld.item.SupernaturalRingItemI;
import com.wxp.supernaturalworld.item.SupernaturalRingItemImpl;
import lombok.AllArgsConstructor;
import lombok.Data;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/** @author wxp */
public class ShopMenuManager {
  private static List<ShopMenu> shopMenus;

  public static void initShopMenu() {
    shopMenus = new ArrayList<>();

    shopMenus.add(new ShopMenu(new ItemStack(Items.BRICK), 5L, null, null));
    shopMenus.add(new ShopMenu(new ItemStack(Items.IRON_INGOT), 10L, null, null));
    shopMenus.add(new ShopMenu(new ItemStack(Items.EMERALD), 15L, null, null));
    shopMenus.add(new ShopMenu(new ItemStack(Items.GOLD_INGOT), 30L, null, null));
    shopMenus.add(new ShopMenu(new ItemStack(Items.DIAMOND), 60L, null, null));
    NonNullList<ItemStack> supernaturalRings = NonNullList.create();
    ItemManager.supernaturalRingItemImpl.getSubItems(
        SupernaturalRingCreativeTab.INSTANCE, supernaturalRings);
    for (ItemStack itemStack : supernaturalRings) {
      long sellPrice = calculateSellPrice(itemStack);
      shopMenus.add(new ShopMenu(itemStack, sellPrice / 2, itemStack, sellPrice));
    }
  }

  public static Collection<ShopMenu> getInitializedShopMenu() {
    return shopMenus;
  }

  public static ShopMenu getSellItem(int index) {
    return shopMenus.get(index);
  }

  public static int getNextTypeIndexInSellItem(int index) {
    SupernaturalRingInfo supernaturalRingInfo =
        SupernaturalRingItemImpl.getRingInfo(shopMenus.get(index).getSellItem());
    for (int i = index; i < shopMenus.size(); i++) {
      SupernaturalRingInfo currentSupernaturalRingInfo =
          SupernaturalRingItemImpl.getRingInfo(shopMenus.get(i).getSellItem());
      if (!supernaturalRingInfo.getSkillType().equals(currentSupernaturalRingInfo.getSkillType())) {
        return i;
      }
    }
    return -1;
  }

  public static int getPreTypeIndexInSellItem(int index) {
    SupernaturalRingInfo supernaturalRingInfo =
        SupernaturalRingItemImpl.getRingInfo(shopMenus.get(index).getSellItem());
    for (int i = index; i > 4; i--) {
      SupernaturalRingInfo currentSupernaturalRingInfo =
          SupernaturalRingItemImpl.getRingInfo(shopMenus.get(i).getSellItem());
      if (!supernaturalRingInfo.getSkillType().equals(currentSupernaturalRingInfo.getSkillType())) {
        return i;
      }
    }
    return -1;
  }

  public static int getNextLevelIndexInSellItem(int index) {
    SupernaturalRingInfo supernaturalRingInfo =
        SupernaturalRingItemImpl.getRingInfo(shopMenus.get(index).getSellItem());
    for (int i = index; i < shopMenus.size(); i++) {
      SupernaturalRingInfo currentSupernaturalRingInfo =
          SupernaturalRingItemImpl.getRingInfo(shopMenus.get(i).getSellItem());
      if (!supernaturalRingInfo
          .getSupernaturalLevel()
          .equals(currentSupernaturalRingInfo.getSupernaturalLevel())) {
        return i;
      }
    }
    return -1;
  }

  public static int getPreLevelIndexInSellItem(int index) {
    SupernaturalRingInfo supernaturalRingInfo =
        SupernaturalRingItemImpl.getRingInfo(shopMenus.get(index).getSellItem());
    for (int i = index; i > 4; i--) {
      SupernaturalRingInfo currentSupernaturalRingInfo =
          SupernaturalRingItemImpl.getRingInfo(shopMenus.get(i).getSellItem());
      if (!supernaturalRingInfo
          .getSupernaturalLevel()
          .equals(currentSupernaturalRingInfo.getSupernaturalLevel())) {
        return i;
      }
    }
    return -1;
  }

  public static int getSellItemSize() {
    return shopMenus.size();
  }

  private static long calculateSellPrice(ItemStack itemStack) {
    SupernaturalRingInfo supernaturalRingInfo = SupernaturalRingItemImpl.getRingInfo(itemStack);
    int base = 0;
    switch (supernaturalRingInfo.getSupernaturalLevel()) {
      case TEN:
        base = 10;
        break;
      case HUNDRED:
        base = 50;
        break;
      case THOUSAND:
        base = 250;
        break;
      case TEN_THOUSAND:
        base = 1250;
        break;
      case HUNDRED_THOUSAND:
        base = 6250;
        break;
      default:
    }
    return supernaturalRingInfo.getBaseYear() * base;
  }

  @Data
  @AllArgsConstructor
  public static class ShopMenu {
    private ItemStack buyItem;
    private Long buyMoney;
    private ItemStack sellItem;
    private Long sellMoney;
  }
}
