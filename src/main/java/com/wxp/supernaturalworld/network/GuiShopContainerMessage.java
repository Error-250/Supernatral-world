package com.wxp.supernaturalworld.network;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minecraft.nbt.NBTTagCompound;

/** @author wxp */
@Getter
@NoArgsConstructor
public class GuiShopContainerMessage extends AbstractNbtMessage {
  private int index;

  public GuiShopContainerMessage(int index) {
    this.index = index;

    NBTTagCompound nbtTagCompound = new NBTTagCompound();
    nbtTagCompound.setInteger("shop_index", index);
    this.nbt = nbtTagCompound;
  }

  @Override
  public void fromBytes(ByteBuf buf) {
    super.fromBytes(buf);
    index = this.nbt.getInteger("shop_index");
  }
}
