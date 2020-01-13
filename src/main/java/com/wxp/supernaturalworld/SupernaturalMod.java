package com.wxp.supernaturalworld;

import com.wxp.supernaturalworld.config.SupernaturalConfig;
import com.wxp.supernaturalworld.manager.ItemManager;
import com.wxp.supernaturalworld.manager.KeyManager;
import com.wxp.supernaturalworld.proxy.ModProxy;
import com.wxp.supernaturalworld.register.CapabilityRegister;
import com.wxp.supernaturalworld.register.NetworkRegister;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

/** @author wxp */
@Mod(
    modid = SupernaturalConfig.MOD_ID,
    name = SupernaturalConfig.NAME,
    version = SupernaturalConfig.VERSION)
public class SupernaturalMod {
  public static Logger logger;

  @Mod.Instance(SupernaturalConfig.MOD_ID)
  public static SupernaturalMod INSTANCE;

  @SidedProxy(
      clientSide = "com.wxp.supernaturalworld.proxy.ClientProxy",
      serverSide = "com.wxp.supernaturalworld.proxy.ServerProxy",
      modId = SupernaturalConfig.MOD_ID)
  public static ModProxy modProxy;

  @EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    logger = event.getModLog();
    modProxy.preInit(event);
    ItemManager.initItem();
    KeyManager.initKey();

    CapabilityRegister.registerCapability();
    NetworkRegister.registerNetwork();
  }

  @EventHandler
  public void init(FMLInitializationEvent event) {
    modProxy.init(event);
  }
}
