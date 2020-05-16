package com.wxp.supernaturalworld.gui.guicontainer;

import com.wxp.supernaturalworld.config.SupernaturalConfig;
import com.wxp.supernaturalworld.gui.container.GuiAlchemyFurnaceContainer;
import net.minecraft.util.ResourceLocation;

/** @author wxp */
public class GuiAlchemyFurnaceGuiContainer extends AbstractGuiSupernaturalGuiContainer {
  private static final ResourceLocation TEXTURE =
      new ResourceLocation(
          SupernaturalConfig.MOD_ID, "textures/gui/container/alchemy_furnace_gui.png");

  public GuiAlchemyFurnaceGuiContainer(GuiAlchemyFurnaceContainer inventorySlotsIn) {
    super(inventorySlotsIn);
  }

  @Override
  ResourceLocation getBackgroundResource() {
    return TEXTURE;
  }

  @Override
  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
    super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);

    int middleOffsetX = (this.width - this.xSize) / 2;
    int middleOffsetY = (this.height - this.ySize) / 2;

    GuiAlchemyFurnaceContainer alchemyFurnaceContainer =
        (GuiAlchemyFurnaceContainer) this.inventorySlots;
    int textureWidth = 1 + (int) Math.ceil(22.0 * alchemyFurnaceContainer.burnProgress());
    this.drawTexturedModalRect(middleOffsetX + 90, middleOffsetY + 33, 176, 16, textureWidth, 17);
    if (alchemyFurnaceContainer.burnProgress() > 0) {
      this.drawTexturedModalRect(middleOffsetX + 66, middleOffsetY + 33, 176, 2, 14, 13);
    }
  }
}
