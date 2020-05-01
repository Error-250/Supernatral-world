package com.wxp.supernaturalworld.register;

import com.wxp.supernaturalworld.config.SupernaturalConfig;
import com.wxp.supernaturalworld.manager.EntityManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;

/** @author wxp */
@Mod.EventBusSubscriber(modid = SupernaturalConfig.MOD_ID)
public class EntityRegister {
  @SubscribeEvent
  public static void registerEntity(RegistryEvent.Register<EntityEntry> event) {
    for (EntityEntry entityEntry : EntityManager.getInitializedEntity()) {
      event.getRegistry().register(entityEntry);
    }
  }
}
