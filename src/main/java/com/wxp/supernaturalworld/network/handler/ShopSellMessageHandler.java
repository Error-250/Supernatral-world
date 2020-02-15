package com.wxp.supernaturalworld.network.handler;

import com.wxp.supernaturalworld.gui.container.GuiSupernaturalShopContainer;
import com.wxp.supernaturalworld.network.ShopSellMessage;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

/** @author wxp */
public class ShopSellMessageHandler implements IMessageHandler<ShopSellMessage, IMessage> {
  @Override
  public IMessage onMessage(ShopSellMessage message, MessageContext ctx) {
    if (Side.SERVER == ctx.side) {
      ctx.getServerHandler()
          .player
          .getServerWorld()
          .addScheduledTask(
              () -> {
                EntityPlayerMP playerMP = ctx.getServerHandler().player;
                if (playerMP.openContainer instanceof GuiSupernaturalShopContainer) {
                  GuiSupernaturalShopContainer guiSupernaturalShopContainer =
                      (GuiSupernaturalShopContainer) playerMP.openContainer;
                  if (message.getOperateType() == 1) {
                    // sell
                    guiSupernaturalShopContainer.sellItem(message.getIndex());
                  } else {
                    // buy
                    guiSupernaturalShopContainer.buyItem(message.getIndex());
                  }
                }
              });
    }
    return null;
  }
}
