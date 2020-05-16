package com.wxp.supernaturalworld.potion;

import com.wxp.supernaturalworld.config.SupernaturalConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

import java.util.Objects;

/** @author wxp */
public abstract class AbstractPotion extends Potion {
  protected AbstractPotion(int liquidColorIn) {
    super(false, liquidColorIn);
    setRegistryName(SupernaturalConfig.MOD_ID, getPotionName());
    setPotionName(String.format("potion.%s", getPotionName()));
  }

  /**
   * 获取药效名
   *
   * @return 获取药效名
   */
  abstract String getPotionName();

  int getTextureX() {
    return 0;
  }

  int getTextureY() {
    return 0;
  }

  public PotionEffect getPotionEffect(int duration) {
    return new PotionEffect(this, duration, 0);
  }

  @Override
  public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc) {
    mc.getTextureManager()
        .bindTexture(
            new ResourceLocation(SupernaturalConfig.MOD_ID, "textures/gui/container/effect_icon.png"));
    Objects.requireNonNull(mc.currentScreen)
        .drawTexturedModalRect(x + 6, y + 7, getTextureX(), getTextureY(), 18, 18);
    super.renderInventoryEffect(x, y, effect, mc);
  }
}
