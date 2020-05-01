package com.wxp.supernaturalworld.domain;

import com.wxp.supernaturalworld.item.SupernaturalRingItemI;
import lombok.Data;

/** @author wxp */
@Data
public class SupernaturalRingInfo {
  private SupernaturalLevel supernaturalLevel;
  private SupernaturalRingItemI.RingSkill.SkillType skillType;
  private int year;
  private int baseYear;
}
