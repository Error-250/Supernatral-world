package com.wxp.supernaturalworld.entity.ai;

import com.wxp.supernaturalworld.entity.SupernaturalMonster;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.world.EnumDifficulty;

/** @author wxp */
public class SupernaturalAIMonsterIdle extends EntityAILookIdle {
  private SupernaturalMonster monster;
  private SupernaturalAIMonsterAttack supernaturalAIMonsterAttack;
  private float needAddHealth = 0;

  public SupernaturalAIMonsterIdle(
      SupernaturalAIMonsterAttack supernaturalAIMonsterAttack,
      SupernaturalMonster supernaturalMonster) {
    super(supernaturalMonster);
    this.supernaturalAIMonsterAttack = supernaturalAIMonsterAttack;
    monster = supernaturalMonster;
  }

  @Override
  public boolean shouldExecute() {
    return !supernaturalAIMonsterAttack.shouldExecute() || super.shouldExecute();
  }

  @Override
  public boolean shouldContinueExecuting() {
    return canRecoverHealth() && monster.getHealth() < monster.getMaxHealth();
  }

  @Override
  public void startExecuting() {
    super.startExecuting();
    needAddHealth = (monster.getMaxHealth() - monster.getHealth()) / 200;
  }

  @Override
  public void updateTask() {
    super.updateTask();
    if (canRecoverHealth()) {
      monster.heal(needAddHealth);
    }
    monster.tryUpgrade();
  }

  private boolean canRecoverHealth() {
    if (this.monster.world.playerEntities.size() == 0) {
      return false;
    }
    return !this.monster.world.playerEntities.get(0).isCreative()
        && !EnumDifficulty.PEACEFUL.equals(this.monster.world.getWorldInfo().getDifficulty());
  }
}
