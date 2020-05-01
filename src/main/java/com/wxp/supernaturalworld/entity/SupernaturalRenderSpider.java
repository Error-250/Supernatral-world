package com.wxp.supernaturalworld.entity;

import com.wxp.supernaturalworld.config.SupernaturalConfig;
import com.wxp.supernaturalworld.domain.SupernaturalHelper;
import com.wxp.supernaturalworld.domain.SupernaturalLevel;
import net.minecraft.client.model.ModelSpider;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

/** @author wxp */
public class SupernaturalRenderSpider extends RenderLiving<SupernaturalSpiderEntity> {
  private static final ResourceLocation SPIDER_TEN_TEXTURES =
      new ResourceLocation(
          SupernaturalConfig.MOD_ID, "textures/entity/supernatural_spider_ten.png");
  private static final ResourceLocation SPIDER_HUNDRED_TEXTURES =
      new ResourceLocation(
          SupernaturalConfig.MOD_ID, "textures/entity/supernatural_spider_hundred.png");
  private static final ResourceLocation SPIDER_THOUSAND_TEXTURES =
      new ResourceLocation(
          SupernaturalConfig.MOD_ID, "textures/entity/supernatural_spider_thousand.png");
  private static final ResourceLocation SPIDER_TEN_THOUSAND_TEXTURES =
      new ResourceLocation(
          SupernaturalConfig.MOD_ID, "textures/entity/supernatural_spider_ten_thousand.png");
  private static final ResourceLocation SPIDER_HUNDRED_THOUSAND_TEXTURES =
      new ResourceLocation(
          SupernaturalConfig.MOD_ID, "textures/entity/supernatural_spider_hundred_thousand.png");
  private SupernaturalLevel lastLevel;

  public SupernaturalRenderSpider(RenderManager rendermanagerIn) {
    super(rendermanagerIn, new ModelSpider(), 1.0f);
  }

  @Override
  protected float getDeathMaxRotation(SupernaturalSpiderEntity entityLivingBaseIn) {
    return 180.0F;
  }

  @Nullable
  @Override
  protected ResourceLocation getEntityTexture(SupernaturalSpiderEntity entity) {
    int currentYears = entity.getActiveYears();
    SupernaturalLevel level = SupernaturalHelper.calculateLevel(currentYears);
    switch (level) {
      case HUNDRED:
        return SPIDER_HUNDRED_TEXTURES;
      case THOUSAND:
        return SPIDER_THOUSAND_TEXTURES;
      case TEN_THOUSAND:
        return SPIDER_TEN_THOUSAND_TEXTURES;
      case HUNDRED_THOUSAND:
        return SPIDER_HUNDRED_THOUSAND_TEXTURES;
      case TEN:
      default:
        return SPIDER_TEN_TEXTURES;
    }
  }

  @Override
  public boolean shouldRender(
      SupernaturalSpiderEntity livingEntity,
      ICamera camera,
      double camX,
      double camY,
      double camZ) {
    SupernaturalLevel level = SupernaturalHelper.calculateLevel(livingEntity.getActiveYears());
    ;
    if (lastLevel == null || !lastLevel.equals(level)) {
      return true;
    }
    return super.shouldRender(livingEntity, camera, camX, camY, camZ);
  }

  @Override
  public void doRender(
      SupernaturalSpiderEntity entity,
      double x,
      double y,
      double z,
      float entityYaw,
      float partialTicks) {
    super.doRender(entity, x, y, z, entityYaw, partialTicks);
    lastLevel = SupernaturalHelper.calculateLevel(entity.getActiveYears());
  }
}
