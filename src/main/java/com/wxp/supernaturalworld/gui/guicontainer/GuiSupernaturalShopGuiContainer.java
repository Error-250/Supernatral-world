package com.wxp.supernaturalworld.gui.guicontainer;

import com.wxp.supernaturalworld.capability.SupernaturalEntityI;
import com.wxp.supernaturalworld.config.SupernaturalConfig;
import com.wxp.supernaturalworld.gui.button.GuiImageButton;
import com.wxp.supernaturalworld.gui.container.GuiSupernaturalShopContainer;
import com.wxp.supernaturalworld.manager.CapabilityManager;
import com.wxp.supernaturalworld.manager.ShopMenuManager;
import com.wxp.supernaturalworld.network.GuiShopContainerMessage;
import com.wxp.supernaturalworld.network.ShopSellMessage;
import com.wxp.supernaturalworld.register.NetworkRegister;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/** @author wxp */
public class GuiSupernaturalShopGuiContainer extends AbstractGuiSupernaturalGuiContainer {
  private GuiSupernaturalShopContainer supernaturalShopContainer;
  private static final ResourceLocation TEXTURE =
      new ResourceLocation(
          SupernaturalConfig.MOD_ID, "textures/gui/container/supernatural_shop_gui.png");
  private GuiImageButton sellButton;
  private GuiImageButton buyButton;
  private GuiImageButton leftLevelSliderButton;
  private GuiImageButton leftYearSliderButton;
  private GuiImageButton leftSliderButton;
  private GuiImageButton rightSliderButton;
  private GuiImageButton rightYearSliderButton;
  private GuiImageButton rightLevelSliderButton;
  private int index = 5;

  public GuiSupernaturalShopGuiContainer(GuiSupernaturalShopContainer container) {
    super(container);

    supernaturalShopContainer = container;
  }

  @Override
  ResourceLocation getBackgroundResource() {
    return TEXTURE;
  }

  @Override
  public void updateScreen() {
    super.updateScreen();
    leftLevelSliderButton.setEnable(ShopMenuManager.getPreLevelIndexInSellItem(index) != -1);
    leftYearSliderButton.setEnable(ShopMenuManager.getPreTypeIndexInSellItem(index) != -1);
    leftSliderButton.setEnable(index != 5);
    rightSliderButton.setEnable(index != ShopMenuManager.getSellItemSize() - 1);
    rightYearSliderButton.setEnable(ShopMenuManager.getNextTypeIndexInSellItem(index) != -1);
    rightLevelSliderButton.setEnable(ShopMenuManager.getNextLevelIndexInSellItem(index) != -1);
    if (supernaturalShopContainer
        .getEntityPlayer()
        .hasCapability(CapabilityManager.supernaturalEntityICapability, null)) {
      SupernaturalEntityI supernaturalEntityI =
          supernaturalShopContainer
              .getEntityPlayer()
              .getCapability(CapabilityManager.supernaturalEntityICapability, null);
      if (supernaturalEntityI == null) {
        sellButton.setEnable(false);
        buyButton.setEnable(false);
      } else {
        sellButton.setEnable(supernaturalShopContainer.getSupernaturalShopSellSlot().getHasStack());
        buyButton.setEnable(
            supernaturalEntityI.getPlayerSupernaturalMoney()
                > ShopMenuManager.getSellItem(index).getSellMoney());
      }
    } else {
      sellButton.setEnable(false);
      buyButton.setEnable(false);
    }
  }

  @Override
  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
    super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
    int middleOffsetX = (this.width - this.xSize) / 2;
    int middleOffsetY = (this.height - this.ySize) / 2;
    SupernaturalEntityI supernaturalEntityI =
        supernaturalShopContainer
            .getEntityPlayer()
            .getCapability(CapabilityManager.supernaturalEntityICapability, null);
    if (supernaturalEntityI != null) {
      drawString(
          this.fontRenderer,
          "Your money: " + supernaturalEntityI.getPlayerSupernaturalMoney(),
          middleOffsetX + 5,
          middleOffsetY + 5,
          0x000);
    }
    drawString(
        this.fontRenderer,
        "Sell:" + simpleMoney(ShopMenuManager.getSellItem(index).getSellMoney()),
        middleOffsetX + 94,
        middleOffsetY + 55,
        0x000);
    if (supernaturalShopContainer.getSupernaturalShopSellSlot().getHasStack()) {
      ItemStack itemStack = supernaturalShopContainer.getSupernaturalShopSellSlot().getStack();
      List<ShopMenuManager.ShopMenu> findItem =
          ShopMenuManager.getInitializedShopMenu().stream()
              .filter(
                  shopMenu ->
                      shopMenu.getBuyItem().getItem() == itemStack.getItem()
                          && shopMenu.getBuyItem().getMetadata() == itemStack.getMetadata())
              .collect(Collectors.toList());
      drawString(
          this.fontRenderer,
          "Sell:" + simpleMoney(findItem.get(0).getBuyMoney()),
          middleOffsetX + 94,
          middleOffsetY + 34,
          0x000);
    }
  }

  @Override
  public void initGui() {
    super.initGui();
    int middleOffsetX = (this.width - this.xSize) / 2;
    int middleOffsetY = (this.height - this.ySize) / 2;

    sellButton =
        new GuiImageButton(0, middleOffsetX + 70, middleOffsetY + 31, 25, 16, 43, 180, TEXTURE);
    sellButton.setDisEnableTextureOffset(43, 162);
    buyButton =
        new GuiImageButton(1, middleOffsetX + 70, middleOffsetY + 52, 25, 16, 43, 180, TEXTURE);
    buyButton.setDisEnableTextureOffset(43, 162);
    leftLevelSliderButton =
        new GuiImageButton(2, middleOffsetX + 3, middleOffsetY + 51, 20, 18, 132, 177, TEXTURE);
    leftLevelSliderButton.setDisEnableTextureOffset(131, 160);
    leftLevelSliderButton.setEnable(Boolean.FALSE);
    leftYearSliderButton =
        new GuiImageButton(3, middleOffsetX + 24, middleOffsetY + 51, 15, 18, 91, 177, TEXTURE);
    leftYearSliderButton.setDisEnableTextureOffset(91, 160);
    leftYearSliderButton.setEnable(Boolean.FALSE);
    leftSliderButton =
        new GuiImageButton(4, middleOffsetX + 39, middleOffsetY + 51, 12, 18, 21, 177, TEXTURE);
    leftSliderButton.setEnable(Boolean.FALSE);
    leftSliderButton.setDisEnableTextureOffset(21, 159);
    rightSliderButton =
        new GuiImageButton(5, middleOffsetX + 123, middleOffsetY + 51, 12, 18, 7, 177, TEXTURE);
    rightSliderButton.setDisEnableTextureOffset(7, 159);
    rightYearSliderButton =
        new GuiImageButton(6, middleOffsetX + 137, middleOffsetY + 51, 15, 18, 74, 177, TEXTURE);
    rightYearSliderButton.setDisEnableTextureOffset(74, 160);
    rightLevelSliderButton =
        new GuiImageButton(7, middleOffsetX + 154, middleOffsetY + 51, 19, 18, 107, 177, TEXTURE);
    rightLevelSliderButton.setDisEnableTextureOffset(107, 160);
    this.addButton(sellButton);
    this.addButton(buyButton);
    this.addButton(leftLevelSliderButton);
    this.addButton(leftYearSliderButton);
    this.addButton(leftSliderButton);
    this.addButton(rightSliderButton);
    this.addButton(rightYearSliderButton);
    this.addButton(rightLevelSliderButton);
  }

  @Override
  protected void actionPerformed(GuiButton button) throws IOException {
    int buttonId = button.id;
    boolean indexChangeFlag = false;
    switch (buttonId) {
      case 0:
        // sell
        ItemStack itemStack = supernaturalShopContainer.getSupernaturalShopSellSlot().getStack();
        Iterator<ShopMenuManager.ShopMenu> iterator =
            ShopMenuManager.getInitializedShopMenu().iterator();
        int i = 0;
        while (iterator.hasNext()) {
          ShopMenuManager.ShopMenu shopMenu = iterator.next();
          if (itemStack.getItem() == shopMenu.getBuyItem().getItem()
              && itemStack.getMetadata() == shopMenu.getBuyItem().getMetadata()) {
            NetworkRegister.simpleNetworkWrapper.sendToServer(new ShopSellMessage(i, 1));
            break;
          }
          i++;
        }
        break;
      case 1:
        // buy
        NetworkRegister.simpleNetworkWrapper.sendToServer(new ShopSellMessage(index, 2));
        break;
      case 2:
        int preLevelIndex = ShopMenuManager.getPreLevelIndexInSellItem(index);
        if (preLevelIndex == -1) {
          preLevelIndex = 5;
        }
        index = preLevelIndex;
        indexChangeFlag = true;
        break;
      case 3:
        int preTypeIndex = ShopMenuManager.getPreTypeIndexInSellItem(index);
        if (preTypeIndex == -1) {
          preTypeIndex = 5;
        }
        index = preTypeIndex;
        indexChangeFlag = true;
        break;
      case 4:
        // left button
        index--;
        indexChangeFlag = true;
        break;
      case 5:
        // right button
        index++;
        indexChangeFlag = true;
        break;
      case 6:
        int nextTypeIndex = ShopMenuManager.getNextTypeIndexInSellItem(index);
        if (nextTypeIndex == -1) {
          nextTypeIndex = 0;
        }
        index = nextTypeIndex;
        indexChangeFlag = true;
        break;
      case 7:
        int nextLevelIndex = ShopMenuManager.getNextLevelIndexInSellItem(index);
        if (nextLevelIndex == -1) {
          nextLevelIndex = 0;
        }
        index = nextLevelIndex;
        indexChangeFlag = true;
        break;
      default:
    }
    if (indexChangeFlag) {
      index = index % ShopMenuManager.getSellItemSize();
      NetworkRegister.simpleNetworkWrapper.sendToServer(new GuiShopContainerMessage(index));
      supernaturalShopContainer.updateSellItem(index);
    }
    super.actionPerformed(button);
  }

  private String simpleMoney(long money) {
    if (money > 10000) {
      if (money % 10000 == 0) {
        return money / 10000 + "W";
      } else {
        return money / 10000.0 + "W";
      }
    } else if (money > 1000) {
      if (money % 1000 == 0) {
        return money / 1000 + "K";
      }
      return money / 1000.0 + "k";
    }
    return String.valueOf(money);
  }
}
