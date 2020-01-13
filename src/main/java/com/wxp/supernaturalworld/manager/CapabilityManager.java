package com.wxp.supernaturalworld.manager;

import com.wxp.supernaturalworld.capability.SupernaturalEntityI;
import com.wxp.supernaturalworld.capability.factory.SupernaturalEntityCapFactory;
import com.wxp.supernaturalworld.capability.storage.SupernaturalCapabilityStorage;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

/** @author wxp */
public class CapabilityManager {
  public static SupernaturalCapabilityStorage supernaturalCapabilityStorage =
      new SupernaturalCapabilityStorage();
  public static SupernaturalEntityCapFactory supernaturalEntityCapFactory =
      new SupernaturalEntityCapFactory();

  @CapabilityInject(SupernaturalEntityI.class)
  public static Capability<SupernaturalEntityI> supernaturalEntityICapability;
}
