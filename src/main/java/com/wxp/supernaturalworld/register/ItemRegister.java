package com.wxp.supernaturalworld.register;

import com.wxp.supernaturalworld.config.SupernaturalConfig;
import com.wxp.supernaturalworld.creativetab.SupernaturalRingCreativeTab;
import com.wxp.supernaturalworld.item.SupernaturalRingItemI;
import com.wxp.supernaturalworld.item.SupernaturalRingItemImpl;
import com.wxp.supernaturalworld.manager.ItemManager;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Objects;

/** @author wxp */
@Mod.EventBusSubscriber(modid = SupernaturalConfig.MOD_ID)
public class ItemRegister {
  @SubscribeEvent
  public static void registerItem(RegistryEvent.Register<Item> event) {
    for (Item item : ItemManager.getInitializedItem()) {
      event.getRegistry().register(item);
    }
    registerItemModel();
  }

  private static void registerItemModel() {
    if (Side.CLIENT.equals(FMLCommonHandler.instance().getSide())) {
      for (Item item : ItemManager.getInitializedItem()) {
        if (item instanceof SupernaturalRingItemImpl) {
          NonNullList<ItemStack> subItems = NonNullList.create();
          SupernaturalRingItemImpl supernaturalRingItem = (SupernaturalRingItemImpl) item;
          supernaturalRingItem.getSubItems(SupernaturalRingCreativeTab.INSTANCE, subItems);
          for (ItemStack subItem : subItems) {
            SupernaturalRingItemI.RingLevel ringLevel =
                SupernaturalRingItemImpl.getRingLevel(subItem);
            ModelLoader.setCustomModelResourceLocation(
                subItem.getItem(),
                subItem.getMetadata(),
                new ModelResourceLocation(
                    Objects.requireNonNull(item.getRegistryName())
                        + "_"
                        + ringLevel.name().toLowerCase(),
                    "inventory"));
          }
        } else {
          ModelLoader.setCustomModelResourceLocation(
              item,
              0,
              new ModelResourceLocation(
                  Objects.requireNonNull(item.getRegistryName()), "inventory"));
        }
      }
    }
  }
}
