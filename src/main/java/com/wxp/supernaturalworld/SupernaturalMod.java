package com.wxp.supernaturalworld;

import com.wxp.supernaturalworld.config.SupernaturalConfig;
import com.wxp.supernaturalworld.manager.*;
import com.wxp.supernaturalworld.proxy.ModProxy;
import com.wxp.supernaturalworld.register.CapabilityRegister;
import com.wxp.supernaturalworld.register.NetworkRegister;
import com.wxp.supernaturalworld.world.SupernaturalWorldProvider;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.oredict.OreDictionary;
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
    BlockManager.initBlock();
    ItemManager.initItem();
    KeyManager.initKey();

    EntityManager.initEntity();
    CapabilityRegister.registerCapability();
    NetworkRegister.registerNetwork();
    PotionManager.initPotion();

    ShopMenuManager.initShopMenu();

    DimensionManager.registerDimension(
        SupernaturalWorldProvider.supernaturalWorldType.getId(),
        SupernaturalWorldProvider.supernaturalWorldType);
  }

  @EventHandler
  public void init(FMLInitializationEvent event) {
    modProxy.init(event);

    OreDictionaryManager.initOreDictionary();
  }
}
