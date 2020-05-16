package com.wxp.supernaturalworld.potion;

/** @author wxp */
public class PotionNotHungry extends AbstractPotion {
  public PotionNotHungry() {
    super(0x00f000);
  }

  @Override
  String getPotionName() {
    return "not_hungry";
  }
}
