package com.wxp.supernaturalworld.register;

import com.wxp.supernaturalworld.block.SupernaturalNormalBlockI;
import com.wxp.supernaturalworld.config.SupernaturalConfig;
import com.wxp.supernaturalworld.manager.BlockManager;
import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/** @author wxp */
@Mod.EventBusSubscriber(modid = SupernaturalConfig.MOD_ID)
public class BlockRegister {
  @SubscribeEvent
  public static void registerBlock(RegistryEvent.Register<Block> event) {
    for (SupernaturalNormalBlockI supernaturalNormalBlockI : BlockManager.getInitializedBlock()) {
      event.getRegistry().register(supernaturalNormalBlockI.getSelf());
    }
  }
}
