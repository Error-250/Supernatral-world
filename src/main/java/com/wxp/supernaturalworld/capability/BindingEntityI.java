package com.wxp.supernaturalworld.capability;

import java.util.UUID;

/** @author wxp */
public interface BindingEntityI {
  /**
   * 获取绑定玩家的uuid
   *
   * @return uuid
   */
  UUID getBindingPlayerUuid();

  /**
   * 绑定玩家
   *
   * @param uuid 玩家的uuid
   */
  void bindPlayer(UUID uuid);
}
