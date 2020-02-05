package com.wxp.supernaturalworld.capability.impl;

import com.wxp.supernaturalworld.capability.BindingEntityI;

import java.util.UUID;

/** @author wxp */
public class BindingEntityImpl implements BindingEntityI {
  private UUID bindingPlayer;

  @Override
  public UUID getBindingPlayerUuid() {
    return bindingPlayer;
  }

  @Override
  public void bindPlayer(UUID uuid) {
    this.bindingPlayer = uuid;
  }
}
