package com.wxp.supernaturalworld.register;

import com.wxp.supernaturalworld.config.SupernaturalConfig;
import com.wxp.supernaturalworld.manager.PotionManager;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/** @author wxp */
@Mod.EventBusSubscriber(modid = SupernaturalConfig.MOD_ID)
public class PotionRegister {
  @SubscribeEvent
  public static void registerPotion(RegistryEvent.Register<Potion> event) {
    for (Potion potion : PotionManager.getInitializedPotion()) {
      event.getRegistry().register(potion);
    }
  }
}
