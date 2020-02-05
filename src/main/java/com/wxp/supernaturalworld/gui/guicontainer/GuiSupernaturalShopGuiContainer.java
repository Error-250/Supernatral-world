package com.wxp.supernaturalworld.gui.guicontainer;

import com.wxp.supernaturalworld.config.SupernaturalConfig;
import com.wxp.supernaturalworld.gui.container.GuiSupernaturalShopContainer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.util.ResourceLocation;

/** @author wxp */
public class GuiSupernaturalShopGuiContainer extends AbstractGuiSupernaturalGuiContainer {
  private GuiSupernaturalShopContainer supernaturalShopContainer;
  private static final ResourceLocation TEXTURE =
      new ResourceLocation(
          SupernaturalConfig.MOD_ID, "textures/gui/container/supernatural_shop_gui.png");
  private GuiLabel label;

  public GuiSupernaturalShopGuiContainer(GuiSupernaturalShopContainer container) {
    super(container);

    supernaturalShopContainer = container;
  }

  @Override
  ResourceLocation getBackgroundResource() {
    return TEXTURE;
  }

  @Override
  public void initGui() {
    int middleOffsetX = (this.width - this.xSize) / 2;
    int middleOffsetY = (this.height - this.ySize) / 2;
    this.label =
        new GuiLabel(
            this.fontRenderer,
            0,
            middleOffsetX + 44,
            middleOffsetY + 20,
            fontRenderer.getStringWidth("Sells: 0"),
            8,
            0xff000000);
    label.addLine("Sells: 0");
    this.labelList.add(label);
    this.addButton(
        new GuiButton(
            0,
            middleOffsetX + 100,
            middleOffsetY + 20,
            fontRenderer.getStringWidth("sell") * 2,
            15,
            "Sell"));
    super.initGui();
  }
}
