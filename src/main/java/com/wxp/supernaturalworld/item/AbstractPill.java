package com.wxp.supernaturalworld.item;

import com.wxp.supernaturalworld.config.SupernaturalConfig;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.Arrays;

/** @author wxp */
public abstract class AbstractPill extends ItemFood {
  public AbstractPill(int amount, float saturation) {
    super(amount, saturation, false);
    setRegistryName(getItemName());
    setHasSubtypes(true);
    this.addPropertyOverride(
        new ResourceLocation(SupernaturalConfig.MOD_ID, "potion_level"),
        (stack, worldIn, entityIn) -> getPillLevel(stack).ordinal());
  }

  /**
   * 物品名称
   *
   * @return 获取物品名称
   */
  abstract String getItemName();

  /**
   * 是否允许使用者使用
   *
   * @param entityPlayer 使用者
   * @param itemStack 使用的物品
   * @return 是否允许使用
   */
  boolean allowEat(EntityPlayer entityPlayer, ItemStack itemStack) {
    return true;
  }

  public PillLevel getPillLevel(ItemStack itemStack) {
    int metaData = itemStack.getMetadata();
    return PillLevel.valueOfNum(metaData);
  }

  @Override
  public String getUnlocalizedName(ItemStack stack) {
    PillLevel pillLevel = getPillLevel(stack);
    return String.format("item.%s_%s", pillLevel.name().toLowerCase(), getItemName());
  }

  @Override
  public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
    if (this.isInCreativeTab(tab)) {
      items.add(new ItemStack(this, 1, 0));
      items.add(new ItemStack(this, 1, 1));
      items.add(new ItemStack(this, 1, 2));
    }
  }

  @Override
  public ActionResult<ItemStack> onItemRightClick(
      World worldIn, EntityPlayer playerIn, EnumHand handIn) {
    ItemStack itemstack = playerIn.getHeldItem(handIn);
    if (!allowEat(playerIn, itemstack)) {
      return new ActionResult<>(EnumActionResult.FAIL, itemstack);
    }
    return super.onItemRightClick(worldIn, playerIn, handIn);
  }

  public enum PillLevel {
    PRIMARY,
    MIDDLE,
    SENIOR;

    public static PillLevel valueOfNum(int num) {
      return Arrays.stream(values())
          .filter(pillLevel -> pillLevel.ordinal() == num)
          .findAny()
          .orElse(null);
    }
  }
}
