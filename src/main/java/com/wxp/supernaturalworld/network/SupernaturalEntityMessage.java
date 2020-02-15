package com.wxp.supernaturalworld.network;

import com.wxp.supernaturalworld.capability.SupernaturalEntityI;
import com.wxp.supernaturalworld.capability.impl.SupernaturalEntityImpl;
import com.wxp.supernaturalworld.manager.CapabilityManager;
import io.netty.buffer.ByteBuf;
import lombok.NoArgsConstructor;
import net.minecraft.nbt.NBTTagCompound;

/** @author wxp */
@NoArgsConstructor
public class SupernaturalEntityMessage extends AbstractNbtMessage {
  private SupernaturalEntityI supernaturalEntityI;

  public SupernaturalEntityMessage(NBTTagCompound nbtTagCompound) {
    super(nbtTagCompound);
  }

  public SupernaturalEntityMessage(SupernaturalEntityI supernaturalEntityI) {
    this.supernaturalEntityI = supernaturalEntityI;
    this.nbt =
        (NBTTagCompound)
            CapabilityManager.supernaturalCapabilityStorage.writeNBT(
                CapabilityManager.supernaturalEntityICapability, supernaturalEntityI, null);
  }

  @Override
  public void fromBytes(ByteBuf buf) {
    super.fromBytes(buf);
    if (supernaturalEntityI == null) {
      supernaturalEntityI = new SupernaturalEntityImpl();
    }
    CapabilityManager.supernaturalCapabilityStorage.readNBT(
        CapabilityManager.supernaturalEntityICapability, supernaturalEntityI, null, nbt);
  }
}
