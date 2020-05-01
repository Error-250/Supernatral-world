package com.wxp.supernaturalworld.item;

import lombok.Data;
import lombok.Getter;

import java.util.Arrays;
import java.util.stream.Stream;

/** @author wxp */
public interface SupernaturalRingItemI {
  @Data
  class RingSkill {
    private Float attack;
    private Float attackDoubleRate;
    private Float defence;
    private Float defenceDoubleRate;
    private RingType ringType;
    private SkillType skillType;
    private String skillDesc;

    public enum SkillType {
      ATTACK_UP,
      DEFENCE_UP,
      ATTACK_DOUBLE,
      DEFENCE_DOUBLE,
      ATTACK_UP_AND_DOUBLE,
      DEFENCE_UP_AND_DOUBLE;

      public static SkillType valueOfNumber(int num) {
        return Arrays.stream(values())
            .filter(allowSkillType -> allowSkillType.ordinal() == num)
            .findFirst()
            .orElse(null);
      }
    }
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
}
