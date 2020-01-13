package com.wxp.supernaturalworld.item;

import net.minecraft.item.ItemStack;

import java.util.stream.Stream;

/** @author wxp */
public interface SupernaturalRingItemI {
  /**
   * 获取魂环等级
   *
   * @param stack ItemStack
   * @return 魂环等级
   */
  RingLevel getRingLevel(ItemStack stack);

  /**
   * 获取魂环年限
   *
   * @param stack ItemStack
   * @return 魂环年限
   */
  int getYears(ItemStack stack);

  public enum RingLevel {
    TEN,
    HUNDRED,
    THOUSAND,
    TEN_THOUSAND,
    HUNDRED_THOUSAND;

    public static RingLevel valueOf(int value) {
      return Stream.of(values())
          .filter(ringLevel -> ringLevel.ordinal() == value)
          .findAny()
          .orElse(null);
    }
  }
}
