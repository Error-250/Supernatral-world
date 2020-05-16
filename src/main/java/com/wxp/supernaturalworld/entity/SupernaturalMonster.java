package com.wxp.supernaturalworld.entity;

import com.wxp.supernaturalworld.config.SupernaturalConfig;
import com.wxp.supernaturalworld.domain.SupernaturalHelper;
import com.wxp.supernaturalworld.domain.SupernaturalLevel;
import com.wxp.supernaturalworld.entity.ai.SupernaturalAIAttackableTarget;
import com.wxp.supernaturalworld.entity.ai.SupernaturalAIMonsterAttack;
import com.wxp.supernaturalworld.entity.ai.SupernaturalAIMonsterIdle;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/** @author wxp */
public class SupernaturalMonster extends EntityMob {
  private static final DataParameter<Integer> monsterActiveYears =
      EntityDataManager.createKey(SupernaturalMonster.class, DataSerializers.VARINT);
  private int activeTicks = 0;
  private SupernaturalAIMonsterAttack supernaturalAIMonsterAttack;

  SupernaturalMonster(World worldIn) {
    super(worldIn);
    enablePersistence();
  }

  @Override
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    setMaxHealth(calculateHealthByLivingTick());
  }

  @Override
  protected void initEntityAI() {
    super.initEntityAI();
    supernaturalAIMonsterAttack = new SupernaturalAIMonsterAttack(this);
    this.tasks.addTask(1, new EntityAISwimming(this));
    this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
    this.tasks.addTask(4, supernaturalAIMonsterAttack);
    this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 0.8D));
    this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
    this.tasks.addTask(6, new SupernaturalAIMonsterIdle(supernaturalAIMonsterAttack, this));
    this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
    this.targetTasks.addTask(2, new SupernaturalAIAttackableTarget<>(this, EntityPlayer.class));
  }

  @Override
  public void onLivingUpdate() {
    super.onLivingUpdate();
    if (world.isRemote) {
      return;
    }
    this.activeTicks++;
    // 上限
    if (shouldDead()) {
      setDead();
    }
  }

  @Override
  protected void entityInit() {
    super.entityInit();
    this.dataManager.register(monsterActiveYears, 0);
  }

  @Override
  protected int getExperiencePoints(EntityPlayer player) {
    return 20 * (SupernaturalHelper.calculateLevel(getActiveYears()).ordinal() + 1);
  }

  @Override
  public void readEntityFromNBT(NBTTagCompound compound) {
    super.readEntityFromNBT(compound);

    NBTTagCompound monsterTag = (NBTTagCompound) compound.getTag("supernatural_monster");
    this.dataManager.set(monsterActiveYears, monsterTag.getInteger("active_years"));
    int maxHealth = calculateHealthByLivingTick();
    setMaxHealth(maxHealth);
    heal(maxHealth);
  }

  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound compound) {
    NBTTagCompound monsterTag = new NBTTagCompound();
    monsterTag.setInteger("active_years", getActiveYears());
    compound.setTag("supernatural_monster", monsterTag);
    return super.writeToNBT(compound);
  }

  public float getMonsterAttack() {
    if (isCreative()) {
      return 0;
    }
    return getActiveYears()
        * SupernaturalConfig.supernaturalPowerConfig.monsterDefenceUpPerYear.floatValue()
        / 2;
  }

  public float getMonsterDefence() {
    if (isCreative()) {
      return 0;
    }
    return getActiveYears()
        * SupernaturalConfig.supernaturalPowerConfig.monsterDefenceUpPerYear.floatValue();
  }

  public boolean tryUpgrade() {
    int currentActiveYears = this.dataManager.get(monsterActiveYears);
    SupernaturalLevel currentLevel = SupernaturalHelper.calculateLevel(currentActiveYears);
    boolean isHealthEnough = getHealth() == getMaxHealth();
    boolean yearUp = activeTicks / 10 >= currentLevel.getMinYear();
    if (isHealthEnough && yearUp) {
      this.dataManager.set(monsterActiveYears, currentActiveYears + currentLevel.getMinYear());
      this.activeTicks -= currentLevel.getMinYear() * 10;
      int maxHealth = calculateHealthByLivingTick();
      setMaxHealth(maxHealth);
      heal(maxHealth);
      return true;
    }
    return false;
  }

  public boolean shouldDead() {
    int currentYear = this.dataManager.get(monsterActiveYears);
    return currentYear + activeTicks / 10 >= SupernaturalLevel.HUNDRED_THOUSAND.getMinYear() * 10;
  }

  public int getActiveYears() {
    return this.dataManager.get(monsterActiveYears);
  }

  public boolean isRecentlyHurt() {
    return this.recentlyHit > 0;
  }

  @Nullable
  @Override
  public IEntityLivingData onInitialSpawn(
      DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
    int ticket = this.rand.nextInt(100000);
    if (ticket == 666) {
      this.dataManager.set(
          monsterActiveYears,
          this.rand.nextInt(9) * SupernaturalLevel.HUNDRED_THOUSAND.getMinYear());
      this.world.addWeatherEffect(
          new EntityLightningBolt(this.world, this.posX, this.posY, this.posZ, true));
    } else if (ticket >= 660 && ticket <= 670) {
      this.dataManager.set(
          monsterActiveYears, this.rand.nextInt(9) * SupernaturalLevel.TEN_THOUSAND.getMinYear());
    }
    return super.onInitialSpawn(difficulty, livingdata);
  }

  private void setMaxHealth(float maxHealth) {
    this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(maxHealth);
  }

  private int calculateHealthByLivingTick() {
    int currentYear = this.dataManager.get(monsterActiveYears);
    if (currentYear == 0) {
      return SupernaturalConfig.supernaturalEntityConfig.tenYearMonsterBaseHealth;
    }
    int baseHealth = 0;
    if (currentYear < SupernaturalLevel.HUNDRED.getMinYear()) {
      return (currentYear / SupernaturalLevel.TEN.getMinYear())
          * SupernaturalConfig.supernaturalEntityConfig.tenYearMonsterBaseHealth;
    }
    baseHealth += 9 * SupernaturalConfig.supernaturalEntityConfig.tenYearMonsterBaseHealth;
    if (currentYear < SupernaturalLevel.THOUSAND.getMinYear()) {
      return baseHealth
          + (currentYear / SupernaturalLevel.HUNDRED.getMinYear())
              * SupernaturalConfig.supernaturalEntityConfig.hundredYearMonsterBaseHealth;
    }
    baseHealth += 9 * SupernaturalConfig.supernaturalEntityConfig.hundredYearMonsterBaseHealth;
    if (currentYear < SupernaturalLevel.TEN_THOUSAND.getMinYear()) {
      return baseHealth
          + (currentYear / SupernaturalLevel.THOUSAND.getMinYear())
              * SupernaturalConfig.supernaturalEntityConfig.thousandYearMonsterBaseHealth;
    }
    baseHealth += 9 * SupernaturalConfig.supernaturalEntityConfig.thousandYearMonsterBaseHealth;
    if (currentYear < SupernaturalLevel.HUNDRED_THOUSAND.getMinYear()) {
      return baseHealth
          + (currentYear / SupernaturalLevel.TEN_THOUSAND.getMinYear())
              * SupernaturalConfig.supernaturalEntityConfig.tenThousandYearMonsterBaseHealth;
    }
    baseHealth += 9 * SupernaturalConfig.supernaturalEntityConfig.tenThousandYearMonsterBaseHealth;
    return baseHealth
        + (currentYear / SupernaturalLevel.HUNDRED_THOUSAND.getMinYear())
            * SupernaturalConfig.supernaturalEntityConfig.hundredThousandYearMonsterBaseHealth;
  }

  private boolean isCreative() {
    if (this.world.playerEntities.size() == 0) {
      return false;
    }
    return this.world.playerEntities.get(0).isCreative();
  }
}
