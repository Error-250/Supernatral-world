package com.wxp.supernaturalworld.capability.storage;

import com.wxp.supernaturalworld.capability.BindingEntityI;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StringUtils;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;
import java.util.UUID;

/** @author wxp */
public class BindingEntityCapabilityStorage implements Capability.IStorage<BindingEntityI> {
  @Nullable
  @Override
  public NBTBase writeNBT(
      Capability<BindingEntityI> capability, BindingEntityI instance, EnumFacing side) {
    NBTTagCompound nbtTagCompound = new NBTTagCompound();
    if (instance.getBindingPlayerUuid() == null) {
      nbtTagCompound.setString("bind_player", "");
    } else {
      nbtTagCompound.setString("bind_player", instance.getBindingPlayerUuid().toString());
    }
    return nbtTagCompound;
  }

  @Override
  public void readNBT(
      Capability<BindingEntityI> capability,
      BindingEntityI instance,
      EnumFacing side,
      NBTBase nbt) {
    NBTTagCompound nbtTagCompound = (NBTTagCompound) nbt;
    String uuid = nbtTagCompound.getString("bind_player");
    if (StringUtils.isNullOrEmpty(uuid)) {
      instance.bindPlayer(null);
    } else {
      instance.bindPlayer(UUID.fromString(uuid));
    }
  }
}
