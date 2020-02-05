package com.wxp.supernaturalworld.register;

import com.wxp.supernaturalworld.capability.BindingEntityI;
import com.wxp.supernaturalworld.capability.SupernaturalEntityI;
import com.wxp.supernaturalworld.manager.CapabilityManager;

/** @author wxp */
public class CapabilityRegister {
  public static void registerCapability() {
    net.minecraftforge.common.capabilities.CapabilityManager.INSTANCE.register(
        SupernaturalEntityI.class,
        CapabilityManager.supernaturalCapabilityStorage,
        CapabilityManager.supernaturalEntityCapFactory);
    net.minecraftforge.common.capabilities.CapabilityManager.INSTANCE.register(
        BindingEntityI.class,
        CapabilityManager.bindingEntityCapabilityStorage,
        CapabilityManager.bindingEntityCapFactory);
  }
}
