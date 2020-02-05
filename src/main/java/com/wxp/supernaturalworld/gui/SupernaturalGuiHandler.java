package com.wxp.supernaturalworld.gui;

import com.wxp.supernaturalworld.gui.container.GuiSupernaturalPlayerContainer;
import com.wxp.supernaturalworld.gui.container.GuiSupernaturalShopContainer;
import com.wxp.supernaturalworld.gui.guicontainer.GuiSupernaturalPlayerGuiContainer;
import com.wxp.supernaturalworld.gui.guicontainer.GuiSupernaturalShopGuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

/** @author wxp */
public class SupernaturalGuiHandler implements IGuiHandler {
  public static final int ID_SUPERNATURAL_PLAYER_UI = 1;
  public static final int ID_SUPERNATURAL_SHOP_UI = 2;

  @Nullable
  @Override
  public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
    if (ID == ID_SUPERNATURAL_PLAYER_UI) {
      return new GuiSupernaturalPlayerContainer(player);
    }
    if (ID == ID_SUPERNATURAL_SHOP_UI) {
      return new GuiSupernaturalShopContainer(player);
    }
    return null;
  }

  @Nullable
  @Override
  public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
    if (ID == ID_SUPERNATURAL_PLAYER_UI) {
      return new GuiSupernaturalPlayerGuiContainer(new GuiSupernaturalPlayerContainer(player));
    }
    if (ID == ID_SUPERNATURAL_SHOP_UI) {
      return new GuiSupernaturalShopGuiContainer(new GuiSupernaturalShopContainer(player));
    }
    return null;
  }
}
