package com.wxp.supernaturalworld.capability.storage;

import com.wxp.supernaturalworld.capability.SupernaturalEntityI;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

/** @author wxp */
public class SupernaturalCapabilityStorage implements Capability.IStorage<SupernaturalEntityI> {
  @Nullable
  @Override
  public NBTBase writeNBT(
      Capability<SupernaturalEntityI> capability, SupernaturalEntityI instance, EnumFacing side) {
    NBTTagCompound nbtTagCompound = new NBTTagCompound();
    nbtTagCompound.setLong(
        "current_supernatural_power", instance.getPlayerCurrentSupernaturalPower());
    nbtTagCompound.setLong(
        "supernatural_power_max_limit", instance.getPlayerSupernaturalPowerMaxLimit());
    nbtTagCompound.setTag(
        "supernatural_ring", instance.getSupernaturalRingHandler().serializeNBT());
    return nbtTagCompound;
  }

  @Override
  public void readNBT(
      Capability<SupernaturalEntityI> capability,
      SupernaturalEntityI instance,
      EnumFacing side,
      NBTBase nbt) {
    NBTTagCompound nbtTagCompound = (NBTTagCompound) nbt;
    instance.setPlayerCurrentSupernaturalPower(
        nbtTagCompound.getLong("current_supernatural_power"));
    instance.setPlayerSupernaturalPowerMaxLimit(
        nbtTagCompound.getLong("supernatural_power_max_limit"));
    instance
        .getSupernaturalRingHandler()
        .deserializeNBT(nbtTagCompound.getCompoundTag("supernatural_ring"));
  }
}
