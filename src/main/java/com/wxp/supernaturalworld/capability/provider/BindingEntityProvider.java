package com.wxp.supernaturalworld.capability.provider;

import com.wxp.supernaturalworld.capability.BindingEntityI;
import com.wxp.supernaturalworld.capability.impl.BindingEntityImpl;
import com.wxp.supernaturalworld.capability.storage.BindingEntityCapabilityStorage;
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
public class BindingEntityProvider implements ICapabilitySerializable {
  private BindingEntityI bindingEntityI = new BindingEntityImpl();
  private BindingEntityCapabilityStorage storage = CapabilityManager.bindingEntityCapabilityStorage;

  @Override
  public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
    return CapabilityManager.bindingEntityICapability.equals(capability);
  }

  @Nullable
  @Override
  public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
    if (CapabilityManager.bindingEntityICapability.equals(capability)) {
      return (T) bindingEntityI;
    }
    return null;
  }

  @Override
  public NBTBase serializeNBT() {
    NBTTagCompound nbtTagCompound = new NBTTagCompound();
    nbtTagCompound.setTag(
        "binding_entity",
        Objects.requireNonNull(
            storage.writeNBT(CapabilityManager.bindingEntityICapability, bindingEntityI, null)));
    return nbtTagCompound;
  }

  @Override
  public void deserializeNBT(NBTBase nbt) {
    NBTTagCompound nbtTagCompound = (NBTTagCompound) nbt;
    storage.readNBT(
        CapabilityManager.bindingEntityICapability,
        bindingEntityI,
        null,
        nbtTagCompound.getTag("binding_entity"));
  }
}
