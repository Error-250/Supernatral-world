package com.wxp.supernaturalworld.network;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minecraft.nbt.NBTTagCompound;

/** @author wxp */
@NoArgsConstructor
@Getter
public class ShopSellMessage extends AbstractNbtMessage {
  private int index;
  private int operateType;

  public ShopSellMessage(int index, int operateType) {
    this.index = index;
    this.operateType = operateType;

    NBTTagCompound nbtTagCompound = new NBTTagCompound();
    nbtTagCompound.setInteger("supernatural_shop_sell_index", index);
    nbtTagCompound.setInteger("supernatural_shop_sell_operate", operateType);
    this.nbt = nbtTagCompound;
  }

  @Override
  public void fromBytes(ByteBuf buf) {
    super.fromBytes(buf);
    this.index = this.nbt.getInteger("supernatural_shop_sell_index");
    this.operateType = this.nbt.getInteger("supernatural_shop_sell_operate");
  }
}
