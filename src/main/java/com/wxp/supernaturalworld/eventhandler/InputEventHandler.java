package com.wxp.supernaturalworld.eventhandler;

import com.wxp.supernaturalworld.capability.SupernaturalEntityI;
import com.wxp.supernaturalworld.config.SupernaturalConfig;
import com.wxp.supernaturalworld.gui.SupernaturalGuiHandler;
import com.wxp.supernaturalworld.manager.CapabilityManager;
import com.wxp.supernaturalworld.manager.KeyManager;
import com.wxp.supernaturalworld.network.OpenGuiMessage;
import com.wxp.supernaturalworld.register.NetworkRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;

/** @author wxp */
@Mod.EventBusSubscriber(modid = SupernaturalConfig.MOD_ID)
public class InputEventHandler {
  @SubscribeEvent
  public static void onKeyInput(InputEvent.KeyInputEvent event) {
    if (Side.SERVER.equals(FMLCommonHandler.instance().getSide())) {
      return;
    }
    if (KeyManager.KEY_SHOW_SUPERNATURAL.isPressed()) {
      EntityPlayer player = Minecraft.getMinecraft().player;
      if (player.hasCapability(CapabilityManager.supernaturalEntityICapability, null)) {
        SupernaturalEntityI supernaturalEntityI =
            player.getCapability(CapabilityManager.supernaturalEntityICapability, null);
        if (supernaturalEntityI == null) {
          return;
        }
        OpenGuiMessage openGuiMessage =
            new OpenGuiMessage(SupernaturalGuiHandler.ID_SUPERNATURAL_PLAYER_UI);
        NetworkRegister.simpleNetworkWrapper.sendToServer(openGuiMessage);
      }
    }
  }
}
