package com.wxp.supernaturalworld.item;

import lombok.Data;
import lombok.Getter;
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
  //  RingLevel getRingLevel(ItemStack stack);

  /**
   * 获取魂环年限
   *
   * @param
   * @return 魂环年限
   */
  //  int getYears(ItemStack stack);

  @Data
  public static class RingSkill {
    private float attack;
    private float defence;
    private RingType ringType;
    private String skillDesc;
  }

  @Getter
  enum RingType {
    ATTACK("强攻"),
    SPEED("敏攻"),
    DEFENCE("防御");

    private String name;

    RingType(String name) {
      this.name = name;
    }

    public static RingType valueOf(int value) {
      return Stream.of(values())
          .filter(ringType -> ringType.ordinal() == value)
          .findAny()
          .orElse(null);
    }
  }

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
