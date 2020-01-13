package com.wxp.supernaturalworld.item;

import com.wxp.supernaturalworld.SupernaturalMod;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

/** @author wxp */
public class SupernaturalRingItemImpl extends Item implements SupernaturalRingItemI {
  private String name = "supernatural_ring";

  public SupernaturalRingItemImpl() {
    this(10);
  }

  public SupernaturalRingItemImpl(int year) {
    setUnlocalizedName(name);
    setRegistryName(name);
    setHasSubtypes(true);
  }

  @Override
  public String getUnlocalizedName(ItemStack stack) {
    return super.getUnlocalizedName() + "_" + getRingLevel(stack).name();
  }

  @Override
  public RingLevel getRingLevel(ItemStack stack) {
    int ringYears = getYears(stack);
    if (ringYears <= 10) {
      return RingLevel.TEN;
    } else if (ringYears < 1000) {
      return RingLevel.HUNDRED;
    } else if (ringYears < 10000) {
      return RingLevel.THOUSAND;
    } else if (ringYears < 100000) {
      return RingLevel.TEN_THOUSAND;
    } else {
      return RingLevel.HUNDRED_THOUSAND;
    }
  }

  @Override
  public int getYears(ItemStack stack) {
    int meta = stack.getMetadata();
    int years;
    if (meta == 0) {
      years = 10;
    } else {
      years = 100 * meta;
    }
    return years;
  }

  @Override
  public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
    if (this.isInCreativeTab(tab)) {
      for (int i = 0; i <= 1000; i++) {
        items.add(new ItemStack(this, 1, i));
      }
    }
  }

  @Override
  public ActionResult<ItemStack> onItemRightClick(
      World worldIn, EntityPlayer playerIn, EnumHand handIn) {
    ItemStack itemStack = playerIn.getHeldItemMainhand();
    SupernaturalMod.logger.info("Year:{} level:{}", getYears(itemStack), getRingLevel(itemStack));
    return super.onItemRightClick(worldIn, playerIn, handIn);
  }
}
