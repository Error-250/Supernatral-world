package com.wxp.supernaturalworld.eventhandler;

import com.wxp.supernaturalworld.SupernaturalMod;
import com.wxp.supernaturalworld.capability.SupernaturalEntityI;
import com.wxp.supernaturalworld.capability.provider.BindingEntityProvider;
import com.wxp.supernaturalworld.capability.provider.SupernaturalEntityProvider;
import com.wxp.supernaturalworld.config.SupernaturalConfig;
import com.wxp.supernaturalworld.item.SupernaturalRingItemI;
import com.wxp.supernaturalworld.item.SupernaturalRingItemImpl;
import com.wxp.supernaturalworld.manager.CapabilityManager;
import com.wxp.supernaturalworld.manager.ShopMenuManager;
import com.wxp.supernaturalworld.register.NetworkRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;
import java.util.stream.Collectors;

/** @author wxp */
@Mod.EventBusSubscriber(modid = SupernaturalConfig.MOD_ID)
public class EntityEventHandler {
  @SubscribeEvent
  public static void onAttachCapabilitiesEntity(AttachCapabilitiesEvent<Entity> event) {
    Entity entity = event.getObject();
    if (entity instanceof EntityPlayer) {
      event.addCapability(
          new ResourceLocation(SupernaturalConfig.MOD_ID, "supernatural"),
          new SupernaturalEntityProvider());
    }
  }

  @SubscribeEvent
  public static void onAttachCapabilitiesItem(AttachCapabilitiesEvent<ItemStack> event) {
    ItemStack item = event.getObject();
    if (item.getItem() instanceof SupernaturalRingItemImpl) {
      event.addCapability(
          new ResourceLocation(SupernaturalConfig.MOD_ID, "binding_item"),
          new BindingEntityProvider());
    }
  }

  @SubscribeEvent
  public static void onEntityJoinWorld(EntityJoinWorldEvent event) {
    if (!event.getWorld().isRemote && event.getEntity() instanceof EntityPlayer) {
      EntityPlayerMP player = (EntityPlayerMP) event.getEntity();
      if (player.hasCapability(CapabilityManager.supernaturalEntityICapability, null)) {
        SupernaturalEntityI supernaturalEntityI =
            player.getCapability(CapabilityManager.supernaturalEntityICapability, null);
        if (supernaturalEntityI == null) {
          return;
        }
        NetworkRegister.syncSupernaturalEntityMessage(supernaturalEntityI, player);
      }
    }
  }

  @SubscribeEvent
  public static void onLivingDeath(LivingDeathEvent event) {
    if (event.getEntity() instanceof EntityMob && !event.getEntity().world.isRemote) {
      // 怪物死亡
      int ticket = event.getEntity().world.rand.nextInt(100);
      SupernaturalMod.logger.info("ticket:{}", ticket);
      if (ticket >= 44 && ticket <= 55) {
        SupernaturalRingItemI.RingLevel wantedLevel;
        if (event.getEntity().ticksExisted >= 100000) {
          wantedLevel = SupernaturalRingItemI.RingLevel.HUNDRED_THOUSAND;
        } else if (event.getEntity().ticksExisted >= 10000) {
          wantedLevel = SupernaturalRingItemI.RingLevel.TEN_THOUSAND;
        } else if (event.getEntity().ticksExisted >= 1000) {
          wantedLevel = SupernaturalRingItemI.RingLevel.THOUSAND;
        } else if (event.getEntity().ticksExisted >= 100) {
          wantedLevel = SupernaturalRingItemI.RingLevel.HUNDRED;
        } else {
          wantedLevel = SupernaturalRingItemI.RingLevel.TEN;
        }
        List<ItemStack> allowDropItems =
            ShopMenuManager.getInitializedShopMenu().stream()
                .filter(
                    shopMenu -> {
                      if (shopMenu.getSellItem() != null
                          && shopMenu.getSellItem().getItem() instanceof SupernaturalRingItemI) {
                        return wantedLevel.equals(
                            SupernaturalRingItemImpl.getRingLevel(shopMenu.getSellItem()));
                      }
                      return false;
                    })
                .map(ShopMenuManager.ShopMenu::getSellItem)
                .collect(Collectors.toList());
        if (allowDropItems.size() > 0) {
          int index = event.getEntity().world.rand.nextInt(allowDropItems.size());
          ItemStack dropItem = allowDropItems.get(index);
          event.getEntity().entityDropItem(dropItem, 1);
        }
      }
    }
  }
}
