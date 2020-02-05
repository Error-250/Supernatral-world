package com.wxp.supernaturalworld.manager;

import com.wxp.supernaturalworld.capability.BindingEntityI;
import com.wxp.supernaturalworld.capability.SupernaturalEntityI;
import com.wxp.supernaturalworld.capability.factory.BindingEntityCapFactory;
import com.wxp.supernaturalworld.capability.factory.SupernaturalEntityCapFactory;
import com.wxp.supernaturalworld.capability.storage.BindingEntityCapabilityStorage;
import com.wxp.supernaturalworld.capability.storage.SupernaturalCapabilityStorage;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

/** @author wxp */
public class CapabilityManager {
  public static SupernaturalCapabilityStorage supernaturalCapabilityStorage =
      new SupernaturalCapabilityStorage();
  public static SupernaturalEntityCapFactory supernaturalEntityCapFactory =
      new SupernaturalEntityCapFactory();
  public static BindingEntityCapabilityStorage bindingEntityCapabilityStorage =
      new BindingEntityCapabilityStorage();
  public static BindingEntityCapFactory bindingEntityCapFactory = new BindingEntityCapFactory();

  @CapabilityInject(SupernaturalEntityI.class)
  public static Capability<SupernaturalEntityI> supernaturalEntityICapability;

  @CapabilityInject(BindingEntityI.class)
  public static Capability<BindingEntityI> bindingEntityICapability;
}
