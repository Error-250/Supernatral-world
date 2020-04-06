package com.wxp.supernaturalworld.capability.impl;

import com.wxp.supernaturalworld.capability.SupernaturalEntityI;
import com.wxp.supernaturalworld.config.SupernaturalConfig;
import net.minecraftforge.items.ItemStackHandler;

/** @author wxp */
public class SupernaturalEntityImpl implements SupernaturalEntityI {
  private Long supernaturalPowerMaxLimit;
  private Long supernaturalPower;
  private ItemStackHandler supernaturalRingSlots;
  private Long money = 0L;

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
    SupernaturalConfig.SupernaturalPowerConfig supernaturalPowerConfig =
        SupernaturalConfig.supernaturalPowerConfig;
    final int powerMaxLimit10 = 10 * supernaturalPowerConfig.powerBetween0and10;
    final int powerMaxLimit20 = powerMaxLimit10 + 10 * supernaturalPowerConfig.powerBetween11and20;
    final int powerMaxLimit30 = powerMaxLimit20 + 10 * supernaturalPowerConfig.powerBetween21and30;
    final int powerMaxLimit40 = powerMaxLimit30 + 10 * supernaturalPowerConfig.powerBetween31and40;
    final int powerMaxLimit50 = powerMaxLimit40 + 10 * supernaturalPowerConfig.powerBetween41and50;
    final int powerMaxLimit60 = powerMaxLimit50 + 10 * supernaturalPowerConfig.powerBetween51and60;
    final int powerMaxLimit70 = powerMaxLimit60 + 10 * supernaturalPowerConfig.powerBetween61and70;
    final int powerMaxLimit80 = powerMaxLimit70 + 10 * supernaturalPowerConfig.powerBetween71and80;
    final int powerMaxLimit90 = powerMaxLimit80 + 10 * supernaturalPowerConfig.powerBetween81and90;
    final int powerMaxLimit95 = powerMaxLimit90 + 5 * supernaturalPowerConfig.powerBetween91and95;
    final int powerMaxLimit100 = powerMaxLimit95 + 5 * supernaturalPowerConfig.powerBetween96and100;

    int allowLevel = 0;
    if (supernaturalPowerMaxLimit <= powerMaxLimit10) {
      allowLevel = (int) (supernaturalPowerMaxLimit / supernaturalPowerConfig.powerBetween0and10);
    } else if (supernaturalPowerMaxLimit <= powerMaxLimit20) {
      allowLevel =
          10
              + (int) (supernaturalPowerMaxLimit - powerMaxLimit10)
                  / supernaturalPowerConfig.powerBetween11and20;
    } else if (supernaturalPowerMaxLimit <= powerMaxLimit30) {
      allowLevel =
          20
              + (int) (supernaturalPowerMaxLimit - powerMaxLimit20)
                  / supernaturalPowerConfig.powerBetween21and30;
    } else if (supernaturalPowerMaxLimit <= powerMaxLimit40) {
      allowLevel =
          30
              + (int) (supernaturalPowerMaxLimit - powerMaxLimit30)
                  / supernaturalPowerConfig.powerBetween31and40;
    } else if (supernaturalPowerMaxLimit <= powerMaxLimit50) {
      allowLevel =
          40
              + (int) (supernaturalPowerMaxLimit - powerMaxLimit40)
                  / supernaturalPowerConfig.powerBetween41and50;
    } else if (supernaturalPowerMaxLimit <= powerMaxLimit60) {
      allowLevel =
          50
              + (int) (supernaturalPowerMaxLimit - powerMaxLimit50)
                  / supernaturalPowerConfig.powerBetween51and60;
    } else if (supernaturalPowerMaxLimit <= powerMaxLimit70) {
      allowLevel =
          60
              + (int) (supernaturalPowerMaxLimit - powerMaxLimit60)
                  / supernaturalPowerConfig.powerBetween61and70;
    } else if (supernaturalPowerMaxLimit <= powerMaxLimit80) {
      allowLevel =
          70
              + (int) (supernaturalPowerMaxLimit - powerMaxLimit70)
                  / supernaturalPowerConfig.powerBetween71and80;
    } else if (supernaturalPowerMaxLimit <= powerMaxLimit90) {
      allowLevel =
          80
              + (int) (supernaturalPowerMaxLimit - powerMaxLimit80)
                  / supernaturalPowerConfig.powerBetween81and90;
    } else if (supernaturalPowerMaxLimit <= powerMaxLimit95) {
      allowLevel =
          90
              + (int) (supernaturalPowerMaxLimit - powerMaxLimit90)
                  / supernaturalPowerConfig.powerBetween91and95;
    } else if (supernaturalPowerMaxLimit <= powerMaxLimit100) {
      allowLevel =
          95
              + (int) (supernaturalPowerMaxLimit - powerMaxLimit95)
                  / supernaturalPowerConfig.powerBetween96and100;
    } else {
      allowLevel = 100;
    }
    return allowLevel;
  }

  @Override
  public Long getPlayerSupernaturalMoney() {
    return money;
  }

  @Override
  public void setPlayerSupernaturalMoney(Long money) {
    this.money = money;
  }

  @Override
  public ItemStackHandler getSupernaturalRingHandler() {
    return supernaturalRingSlots;
  }
}
