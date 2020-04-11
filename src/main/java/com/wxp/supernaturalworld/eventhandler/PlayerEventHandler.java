package com.wxp.supernaturalworld.eventhandler;

import com.wxp.supernaturalworld.capability.SupernaturalEntityI;
import com.wxp.supernaturalworld.config.SupernaturalConfig;
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
    DamageSource source = event.getSource();
    if (source.getTrueSource() instanceof EntityPlayer) {
      EntityPlayer attacker = (EntityPlayer) source.getTrueSource();
      if (attacker.hasCapability(CapabilityManager.supernaturalEntityICapability, null)) {
        SupernaturalEntityI supernaturalEntityI =
            attacker.getCapability(CapabilityManager.supernaturalEntityICapability, null);
        if (supernaturalEntityI != null) {
          float baseAttachAttack =
              supernaturalEntityI.getPlayerSupernaturalPowerLevel()
                  * SupernaturalConfig.supernaturalPowerConfig.attackUpPerPowerLevel.floatValue();
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
          event.setAmount(event.getAmount() + baseAttachAttack);
        }
      }
    }
    if (event.getEntity() instanceof EntityPlayer) {
      EntityPlayer target = (EntityPlayer) event.getEntity();
      if (target.hasCapability(CapabilityManager.supernaturalEntityICapability, null)) {
        SupernaturalEntityI supernaturalEntityI =
            target.getCapability(CapabilityManager.supernaturalEntityICapability, null);
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
            if (SupernaturalRingItemI.RingSkill.SkillType.DEFENCE_DOUBLE.equals(
                    skill.getSkillType())
                || SupernaturalRingItemI.RingSkill.SkillType.DEFENCE_UP_AND_DOUBLE.equals(
                    skill.getSkillType())) {
              defenceDoubleRate += skill.getDefenceDoubleRate();
            }
          }
          if (defenceDoubleRate > 0) {
            baseAttachDefence = baseAttachDefence * defenceDoubleRate;
          }
          event.setAmount(event.getAmount() - baseAttachDefence * 0.8f);
        }
      }
    }
  }
}
