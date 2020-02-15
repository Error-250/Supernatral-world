package com.wxp.supernaturalworld.capability;

import net.minecraftforge.items.ItemStackHandler;

/** @author wxp */
public interface SupernaturalEntityI {
  /**
   * 获取玩家魂力上限
   *
   * @return 魂力上限
   */
  Long getPlayerSupernaturalPowerMaxLimit();

  /**
   * 获取玩家当前魂力值
   *
   * @return 当前魂力值
   */
  Long getPlayerCurrentSupernaturalPower();

  /**
   * 设置玩家魂力上限
   *
   * @param maxLimit 设置新的上限值
   */
  void setPlayerSupernaturalPowerMaxLimit(Long maxLimit);

  /**
   * 玩家使用魂力
   *
   * @param power 使用的魂力数量
   * @return 是否使用成功
   */
  boolean userSupernaturalPower(Long power);

  /**
   * 提高玩家魂力值
   *
   * @param power 魂力值
   */
  void enhanceSupernaturalPower(Long power);

  /**
   * 设置玩家当前的魂力值
   *
   * @param power 魂力值
   */
  void setPlayerCurrentSupernaturalPower(Long power);

  /**
   * 获取玩家魂力等级
   *
   * @return 魂力等级
   */
  int getPlayerSupernaturalPowerLevel();

  /**
   * 获取玩家实际魂力等级
   *
   * @return 魂力等级
   */
  int getPlayerActualSupernaturalPowerLevel();

  /**
   * 获取玩家当前魂币
   *
   * @return 魂币值
   */
  Long getPlayerSupernaturalMoney();

  /**
   * 设置玩家魂币
   *
   * @param money 魂币值
   */
  void setPlayerSupernaturalMoney(Long money);

  /**
   * 臨時方法
   *
   * @return 返回ItemStackHandler
   */
  ItemStackHandler getSupernaturalRingHandler();
}
