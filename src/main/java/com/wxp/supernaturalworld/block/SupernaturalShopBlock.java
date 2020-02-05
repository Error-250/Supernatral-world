package com.wxp.supernaturalworld.block;

import com.wxp.supernaturalworld.SupernaturalMod;
import com.wxp.supernaturalworld.gui.SupernaturalGuiHandler;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Objects;

/** @author wxp */
public class SupernaturalShopBlock extends Block implements SupernaturalNormalBlockI {
  private String name = "supernatural_shop";

  public SupernaturalShopBlock() {
    super(Material.ROCK);
    setHardness(0.5f);
    setSoundType(SoundType.METAL);
    setUnlocalizedName(name);
    setRegistryName(name);
  }

  @Override
  public ItemBlock getItemBlock() {
    ItemBlock itemBlock = new ItemBlock(this);
    itemBlock.setRegistryName(Objects.requireNonNull(this.getRegistryName()));
    return itemBlock;
  }

  @Override
  public Block getSelf() {
    return this;
  }

  @Override
  public boolean onBlockActivated(
      World worldIn,
      BlockPos pos,
      IBlockState state,
      EntityPlayer playerIn,
      EnumHand hand,
      EnumFacing facing,
      float hitX,
      float hitY,
      float hitZ) {
    if (!worldIn.isRemote) {
      playerIn.openGui(
          SupernaturalMod.INSTANCE,
          SupernaturalGuiHandler.ID_SUPERNATURAL_SHOP_UI,
          worldIn,
          pos.getX(),
          pos.getY(),
          pos.getZ());
    }
    return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
  }
}
