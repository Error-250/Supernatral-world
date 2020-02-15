package com.wxp.supernaturalworld.network.handler;

import com.wxp.supernaturalworld.gui.container.GuiSupernaturalShopContainer;
import com.wxp.supernaturalworld.network.GuiShopContainerMessage;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

/** @author wxp */
public class GuiShopContainerMessageHandler
    implements IMessageHandler<GuiShopContainerMessage, IMessage> {
  @Override
  public IMessage onMessage(GuiShopContainerMessage message, MessageContext ctx) {
    if (Side.SERVER.equals(ctx.side)) {
      ctx.getServerHandler()
          .player
          .getServerWorld()
          .addScheduledTask(
              () -> {
                EntityPlayerMP playerMP = ctx.getServerHandler().player;
                if (playerMP.openContainer instanceof GuiSupernaturalShopContainer) {
                  GuiSupernaturalShopContainer guiSupernaturalShopContainer =
                      (GuiSupernaturalShopContainer) playerMP.openContainer;
                  guiSupernaturalShopContainer.updateSellItem(message.getIndex());
                }
              });
    }
    return null;
  }
}
