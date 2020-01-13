package com.wxp.supernaturalworld.network.handler;

import com.wxp.supernaturalworld.capability.SupernaturalEntityI;
import com.wxp.supernaturalworld.capability.impl.SupernaturalEntityImpl;
import com.wxp.supernaturalworld.manager.CapabilityManager;
import com.wxp.supernaturalworld.network.SupernaturalEntityMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

/** @author wxp */
public class SupernaturalEntityMessageHandler
    implements IMessageHandler<SupernaturalEntityMessage, IMessage> {
  @Override
  public IMessage onMessage(SupernaturalEntityMessage message, MessageContext ctx) {
    if (Side.CLIENT == ctx.side) {
      Minecraft.getMinecraft()
          .addScheduledTask(
              () -> {
                EntityPlayer player = Minecraft.getMinecraft().player;
                if (player.hasCapability(CapabilityManager.supernaturalEntityICapability, null)) {
                  SupernaturalEntityI supernaturalEntityI =
                      player.getCapability(CapabilityManager.supernaturalEntityICapability, null);
                  if (supernaturalEntityI == null) {
                    supernaturalEntityI = new SupernaturalEntityImpl();
                  }
                  CapabilityManager.supernaturalCapabilityStorage.readNBT(
                      CapabilityManager.supernaturalEntityICapability,
                      supernaturalEntityI,
                      null,
                      message.getNbt());
                }
              });
    }
    return null;
  }
}
