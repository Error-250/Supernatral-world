package com.wxp.supernaturalworld.eventhandler;

import com.wxp.supernaturalworld.SupernaturalMod;
import com.wxp.supernaturalworld.capability.SupernaturalEntityI;
import com.wxp.supernaturalworld.config.SupernaturalConfig;
import com.wxp.supernaturalworld.domain.SupernaturalHelper;
import com.wxp.supernaturalworld.entity.SupernaturalMonster;
import com.wxp.supernaturalworld.item.SupernaturalRingItemI;
import com.wxp.supernaturalworld.item.SupernaturalRingItemImpl;
import com.wxp.supernaturalworld.manager.CapabilityManager;
import com.wxp.supernaturalworld.register.NetworkRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Objects;

/** @author wxp */
@Mod.EventBusSubscriber(modid = SupernaturalConfig.MOD_ID)
public class PlayerEventHandler {
  @SubscribeEvent
  public static void onPlayerClone(PlayerEvent.Clone event) {
    if (event.getOriginal().hasCapability(CapabilityManager.supernaturalEntityICapability, null)
        && event
            .getEntityPlayer()
            .hasCapability(CapabilityManager.supernaturalEntityICapability, null)) {
      SupernaturalEntityI originData =
          event.getOriginal().getCapability(CapabilityManager.supernaturalEntityICapability, null);
      if (originData == null) {
        return;
      }
      NBTBase base =
          CapabilityManager.supernaturalCapabilityStorage.writeNBT(
              CapabilityManager.supernaturalEntityICapability, originData, null);
      CapabilityManager.supernaturalCapabilityStorage.readNBT(
          CapabilityManager.supernaturalEntityICapability,
          Objects.requireNonNull(
              event
                  .getEntityPlayer()
                  .getCapability(CapabilityManager.supernaturalEntityICapability, null)),
          null,
          base);
    }
  }

  @SubscribeEvent
  public static void onPlayerPickupXp(PlayerPickupXpEvent event) {
    if (event
        .getEntityPlayer()
        .hasCapability(CapabilityManager.supernaturalEntityICapability, null)) {
      SupernaturalEntityI supernaturalEntity =
          event
              .getEntityPlayer()
              .getCapability(CapabilityManager.supernaturalEntityICapability, null);
      if (supernaturalEntity == null) {
        return;
      }
      supernaturalEntity.setPlayerSupernaturalPowerMaxLimit(
          supernaturalEntity.getPlayerSupernaturalPowerMaxLimit() + event.getOrb().getXpValue());
      NetworkRegister.syncSupernaturalEntityMessage(
          supernaturalEntity, (EntityPlayerMP) event.getEntityPlayer());
    }
  }

  @SubscribeEvent
  public static void onPlayerAttackEntity(LivingHurtEvent event) {
    if (event.getEntity().world.isRemote) {
      return;
    }
    DamageSource source = event.getSource();
    float hurtAmount = event.getAmount();
    if (source.getTrueSource() instanceof EntityPlayer) {
      EntityPlayer attacker = (EntityPlayer) source.getTrueSource();
      float attachAttack = calculatePlayerAttachAttack(attacker);
      if (attachAttack > 0) {
        SupernaturalMod.logger.info("attach attack:{}", attachAttack);
        hurtAmount += attachAttack;
      }
    }
    if (source.getTrueSource() instanceof SupernaturalMonster) {
      float attachAttack = ((SupernaturalMonster) source.getTrueSource()).getMonsterAttack();
      if (attachAttack > 0) {
        SupernaturalMod.logger.info("attach attack:{}", attachAttack);
        hurtAmount += attachAttack;
      }
    }
    if (event.getEntity() instanceof EntityPlayer) {
      EntityPlayer target = (EntityPlayer) event.getEntity();
      float attachDefence = calculatePlayerDefence(target);
      if (attachDefence > 0) {
        SupernaturalMod.logger.info("attach defence:{}", attachDefence);
        hurtAmount -= attachDefence;
      }
    }
    if (event.getEntity() instanceof SupernaturalMonster) {
      float attachDefence = ((SupernaturalMonster) event.getEntity()).getMonsterDefence();
      if (attachDefence > 0) {
        SupernaturalMod.logger.info("attach defence:{}", attachDefence);
        hurtAmount -= attachDefence;
      }
    }
    if (hurtAmount != event.getAmount()) {
      event.setAmount(hurtAmount);
    }
  }

  private static float calculatePlayerAttachAttack(EntityPlayer entityPlayer) {
    if (entityPlayer.hasCapability(CapabilityManager.supernaturalEntityICapability, null)) {
      SupernaturalEntityI supernaturalEntityI =
          entityPlayer.getCapability(CapabilityManager.supernaturalEntityICapability, null);
      if (supernaturalEntityI != null) {
        float baseAttachAttack =
            SupernaturalHelper.calculateBaseAttack(
                supernaturalEntityI.getPlayerSupernaturalPowerLevel());
        float attackDoubleRate = 0f;
        ItemStackHandler handler = supernaturalEntityI.getSupernaturalRingHandler();
        for (int i = 0; i < handler.getSlots(); i++) {
          ItemStack stack = handler.getStackInSlot(i);
          if (stack.isEmpty()) {
            continue;
          }
          SupernaturalRingItemI.RingSkill skill = SupernaturalRingItemImpl.getRingSkill(stack);
          if (SupernaturalRingItemI.RingSkill.SkillType.ATTACK_UP.equals(skill.getSkillType())
              || SupernaturalRingItemI.RingSkill.SkillType.ATTACK_UP_AND_DOUBLE.equals(
                  skill.getSkillType())) {
            baseAttachAttack += skill.getAttack();
          }
          if (SupernaturalRingItemI.RingSkill.SkillType.ATTACK_DOUBLE.equals(skill.getSkillType())
              || SupernaturalRingItemI.RingSkill.SkillType.ATTACK_UP_AND_DOUBLE.equals(
                  skill.getSkillType())) {
            attackDoubleRate += skill.getAttackDoubleRate();
          }
        }
        if (attackDoubleRate > 0) {
          baseAttachAttack = baseAttachAttack * attackDoubleRate;
        }
        return baseAttachAttack;
      }
    }
    return 0;
  }

  private static float calculatePlayerDefence(EntityPlayer entityPlayer) {
    if (entityPlayer.hasCapability(CapabilityManager.supernaturalEntityICapability, null)) {
      SupernaturalEntityI supernaturalEntityI =
          entityPlayer.getCapability(CapabilityManager.supernaturalEntityICapability, null);
      if (supernaturalEntityI != null) {
        float baseAttachDefence =
            supernaturalEntityI.getPlayerSupernaturalPowerLevel()
                * SupernaturalConfig.supernaturalPowerConfig.defenceUpPerPowerLevel.floatValue();
        float defenceDoubleRate = 0;
        ItemStackHandler handler = supernaturalEntityI.getSupernaturalRingHandler();
        for (int i = 0; i < handler.getSlots(); i++) {
          ItemStack stack = handler.getStackInSlot(i);
          if (stack.isEmpty()) {
            continue;
          }
          SupernaturalRingItemI.RingSkill skill = SupernaturalRingItemImpl.getRingSkill(stack);
          if (SupernaturalRingItemI.RingSkill.SkillType.DEFENCE_UP.equals(skill.getSkillType())
              || SupernaturalRingItemI.RingSkill.SkillType.DEFENCE_UP_AND_DOUBLE.equals(
                  skill.getSkillType())) {
            baseAttachDefence += skill.getDefence();
          }
          if (SupernaturalRingItemI.RingSkill.SkillType.DEFENCE_DOUBLE.equals(skill.getSkillType())
              || SupernaturalRingItemI.RingSkill.SkillType.DEFENCE_UP_AND_DOUBLE.equals(
                  skill.getSkillType())) {
            defenceDoubleRate += skill.getDefenceDoubleRate();
          }
        }
        if (defenceDoubleRate > 0) {
          baseAttachDefence = baseAttachDefence * defenceDoubleRate;
        }
        return baseAttachDefence;
      }
    }
    return 0;
  }
}
