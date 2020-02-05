package com.wxp.supernaturalworld.network;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

/** @author wxp */
@NoArgsConstructor
@Getter
public class AbstractNbtMessage implements IMessage {
  private NBTTagCompound nbt = null;

  public AbstractNbtMessage(NBTTagCompound nbtTagCompound) {
    this.nbt = nbtTagCompound;
  }

  @Override
  public void fromBytes(ByteBuf buf) {
    nbt = ByteBufUtils.readTag(buf);
  }

  @Override
  public void toBytes(ByteBuf buf) {
    if (this.nbt == null) {
      return;
    }
    ByteBufUtils.writeTag(buf, nbt);
  }
}
