package com.wxp.supernaturalworld.proxy;

import com.wxp.supernaturalworld.register.KeyRegister;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/** @author wxp */
public class ClientProxy implements ModProxy {
  @Override
  public void preInit(FMLPreInitializationEvent event) {}

  @Override
  public void init(FMLInitializationEvent event) {
    KeyRegister.registerKey();
  }
}
