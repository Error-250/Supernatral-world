package com.wxp.supernaturalworld.entity;

import com.wxp.supernaturalworld.config.SupernaturalConfig;
import com.wxp.supernaturalworld.manager.EntityManager;
import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.init.Biomes;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateClimber;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;

import javax.annotation.Nullable;

/** @author wxp */
public class SupernaturalSpiderEntity extends SupernaturalMonster {
  private static final DataParameter<Byte> CLIMBING =
      EntityDataManager.<Byte>createKey(EntitySpider.class, DataSerializers.BYTE);
  private static String entityName = "supernatural_spider";

  public SupernaturalSpiderEntity(World worldIn) {
    super(worldIn);
    this.setSize(1.4F, 0.9F);
  }

  @Override
  protected void initEntityAI() {
    super.initEntityAI();
  }

  @Override
  public double getMountedYOffset() {
    return this.height * 0.5F;
  }

  @Override
  protected PathNavigate createNavigator(World worldIn) {
    return new PathNavigateClimber(this, worldIn);
  }

  @Override
  protected void entityInit() {
    super.entityInit();
    this.dataManager.register(CLIMBING, (byte) 0);
  }

  @Override
  public void onUpdate() {
    super.onUpdate();

    if (!this.world.isRemote) {
      this.setBesideClimbableBlock(this.collidedHorizontally);
    }
  }

  @Override
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED)
        .setBaseValue(0.30000001192092896D);
  }

  @Override
  protected SoundEvent getAmbientSound() {
    return SoundEvents.ENTITY_SPIDER_AMBIENT;
  }

  @Override
  protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
    return SoundEvents.ENTITY_SPIDER_HURT;
  }

  @Override
  protected SoundEvent getDeathSound() {
    return SoundEvents.ENTITY_SPIDER_DEATH;
  }

  @Override
  protected void playStepSound(BlockPos pos, Block blockIn) {
    this.playSound(SoundEvents.ENTITY_SPIDER_STEP, 0.15F, 1.0F);
  }

  @Override
  @Nullable
  protected ResourceLocation getLootTable() {
    return LootTableList.ENTITIES_SPIDER;
  }

  /**
   * Returns true if this entity should move as if it were on a ladder (either because it's actually
   * on a ladder, or for AI reasons)
   */
  @Override
  public boolean isOnLadder() {
    return this.isBesideClimbableBlock();
  }

  /** Sets the Entity inside a web block. */
  @Override
  public void setInWeb() {}

  /** Get this Entity's EnumCreatureAttribute */
  @Override
  public EnumCreatureAttribute getCreatureAttribute() {
    return EnumCreatureAttribute.ARTHROPOD;
  }

  @Override
  public boolean isPotionApplicable(PotionEffect potioneffectIn) {
    return potioneffectIn.getPotion() != MobEffects.POISON
        && super.isPotionApplicable(potioneffectIn);
  }

  /**
   * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning
   * etc, but not called when entity is reloaded from nbt. Mainly used for initializing attributes
   * and inventory
   */
  @Override
  @Nullable
  public IEntityLivingData onInitialSpawn(
      DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
    livingdata = super.onInitialSpawn(difficulty, livingdata);

    if (livingdata == null) {
      livingdata = new EntitySpider.GroupData();

      if (this.world.getDifficulty() == EnumDifficulty.HARD
          && this.world.rand.nextFloat() < 0.1F * difficulty.getClampedAdditionalDifficulty()) {
        ((EntitySpider.GroupData) livingdata).setRandomEffect(this.world.rand);
      }
    }

    if (livingdata instanceof EntitySpider.GroupData) {
      Potion potion = ((EntitySpider.GroupData) livingdata).effect;

      if (potion != null) {
        this.addPotionEffect(new PotionEffect(potion, Integer.MAX_VALUE));
      }
    }

    return livingdata;
  }

  @Override
  public float getEyeHeight() {
    return 0.65F;
  }

  /**
   * Returns true if the WatchableObject (Byte) is 0x01 otherwise returns false. The WatchableObject
   * is updated using setBesideClimableBlock.
   */
  private boolean isBesideClimbableBlock() {
    return (this.dataManager.get(CLIMBING) & 1) != 0;
  }

  /**
   * Updates the WatchableObject (Byte) created in entityInit(), setting it to 0x01 if par1 is true
   * or 0x00 if it is false.
   */
  private void setBesideClimbableBlock(boolean climbing) {
    byte b0 = this.dataManager.get(CLIMBING);

    if (climbing) {
      b0 = (byte) (b0 | 1);
    } else {
      b0 = (byte) (b0 & -2);
    }

    this.dataManager.set(CLIMBING, b0);
  }

  public static EntityEntry getEntity() {
    return EntityEntryBuilder.create()
        .id(
            new ResourceLocation(SupernaturalConfig.MOD_ID, entityName),
            EntityManager.getIdByClass(SupernaturalSpiderEntity.class))
        .name(entityName)
        .tracker(80, 3, true)
        .entity(SupernaturalSpiderEntity.class)
        .egg(0xffff66, 0x660000)
        .spawn(EnumCreatureType.MONSTER, 100, 4, 4, Biomes.FOREST)
        .build();
  }
}
