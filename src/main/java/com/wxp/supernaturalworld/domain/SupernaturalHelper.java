package com.wxp.supernaturalworld.domain;

import com.wxp.supernaturalworld.config.SupernaturalConfig;
import com.wxp.supernaturalworld.item.SupernaturalRingItemI;
import com.wxp.supernaturalworld.item.SupernaturalRingItemImpl;
import com.wxp.supernaturalworld.manager.ShopMenuManager;
import net.minecraft.item.ItemStack;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/** @author wxp */
public class SupernaturalHelper {
  public static SupernaturalLevel calculateLevel(long num) {
    if (num >= SupernaturalLevel.HUNDRED_THOUSAND.getMinYear()) {
      return SupernaturalLevel.HUNDRED_THOUSAND;
    }
    if (num >= SupernaturalLevel.TEN_THOUSAND.getMinYear()) {
      return SupernaturalLevel.TEN_THOUSAND;
    }
    if (num >= SupernaturalLevel.THOUSAND.getMinYear()) {
      return SupernaturalLevel.THOUSAND;
    }
    if (num >= SupernaturalLevel.HUNDRED.getMinYear()) {
      return SupernaturalLevel.HUNDRED;
    }
    return SupernaturalLevel.TEN;
  }

  public static float calculateBaseAttack(int years) {
    float baseAttack = 0;
    if (years < 20) {
      return years
          * SupernaturalConfig.supernaturalPowerConfig.attackUpBetween0And20PerPowerLevel
              .floatValue();
    }
    baseAttack +=
        20
            * SupernaturalConfig.supernaturalPowerConfig.attackUpBetween0And20PerPowerLevel
                .floatValue();
    if (years < 40) {
      return baseAttack
          + (years - 20)
              * SupernaturalConfig.supernaturalPowerConfig.attackUpBetween20And40PerPowerLevel
                  .floatValue();
    }
    baseAttack +=
        20
            * SupernaturalConfig.supernaturalPowerConfig.attackUpBetween20And40PerPowerLevel
                .floatValue();
    if (years < 70) {
      return baseAttack
          + (years - 40)
              * SupernaturalConfig.supernaturalPowerConfig.attackUpBetween40And70PerPowerLevel
                  .floatValue();
    }
    baseAttack +=
        30
            * SupernaturalConfig.supernaturalPowerConfig.attackUpBetween40And70PerPowerLevel
                .floatValue();
    if (years < 90) {
      return baseAttack
          + (years - 70)
              * SupernaturalConfig.supernaturalPowerConfig.attackUpBetween70And90PerPowerLevel
                  .floatValue();
    }
    baseAttack +=
        20
            * SupernaturalConfig.supernaturalPowerConfig.attackUpBetween70And90PerPowerLevel
                .floatValue();
    return baseAttack
        + (years - 90)
            * SupernaturalConfig.supernaturalPowerConfig.attackUpBetween90And100PerPowerLevel
                .floatValue();
  }

  public static ItemStack randomSelectSupernaturalRing(
      SupernaturalLevel expectLevel, Random random) {
    List<ItemStack> expectRings =
        ShopMenuManager.getInitializedShopMenu().stream()
            .filter(
                shopMenu -> {
                  if (shopMenu.getSellItem() != null
                      && shopMenu.getSellItem().getItem() instanceof SupernaturalRingItemI) {
                    SupernaturalRingInfo supernaturalRingInfo =
                        SupernaturalRingItemImpl.getRingInfo(shopMenu.getSellItem());
                    return expectLevel.equals(supernaturalRingInfo.getSupernaturalLevel());
                  }
                  return false;
                })
            .map(ShopMenuManager.ShopMenu::getSellItem)
            .collect(Collectors.toList());
    if (expectRings.size() > 0) {
      int ticket = random.nextInt(1000);
      return expectRings.get(ticket % expectRings.size());
    }
    return ItemStack.EMPTY;
  }
}
