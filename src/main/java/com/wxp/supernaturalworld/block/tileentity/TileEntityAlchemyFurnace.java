package com.wxp.supernaturalworld.block.tileentity;

import com.wxp.supernaturalworld.block.AlchemyFurnaceBlock;
import com.wxp.supernaturalworld.item.AbstractPill;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.ItemCoal;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.List;

/** @author wxp */
public class TileEntityAlchemyFurnace extends TileEntity implements ITickable {
  private int burnTime = 0;
  private ItemStackHandler upInventory = new ItemStackHandler(3);
  private ItemStackHandler downInventory = new ItemStackHandler();
  private ItemStackHandler outInventory = new ItemStackHandler();
  private final String keyUpInventory = "up_inventory";
  private final String keyDownInventory = "down_inventory";
  private final String keyOutInventory = "out_inventory";
  private final String keyBurnTime = "burn_time";

  @Override
  public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
    if (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.equals(capability)) {
      return true;
    }
    return super.hasCapability(capability, facing);
  }

  @Nullable
  @Override
  public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
    if (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.equals(capability)) {
      if (facing == null) {
        return (T) outInventory;
      }
      switch (facing) {
        case UP:
          return (T) upInventory;
        case DOWN:
          return (T) downInventory;
        default:
          return (T) outInventory;
      }
    }
    return super.getCapability(capability, facing);
  }

  @Override
  public void readFromNBT(NBTTagCompound compound) {
    super.readFromNBT(compound);
    upInventory.deserializeNBT(compound.getCompoundTag(keyUpInventory));
    downInventory.deserializeNBT(compound.getCompoundTag(keyDownInventory));
    outInventory.deserializeNBT(compound.getCompoundTag(keyOutInventory));
    burnTime = compound.getInteger(keyBurnTime);
  }

  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound compound) {
    compound.setTag(keyUpInventory, upInventory.serializeNBT());
    compound.setTag(keyDownInventory, downInventory.serializeNBT());
    compound.setTag(keyOutInventory, outInventory.serializeNBT());
    compound.setInteger(keyBurnTime, burnTime);
    super.writeToNBT(compound);
    return compound;
  }

  @Override
  public boolean shouldRefresh(
      World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
    return oldState.getBlock() != newSate.getBlock();
  }

  @Override
  public void update() {
    if (!this.world.isRemote) {
      List<AlchemyFurnaceBlock.AlchemyMenu> alchemyMenus =
          AlchemyFurnaceBlock.AlchemyMenu.getSupportAlchemyMenu();
      AlchemyFurnaceBlock.AlchemyMenu matchMenu =
          alchemyMenus.stream()
              .filter(
                  alchemyMenu ->
                      alchemyMenu.getAlchemyMaterials().stream()
                          .allMatch(
                              alchemyMaterial -> {
                                ItemStack realItem =
                                    upInventory.extractItem(alchemyMaterial.getIndex(), 1, true);
                                return !realItem.isEmpty()
                                    && alchemyMaterial.getNeedItem() == realItem.getItem();
                              }))
              .findFirst()
              .orElse(null);
      IBlockState state = this.world.getBlockState(pos);
      if (matchMenu != null) {
        ItemStack fuelItemStack = downInventory.extractItem(0, 1, true);
        ItemStack outItem = matchMenu.getOutItem().copy();
        boolean hasFuel =
            state.getValue(AlchemyFurnaceBlock.BURNING)
                || (!fuelItemStack.isEmpty() && fuelItemStack.getItem() instanceof ItemCoal);
        boolean canBurn = hasFuel && outInventory.insertItem(0, outItem, true).isEmpty();
        if (canBurn) {
          downInventory.extractItem(0, 1, false);
          this.world.setBlockState(
              pos, state.withProperty(AlchemyFurnaceBlock.BURNING, Boolean.TRUE));

          int burnTotalTime = 100 * (matchMenu.getLevel().ordinal() + 1);

          burnTime++;
          if (this.burnTime >= burnTotalTime) {
            this.burnTime = 0;
            upInventory.extractItem(0, 1, false);
            if (AbstractPill.PillLevel.SENIOR.equals(matchMenu.getLevel())) {
              ItemStack emptyBottle = new ItemStack(Items.GLASS_BOTTLE);
              upInventory.insertItem(0, emptyBottle, false);
            }
            upInventory.extractItem(1, 1, false);
            upInventory.extractItem(2, 1, false);
            outInventory.insertItem(0, outItem, false);
            this.markDirty();
          }
        }
      } else {
        this.world.setBlockState(
            pos, state.withProperty(AlchemyFurnaceBlock.BURNING, Boolean.FALSE));
      }
    }
  }

  public int getBurnTime() {
    return this.burnTime;
  }

  public void setBurnTime(int burnTime) {
    this.burnTime = burnTime;
  }

  public void dropAllItem() {
    ItemStack allItemStack = upInventory.getStackInSlot(0);
    if (!allItemStack.isEmpty()) {
      ItemStack upDropItem = upInventory.extractItem(0, allItemStack.getCount(), false);
      Block.spawnAsEntity(this.world, pos, upDropItem);
    }
    allItemStack = upInventory.getStackInSlot(1);
    if (!allItemStack.isEmpty()) {
      ItemStack upDropItem = upInventory.extractItem(1, allItemStack.getCount(), false);
      Block.spawnAsEntity(this.world, pos, upDropItem);
    }
    allItemStack = upInventory.getStackInSlot(2);
    if (!allItemStack.isEmpty()) {
      ItemStack upDropItem = upInventory.extractItem(2, allItemStack.getCount(), false);
      Block.spawnAsEntity(this.world, pos, upDropItem);
    }
    allItemStack = downInventory.getStackInSlot(0);
    if (!allItemStack.isEmpty()) {
      ItemStack downDropItem = downInventory.extractItem(0, allItemStack.getCount(), false);
      Block.spawnAsEntity(this.world, pos, downDropItem);
    }
    allItemStack = outInventory.getStackInSlot(0);
    if (!allItemStack.isEmpty()) {
      ItemStack outDropItem = outInventory.extractItem(0, allItemStack.getCount(), false);
      Block.spawnAsEntity(this.world, pos, outDropItem);
    }
  }
}
