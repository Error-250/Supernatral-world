package com.wxp.supernaturalworld.capability.impl;

import com.wxp.supernaturalworld.capability.SupernaturalEntityI;
import net.minecraftforge.items.ItemStackHandler;

/** @author wxp */
public class SupernaturalEntityImpl implements SupernaturalEntityI {
  private Long supernaturalPowerMaxLimit;
  private Long supernaturalPower;
  private ItemStackHandler supernaturalRingSlots;

  public SupernaturalEntityImpl() {
    this.supernaturalPower = 0L;
    this.supernaturalPowerMaxLimit = 0L;
    supernaturalRingSlots = new ItemStackHandler(9);
  }

  @Override
  public Long getPlayerSupernaturalPowerMaxLimit() {
    return supernaturalPowerMaxLimit;
  }

  @Override
  public Long getPlayerCurrentSupernaturalPower() {
    return supernaturalPower;
  }

  @Override
  public void setPlayerSupernaturalPowerMaxLimit(Long maxLimit) {
    boolean isSupernaturalPowerFull = this.supernaturalPowerMaxLimit.equals(this.supernaturalPower);
    this.supernaturalPowerMaxLimit = maxLimit;
    if (isSupernaturalPowerFull) {
      this.supernaturalPower = maxLimit;
    }
  }

  @Override
  public boolean userSupernaturalPower(Long power) {
    if (supernaturalPower >= power) {
      supernaturalPower -= power;
      return true;
    }
    return false;
  }

  @Override
  public void enhanceSupernaturalPower(Long power) {
    supernaturalPower += power;
  }

  @Override
  public void setPlayerCurrentSupernaturalPower(Long power) {
    this.supernaturalPower = power;
  }

  @Override
  public int getPlayerSupernaturalPowerLevel() {
    int maxLevel = 0;
    for (int i = 0; i < 9; i++) {
      if (supernaturalRingSlots.getStackInSlot(i).isEmpty()) {
        break;
      }
      maxLevel++;
    }
    int allowLevel = getPlayerActualSupernaturalPowerLevel();
    if (allowLevel > (maxLevel + 1) * 10) {
      return (maxLevel + 1) * 10;
    }
    return allowLevel;
  }

  @Override
  public int getPlayerActualSupernaturalPowerLevel() {
    final int powerMaxMinutes0_10 = 10;
    final int powerMaxMinutes11_20 = 100;
    final int powerMaxMinutes21_30 = 200;
    final int powerMaxMinutes31_40 = 500;
    final int powerMaxMinutes41_50 = 800;
    final int powerMaxMinutes51_60 = 1000;
    final int powerMaxMinutes61_70 = 1500;
    final int powerMaxMinutes71_80 = 2000;
    final int powerMaxMinutes81_90 = 5000;
    final int powerMaxMinutes91_95 = 8000;
    final int powerMaxMinutes96_100 = 10000;
    final int powerMaxLimit10 = 10 * powerMaxMinutes0_10;
    final int powerMaxLimit20 = powerMaxLimit10 + 10 * powerMaxMinutes11_20;
    final int powerMaxLimit30 = powerMaxLimit20 + 10 * powerMaxMinutes21_30;
    final int powerMaxLimit40 = powerMaxLimit30 + 10 * powerMaxMinutes31_40;
    final int powerMaxLimit50 = powerMaxLimit40 + 10 * powerMaxMinutes41_50;
    final int powerMaxLimit60 = powerMaxLimit50 + 10 * powerMaxMinutes51_60;
    final int powerMaxLimit70 = powerMaxLimit60 + 10 * powerMaxMinutes61_70;
    final int powerMaxLimit80 = powerMaxLimit70 + 10 * powerMaxMinutes71_80;
    final int powerMaxLimit90 = powerMaxLimit80 + 10 * powerMaxMinutes81_90;
    final int powerMaxLimit95 = powerMaxLimit90 + 5 * powerMaxMinutes91_95;
    final int powerMaxLimit100 = powerMaxLimit95 + 5 * powerMaxMinutes96_100;

    int allowLevel = 0;
    if (supernaturalPowerMaxLimit <= powerMaxLimit10) {
      allowLevel = (int) (supernaturalPowerMaxLimit / powerMaxMinutes0_10);
    } else if (supernaturalPowerMaxLimit <= powerMaxLimit20) {
      allowLevel = 10 + (int) (supernaturalPowerMaxLimit - powerMaxLimit10) / powerMaxMinutes11_20;
    } else if (supernaturalPowerMaxLimit <= powerMaxLimit30) {
      allowLevel = 20 + (int) (supernaturalPowerMaxLimit - powerMaxLimit20) / powerMaxMinutes21_30;
    } else if (supernaturalPowerMaxLimit <= powerMaxLimit40) {
      allowLevel = 30 + (int) (supernaturalPowerMaxLimit - powerMaxLimit30) / powerMaxMinutes31_40;
    } else if (supernaturalPowerMaxLimit <= powerMaxLimit50) {
      allowLevel = 40 + (int) (supernaturalPowerMaxLimit - powerMaxLimit40) / powerMaxMinutes41_50;
    } else if (supernaturalPowerMaxLimit <= powerMaxLimit60) {
      allowLevel = 50 + (int) (supernaturalPowerMaxLimit - powerMaxLimit50) / powerMaxMinutes51_60;
    } else if (supernaturalPowerMaxLimit <= powerMaxLimit70) {
      allowLevel = 60 + (int) (supernaturalPowerMaxLimit - powerMaxLimit60) / powerMaxMinutes61_70;
    } else if (supernaturalPowerMaxLimit <= powerMaxLimit80) {
      allowLevel = 70 + (int) (supernaturalPowerMaxLimit - powerMaxLimit70) / powerMaxMinutes71_80;
    } else if (supernaturalPowerMaxLimit <= powerMaxLimit90) {
      allowLevel = 80 + (int) (supernaturalPowerMaxLimit - powerMaxLimit80) / powerMaxMinutes81_90;
    } else if (supernaturalPowerMaxLimit <= powerMaxLimit95) {
      allowLevel = 90 + (int) (supernaturalPowerMaxLimit - powerMaxLimit90) / powerMaxMinutes91_95;
    } else if (supernaturalPowerMaxLimit <= powerMaxLimit100) {
      allowLevel = 95 + (int) (supernaturalPowerMaxLimit - powerMaxLimit95) / powerMaxMinutes96_100;
    } else {
      allowLevel = 100;
    }
    return allowLevel;
  }

  @Override
  public ItemStackHandler getSupernaturalRingHandler() {
    return supernaturalRingSlots;
  }
}
