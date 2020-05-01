package com.wxp.supernaturalworld.entity.ai;

import com.wxp.supernaturalworld.entity.SupernaturalMonster;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackMelee;

/** @author wxp */
public class SupernaturalAIMonsterAttack extends EntityAIAttackMelee {
  public SupernaturalAIMonsterAttack(SupernaturalMonster supernaturalMonster) {
    super(
        supernaturalMonster,
        supernaturalMonster.getAIMoveSpeed() > 1 ? supernaturalMonster.getAIMoveSpeed() : 1.0,
        true);
  }

  @Override
  public boolean shouldContinueExecuting() {
    if (this.attacker.getRNG().nextInt(100) == 0) {
      this.attacker.setAttackTarget(null);
      return false;
    } else {
      return super.shouldContinueExecuting();
    }
  }

  @Override
  protected double getAttackReachSqr(EntityLivingBase attackTarget) {
    return 4.0F + attackTarget.width;
  }
}
