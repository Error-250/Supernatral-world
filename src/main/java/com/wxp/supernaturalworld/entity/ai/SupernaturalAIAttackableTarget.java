package com.wxp.supernaturalworld.entity.ai;

import com.wxp.supernaturalworld.entity.SupernaturalMonster;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;

/** @author wxp */
public class SupernaturalAIAttackableTarget<T extends EntityLivingBase>
    extends EntityAINearestAttackableTarget<T> {
  public SupernaturalAIAttackableTarget(SupernaturalMonster monster, Class<T> classTarget) {
    super(monster, classTarget, true);
  }
}
