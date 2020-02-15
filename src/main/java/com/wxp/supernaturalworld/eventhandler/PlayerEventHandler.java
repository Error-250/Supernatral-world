package com.wxp.supernaturalworld.eventhandler;

import com.wxp.supernaturalworld.capability.SupernaturalEntityI;
import com.wxp.supernaturalworld.config.SupernaturalConfig;
import com.wxp.supernaturalworld.item.SupernaturalRingItemI;
import com.wxp.supernaturalworld.item.SupernaturalRingItemImpl;
import com.wxp.supernaturalworld.manager.CapabilityManager;
import com.wxp.supernaturalworld.network.SupernaturalEntityMessage;
import com.wxp.supernaturalworld.register.NetworkRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
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
          ItemStackHandler handler = supernaturalEntityI.getSupernaturalRingHandler();
          float attachAttack = 0;
          for (int i = 0; i < handler.getSlots(); i++) {
            ItemStack stack = handler.getStackInSlot(i);
            if (stack.isEmpty()) {
              continue;
            }
            SupernaturalRingItemI.RingSkill skill = SupernaturalRingItemImpl.getRingSkill(stack);
            attachAttack += skill.getAttack();
          }
          if (attachAttack > 0) {
            event.setAmount(event.getAmount() + attachAttack);
          }
        }
      }
    }
    if (event.getEntity() instanceof EntityPlayer) {
      EntityPlayer target = (EntityPlayer) event.getEntity();
      if (target.hasCapability(CapabilityManager.supernaturalEntityICapability, null)) {
        SupernaturalEntityI supernaturalEntityI =
            target.getCapability(CapabilityManager.supernaturalEntityICapability, null);
        if (supernaturalEntityI != null) {
          ItemStackHandler handler = supernaturalEntityI.getSupernaturalRingHandler();
          float attachDefence = 0;
          for (int i = 0; i < handler.getSlots(); i++) {
            ItemStack stack = handler.getStackInSlot(i);
            if (stack.isEmpty()) {
              continue;
            }
            SupernaturalRingItemI.RingSkill skill = SupernaturalRingItemImpl.getRingSkill(stack);
            attachDefence += skill.getDefence();
          }
          if (attachDefence > 0) {
            event.setAmount(event.getAmount() - attachDefence * 0.8f);
          }
        }
      }
    }
  }
}
