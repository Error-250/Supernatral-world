package com.wxp.supernaturalworld.gui.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

/** @author wxp */
public class GuiImageButton extends GuiButton {
  private ResourceLocation background;
  private int enableTextureX;
  private int enableTextureY;
  private int hoverTextureX;
  private int hoverTextureY;
  private int disEnableTextureX;
  private int disEnableTextureY;

  public GuiImageButton(
      int buttonId,
      int x,
      int y,
      int with,
      int height,
      int textureX,
      int textureY,
      ResourceLocation backgroundImage) {
    super(buttonId, x, y, "");
    this.width = with;
    this.height = height;
    this.background = backgroundImage;
    this.enableTextureX = textureX;
    this.enableTextureY = textureY;
    this.hoverTextureX = textureX;
    this.hoverTextureY = textureY;
    this.disEnableTextureX = textureX;
    this.disEnableTextureY = textureY;
  }

  public void setEnable(boolean enable) {
    this.enabled = enable;
  }

  public void setDisEnableTextureOffset(int x, int y) {
    this.disEnableTextureX = x;
    this.disEnableTextureY = y;
  }

  public void setHoverTextureOffset(int x, int y) {
    this.hoverTextureX = x;
    this.hoverTextureY = y;
  }

  @Override
  public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
    if (this.visible) {
      GlStateManager.color(1.0F, 1.0F, 1.0F);

      mc.getTextureManager().bindTexture(background);
      this.hovered =
          mouseX >= this.x
              && mouseY >= this.y
              && mouseX < this.x + this.width
              && mouseY < this.y + this.height;

      if (this.enabled && this.hovered) {
        this.drawTexturedModalRect(
            this.x, this.y, hoverTextureX, hoverTextureY, this.width, this.height);
      } else if (this.enabled) {
        this.drawTexturedModalRect(
            this.x, this.y, enableTextureX, enableTextureY, this.width, this.height);
      } else {
        this.drawTexturedModalRect(
            this.x, this.y, disEnableTextureX, disEnableTextureY, this.width, this.height);
      }
    }
  }
}
