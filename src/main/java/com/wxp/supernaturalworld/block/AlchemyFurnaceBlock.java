package com.wxp.supernaturalworld.block;

import com.google.common.collect.Lists;
import com.wxp.supernaturalworld.SupernaturalMod;
import com.wxp.supernaturalworld.block.tileentity.TileEntityAlchemyFurnace;
import com.wxp.supernaturalworld.gui.SupernaturalGuiHandler;
import com.wxp.supernaturalworld.item.AbstractPill;
import com.wxp.supernaturalworld.manager.ItemManager;
import lombok.AllArgsConstructor;
import lombok.Data;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/** @author wxp */
public class AlchemyFurnaceBlock extends Block implements SupernaturalNormalBlockI {
  private String name = "alchemy_furnace";
  public static final PropertyBool BURNING = PropertyBool.create("burning");

  public AlchemyFurnaceBlock() {
    super(Material.IRON);
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
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, BURNING);
  }

  @Override
  public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
    if (stateIn.getValue(BURNING)) {
      double d0 = (double) pos.getX() + 0.5D;
      double d1 = (double) pos.getY() + rand.nextDouble() * 6.0D / 16.0D;
      double d2 = (double) pos.getZ() + 0.5D;
      double d4 = rand.nextDouble() * 0.6D - 0.3D;

      if (rand.nextDouble() < 0.1D) {
        worldIn.playSound(
            (double) pos.getX() + 0.5D,
            (double) pos.getY(),
            (double) pos.getZ() + 0.5D,
            SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE,
            SoundCategory.BLOCKS,
            1.0F,
            1.0F,
            false);
      }

      worldIn.spawnParticle(
          EnumParticleTypes.SMOKE_NORMAL, d0 - 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
      worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 - 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);

      worldIn.spawnParticle(
          EnumParticleTypes.SMOKE_NORMAL, d0 + 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
      worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);

      worldIn.spawnParticle(
          EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 - 0.52D, 0.0D, 0.0D, 0.0D);
      worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 - 0.52D, 0.0D, 0.0D, 0.0D);

      worldIn.spawnParticle(
          EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 + 0.52D, 0.0D, 0.0D, 0.0D);
      worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 + 0.52D, 0.0D, 0.0D, 0.0D);
    }
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return state.getValue(BURNING) ? 1 : 0;
  }

  @Override
  public IBlockState getStateFromMeta(int meta) {
    boolean burning = meta == 1;
    return getDefaultState().withProperty(BURNING, burning);
  }

  @Nullable
  @Override
  public TileEntity createTileEntity(World world, IBlockState state) {
    return new TileEntityAlchemyFurnace();
  }

  @Override
  public boolean hasTileEntity(IBlockState state) {
    return true;
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
          SupernaturalGuiHandler.ID_ALCHEMY_FURNACE_UI,
          worldIn,
          pos.getX(),
          pos.getY(),
          pos.getZ());
    }
    return Boolean.TRUE;
  }

  @Override
  public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
    TileEntityAlchemyFurnace tileEntityAlchemyFurnace =
        (TileEntityAlchemyFurnace) worldIn.getTileEntity(pos);
    if (tileEntityAlchemyFurnace != null) {
      tileEntityAlchemyFurnace.dropAllItem();
    }
    super.breakBlock(worldIn, pos, state);
  }

  @Data
  @AllArgsConstructor
  public static class AlchemyMenu {
    private List<AlchemyMaterial> alchemyMaterials;
    private AbstractPill.PillLevel level;
    private ItemStack outItem;

    @Data
    @AllArgsConstructor
    public static class AlchemyMaterial {
      private int index;
      private Item needItem;
    }

    public static List<AlchemyMenu> getSupportAlchemyMenu() {
      return Lists.newArrayList(
          new AlchemyMenu(
              Lists.newArrayList(
                  new AlchemyMaterial(0, Items.ENDER_PEARL),
                  new AlchemyMaterial(1, Items.ROTTEN_FLESH)),
              AbstractPill.PillLevel.PRIMARY,
              new ItemStack(ItemManager.pillNotHungry)),
          new AlchemyMenu(
              Lists.newArrayList(
                  new AlchemyMaterial(0, Items.DYE), new AlchemyMaterial(1, Items.ROTTEN_FLESH)),
              AbstractPill.PillLevel.MIDDLE,
              new ItemStack(ItemManager.pillNotHungry, 1, 1)),
          new AlchemyMenu(
              Lists.newArrayList(
                  new AlchemyMaterial(0, Items.DRAGON_BREATH),
                  new AlchemyMaterial(1, Items.ROTTEN_FLESH)),
              AbstractPill.PillLevel.SENIOR,
              new ItemStack(ItemManager.pillNotHungry, 1, 2)),
          new AlchemyMenu(
              Lists.newArrayList(
                  new AlchemyMaterial(0, Items.ENDER_PEARL),
                  new AlchemyMaterial(1, Items.MAGMA_CREAM)),
              AbstractPill.PillLevel.PRIMARY,
              new ItemStack(ItemManager.pillSupernaturalPower)),
          new AlchemyMenu(
              Lists.newArrayList(
                  new AlchemyMaterial(0, Items.DYE), new AlchemyMaterial(1, Items.MAGMA_CREAM)),
              AbstractPill.PillLevel.MIDDLE,
              new ItemStack(ItemManager.pillSupernaturalPower, 1, 1)),
          new AlchemyMenu(
              Lists.newArrayList(
                  new AlchemyMaterial(0, Items.DRAGON_BREATH),
                  new AlchemyMaterial(1, Items.MAGMA_CREAM)),
              AbstractPill.PillLevel.SENIOR,
              new ItemStack(ItemManager.pillSupernaturalPower, 1, 2)));
    }
  }
}
