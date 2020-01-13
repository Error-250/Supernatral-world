package com.wxp.supernaturalworld.capability.provider;

import com.wxp.supernaturalworld.capability.impl.SupernaturalEntityImpl;
import com.wxp.supernaturalworld.capability.storage.SupernaturalCapabilityStorage;
import com.wxp.supernaturalworld.manager.CapabilityManager;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

/** @author wxp */
public class SupernaturalEntityProvider implements ICapabilitySerializable {
  private SupernaturalEntityImpl supernaturalEntity = new SupernaturalEntityImpl();
  private SupernaturalCapabilityStorage storage =
      (SupernaturalCapabilityStorage) CapabilityManager.supernaturalEntityICapability.getStorage();

  @Override
  public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
    return CapabilityManager.supernaturalEntityICapability.equals(capability);
  }

  @Nullable
  @Override
  public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
    if (CapabilityManager.supernaturalEntityICapability.equals(capability)) {
      return (T) supernaturalEntity;
    }
    return null;
  }

  @Override
  public NBTBase serializeNBT() {
    NBTTagCompound nbtTagCompound = new NBTTagCompound();
    nbtTagCompound.setTag(
        "supernatural",
        Objects.requireNonNull(
            storage.writeNBT(
                CapabilityManager.supernaturalEntityICapability, supernaturalEntity, null)));
    return nbtTagCompound;
  }

  @Override
  public void deserializeNBT(NBTBase nbt) {
    NBTTagCompound nbtTagCompound = (NBTTagCompound) nbt;
    storage.readNBT(
        CapabilityManager.supernaturalEntityICapability,
        supernaturalEntity,
        null,
        nbtTagCompound.getTag("supernatural"));
  }
}
