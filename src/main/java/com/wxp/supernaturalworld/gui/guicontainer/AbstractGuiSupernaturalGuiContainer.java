package com.wxp.supernaturalworld.gui.guicontainer;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

/** @author wxp */
public abstract class AbstractGuiSupernaturalGuiContainer extends GuiContainer {

  public AbstractGuiSupernaturalGuiContainer(Container inventorySlotsIn) {
    super(inventorySlotsIn);
    this.xSize = 176;
    this.ySize = 156;
  }

  /**
   * 获取GUI背景图片文件名
   *
   * @return resource
   */
  abstract ResourceLocation getBackgroundResource();

  @Override
  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
    GlStateManager.color(1.0F, 1.0F, 1.0F);

    this.mc.getTextureManager().bindTexture(getBackgroundResource());
    int middleOffsetX = (this.width - this.xSize) / 2;
    int middleOffsetY = (this.height - this.ySize) / 2;

    this.drawTexturedModalRect(middleOffsetX, middleOffsetY, 0, 0, this.xSize, this.ySize);
  }

  @Override
  public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    super.drawScreen(mouseX, mouseY, partialTicks);
    this.renderHoveredToolTip(mouseX, mouseY);
  }
}
