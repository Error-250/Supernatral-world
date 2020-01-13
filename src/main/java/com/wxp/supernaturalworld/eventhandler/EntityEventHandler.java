package com.wxp.supernaturalworld.eventhandler;

import com.wxp.supernaturalworld.capability.SupernaturalEntityI;
import com.wxp.supernaturalworld.capability.provider.SupernaturalEntityProvider;
import com.wxp.supernaturalworld.config.SupernaturalConfig;
import com.wxp.supernaturalworld.manager.CapabilityManager;
import com.wxp.supernaturalworld.network.SupernaturalEntityMessage;
import com.wxp.supernaturalworld.register.NetworkRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/** @author wxp */
@Mod.EventBusSubscriber(modid = SupernaturalConfig.MOD_ID)
public class EntityEventHandler {
  @SubscribeEvent
  public static void onAttachCapabilitiesEntity(AttachCapabilitiesEvent<Entity> event) {
    Entity entity = event.getObject();
    if (entity instanceof EntityPlayer) {
      event.addCapability(
          new ResourceLocation(SupernaturalConfig.MOD_ID, "supernatural"),
          new SupernaturalEntityProvider());
    }
  }

  @SubscribeEvent
  public static void onEntityJoinWorld(EntityJoinWorldEvent event) {
    if (!event.getWorld().isRemote && event.getEntity() instanceof EntityPlayer) {
      EntityPlayerMP player = (EntityPlayerMP) event.getEntity();
      if (player.hasCapability(CapabilityManager.supernaturalEntityICapability, null)) {
        SupernaturalEntityI supernaturalEntityI =
            player.getCapability(CapabilityManager.supernaturalEntityICapability, null);
        if (supernaturalEntityI == null) {
          return;
        }
        NBTBase base =
            CapabilityManager.supernaturalCapabilityStorage.writeNBT(
                CapabilityManager.supernaturalEntityICapability, supernaturalEntityI, null);
        NetworkRegister.simpleNetworkWrapper.sendTo(
            new SupernaturalEntityMessage((NBTTagCompound) base), player);
      }
    }
  }
}
