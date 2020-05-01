package com.wxp.supernaturalworld.domain;

import lombok.Getter;

import java.util.stream.Stream;

/** @author wxp */
@Getter
public enum SupernaturalLevel {
  // 十年
  TEN(10),
  // 百年
  HUNDRED(100),
  // 千年
  THOUSAND(1000),
  // 万年
  TEN_THOUSAND(10000),
  // 十万年
  HUNDRED_THOUSAND(100000);

  private int minYear;

  SupernaturalLevel(int minYear) {
    this.minYear = minYear;
  }

  public static SupernaturalLevel valueOf(int value) {
    return Stream.of(values())
        .filter(supernaturalLevel -> supernaturalLevel.ordinal() == value)
        .findAny()
        .orElse(null);
  }
}
