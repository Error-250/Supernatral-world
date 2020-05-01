package com.wxp.supernaturalworld.entity.ai;

import com.wxp.supernaturalworld.SupernaturalMod;
import com.wxp.supernaturalworld.entity.SupernaturalMonster;
import net.minecraft.entity.ai.EntityAIBase;

/** @author wxp */
public abstract class AbstractSupernaturalAI extends EntityAIBase {
  SupernaturalMonster aiOwner;

  public AbstractSupernaturalAI(SupernaturalMonster supernaturalMonster) {
    this.aiOwner = supernaturalMonster;
  }

  /**
   * ai执行的条件
   *
   * @return 是否可执行
   */
  @Override
  public abstract boolean shouldExecute();

  @Override
  public void startExecuting() {
    super.startExecuting();
    SupernaturalMod.logger.info("Start Ai:{}", this.getClass());
  }
}
