package com.wxp.supernaturalworld.register;

import com.wxp.supernaturalworld.manager.KeyManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

/** @author wxp */
public class KeyRegister {
  public static void registerKey() {
    for (KeyBinding keyBinding : KeyManager.getInitializedKey()) {
      ClientRegistry.registerKeyBinding(keyBinding);
    }
  }
}
