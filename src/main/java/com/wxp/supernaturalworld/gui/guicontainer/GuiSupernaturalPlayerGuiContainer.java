package com.wxp.supernaturalworld.gui.guicontainer;

import com.wxp.supernaturalworld.capability.SupernaturalEntityI;
import com.wxp.supernaturalworld.config.SupernaturalConfig;
import com.wxp.supernaturalworld.gui.container.GuiSupernaturalPlayerContainer;
import com.wxp.supernaturalworld.manager.CapabilityManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;

/** @author wxp */
public class GuiSupernaturalPlayerGuiContainer extends AbstractGuiSupernaturalGuiContainer {
  private static final ResourceLocation TEXTURE =
      new ResourceLocation(
          SupernaturalConfig.MOD_ID, "textures/gui/container/supernatural_player_gui.png");

  public GuiSupernaturalPlayerGuiContainer(GuiSupernaturalPlayerContainer container) {
    super(container);
  }

  @Override
  ResourceLocation getBackgroundResource() {
    return TEXTURE;
  }

  @Override
  protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
    GuiSupernaturalPlayerContainer guiSupernaturalPlayerContainer =
        (GuiSupernaturalPlayerContainer) this.inventorySlots;
    SupernaturalEntityI supernaturalEntityI =
        guiSupernaturalPlayerContainer
            .getEntityPlayer()
            .getCapability(CapabilityManager.supernaturalEntityICapability, null);
    if (supernaturalEntityI != null) {
      TextComponentTranslation supernaturalLevelMessage =
          new TextComponentTranslation(
              "supernatural_player_container.supernatural_level",
              supernaturalEntityI.getPlayerSupernaturalPowerLevel());
      String supernaturalLevelMessageStr = supernaturalLevelMessage.getFormattedText();
      this.fontRenderer.drawString(supernaturalLevelMessageStr, 9, 16, 0x404040);

      TextComponentTranslation leaveSupernaturalPower =
          new TextComponentTranslation(
              "supernatural_player_container.current_supernatural_power",
              supernaturalEntityI.getPlayerCurrentSupernaturalPower());
      String leaveSupernaturalPowerStr = leaveSupernaturalPower.getFormattedText();
      this.fontRenderer.drawString(
          leaveSupernaturalPowerStr, 9, 16 + fontRenderer.FONT_HEIGHT + 1, 0x404040);

      TextComponentTranslation supernaturalPowerMaxLimit =
          new TextComponentTranslation(
              "supernatural_player_container.supernatural_power_max_limit",
              supernaturalEntityI.getPlayerSupernaturalPowerMaxLimit());
      String supernaturalPowerMaxLimitStr = supernaturalPowerMaxLimit.getFormattedText();
      this.fontRenderer.drawString(
          supernaturalPowerMaxLimitStr, 9, 16 + fontRenderer.FONT_HEIGHT * 2 + 1, 0x404040);
    }
    super.drawGuiContainerForegroundLayer(mouseX, mouseY);
  }
}
