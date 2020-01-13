package com.wxp.supernaturalworld.register;

import com.wxp.supernaturalworld.SupernaturalMod;
import com.wxp.supernaturalworld.config.SupernaturalConfig;
import com.wxp.supernaturalworld.gui.SupernaturalGuiHandler;
import com.wxp.supernaturalworld.network.OpenGuiMessage;
import com.wxp.supernaturalworld.network.SupernaturalEntityMessage;
import com.wxp.supernaturalworld.network.handler.OpenGuiMessageHandler;
import com.wxp.supernaturalworld.network.handler.SupernaturalEntityMessageHandler;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

/** @author wxp */
public class NetworkRegister {
  public static SimpleNetworkWrapper simpleNetworkWrapper =
      NetworkRegistry.INSTANCE.newSimpleChannel(SupernaturalConfig.MOD_ID);
  private static IGuiHandler guiHandler = new SupernaturalGuiHandler();

  private static final int SUPERNATURAL_ENTITY_MESSAGE_ID = 0;
  private static final int OPEN_GUI_MESSAGE_ID = 1;

  public static void registerNetwork() {
    NetworkRegistry.INSTANCE.registerGuiHandler(SupernaturalMod.INSTANCE, guiHandler);

    simpleNetworkWrapper.registerMessage(
        SupernaturalEntityMessageHandler.class,
        SupernaturalEntityMessage.class,
        SUPERNATURAL_ENTITY_MESSAGE_ID,
        Side.CLIENT);
    simpleNetworkWrapper.registerMessage(
        OpenGuiMessageHandler.class, OpenGuiMessage.class, OPEN_GUI_MESSAGE_ID, Side.SERVER);
  }
}
