package com.wxp.supernaturalworld.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

/** @author wxp */
public interface SupernaturalNormalBlockI {
  /**
   * 获取用于注册的item block
   *
   * @return item block
   */
  ItemBlock getItemBlock();

  /**
   * 获取自身, 用于注册
   *
   * @return 获取自身用于注册
   */
  Block getSelf();
}
