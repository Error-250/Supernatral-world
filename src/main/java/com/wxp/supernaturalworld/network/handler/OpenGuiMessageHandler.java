package com.wxp.supernaturalworld.network.handler;

import com.wxp.supernaturalworld.SupernaturalMod;
import com.wxp.supernaturalworld.network.OpenGuiMessage;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

/** @author wxp */
public class OpenGuiMessageHandler implements IMessageHandler<OpenGuiMessage, IMessage> {
  @Override
  public IMessage onMessage(OpenGuiMessage message, MessageContext ctx) {
    if (Side.SERVER.equals(ctx.side)) {
      ctx.getServerHandler()
          .player
          .getServerWorld()
          .addScheduledTask(
              () -> {
                EntityPlayerMP playerMP = ctx.getServerHandler().player;
                playerMP.openGui(
                    SupernaturalMod.INSTANCE,
                    message.getGuiId(),
                    playerMP.world,
                    playerMP.getPosition().getX(),
                    playerMP.getPosition().getY(),
                    playerMP.getPosition().getZ());
                ;
              });
    }
    return null;
  }
}
