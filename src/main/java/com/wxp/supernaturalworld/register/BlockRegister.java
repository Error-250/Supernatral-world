package com.wxp.supernaturalworld.register;

import com.wxp.supernaturalworld.block.SupernaturalNormalBlockI;
import com.wxp.supernaturalworld.block.tileentity.TileEntityAlchemyFurnace;
import com.wxp.supernaturalworld.config.SupernaturalConfig;
import com.wxp.supernaturalworld.manager.BlockManager;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

/** @author wxp */
@Mod.EventBusSubscriber(modid = SupernaturalConfig.MOD_ID)
public class BlockRegister {
  @SubscribeEvent
  public static void registerBlock(RegistryEvent.Register<Block> event) {
    for (SupernaturalNormalBlockI supernaturalNormalBlockI : BlockManager.getInitializedBlock()) {
      event.getRegistry().register(supernaturalNormalBlockI.getSelf());
      if (supernaturalNormalBlockI.getSelf().hasTileEntity(null)) {
        GameRegistry.registerTileEntity(
            TileEntityAlchemyFurnace.class,
            new ResourceLocation(
                SupernaturalConfig.MOD_ID, TileEntityAlchemyFurnace.class.getName()));
      }
    }
  }
}
