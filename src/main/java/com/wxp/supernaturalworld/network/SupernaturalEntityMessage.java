package com.wxp.supernaturalworld.network;

import lombok.NoArgsConstructor;
import net.minecraft.nbt.NBTTagCompound;

/** @author wxp */
@NoArgsConstructor
public class SupernaturalEntityMessage extends AbstractNbtMessage {
  public SupernaturalEntityMessage(NBTTagCompound nbtTagCompound) {
    super(nbtTagCompound);
  }
}
