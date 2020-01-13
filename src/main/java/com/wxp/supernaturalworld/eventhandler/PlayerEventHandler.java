package com.wxp.supernaturalworld.eventhandler;

import com.wxp.supernaturalworld.capability.SupernaturalEntityI;
import com.wxp.supernaturalworld.config.SupernaturalConfig;
import com.wxp.supernaturalworld.manager.CapabilityManager;
import com.wxp.supernaturalworld.network.SupernaturalEntityMessage;
import com.wxp.supernaturalworld.register.NetworkRegister;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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

      NBTBase base =
          CapabilityManager.supernaturalCapabilityStorage.writeNBT(
              CapabilityManager.supernaturalEntityICapability, supernaturalEntity, null);
      NetworkRegister.simpleNetworkWrapper.sendTo(
          new SupernaturalEntityMessage((NBTTagCompound) base),
          (EntityPlayerMP) event.getEntityPlayer());
    }
  }
}
