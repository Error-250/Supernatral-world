package com.wxp.supernaturalworld.eventhandler;

import com.wxp.supernaturalworld.SupernaturalMod;
import com.wxp.supernaturalworld.capability.SupernaturalEntityI;
import com.wxp.supernaturalworld.capability.provider.BindingEntityProvider;
import com.wxp.supernaturalworld.capability.provider.SupernaturalEntityProvider;
import com.wxp.supernaturalworld.config.SupernaturalConfig;
import com.wxp.supernaturalworld.domain.SupernaturalHelper;
import com.wxp.supernaturalworld.domain.SupernaturalLevel;
import com.wxp.supernaturalworld.entity.SupernaturalMonster;
import com.wxp.supernaturalworld.entity.SupernaturalSpiderEntity;
import com.wxp.supernaturalworld.item.SupernaturalRingItemI;
import com.wxp.supernaturalworld.item.SupernaturalRingItemImpl;
import com.wxp.supernaturalworld.manager.CapabilityManager;
import com.wxp.supernaturalworld.manager.ShopMenuManager;
import com.wxp.supernaturalworld.register.NetworkRegister;
import com.wxp.supernaturalworld.world.SupernaturalWorldProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
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
    if (event.getEntity().world.isRemote) {
      return;
    }
    if (event.getEntity() instanceof EntityMob
        && !(event.getEntity() instanceof SupernaturalMonster)) {
      // 怪物死亡
      int ticket = event.getEntity().world.rand.nextInt(100);
      if (ticket >= SupernaturalConfig.supernaturalRingConfig.minSupernaturalRingDropScale
          && ticket <= SupernaturalConfig.supernaturalRingConfig.maxSupernaturalRingDropScale) {
        ItemStack dropItem =
            SupernaturalHelper.randomSelectSupernaturalRing(
                SupernaturalLevel.TEN, ((EntityMob) event.getEntity()).getRNG());
        event.getEntity().entityDropItem(dropItem, 1);
      }
    }
    if (event.getEntity() instanceof SupernaturalMonster) {
      SupernaturalMonster supernaturalMonster = (SupernaturalMonster) event.getEntity();
      if (!supernaturalMonster.isRecentlyHurt() && !supernaturalMonster.shouldDead()) {
        event.setResult(Event.Result.DENY);
      }
    }
  }

  @SubscribeEvent
  public static void canEntitySpawn(LivingSpawnEvent.CheckSpawn event) {
    if (event.getWorld().provider.getDimension()
        == SupernaturalWorldProvider.supernaturalWorldType.getId()) {
      if (event.getEntity() instanceof SupernaturalMonster) {
        Entity entity = event.getEntity();
        Entity findEntity =
            entity.world.findNearestEntityWithinAABB(
                entity.getClass(),
                new AxisAlignedBB(
                    event.getX() - 20,
                    event.getY(),
                    event.getZ() - 20,
                    event.getX() + 20,
                    event.getY() + entity.height,
                    event.getZ() + 20),
                entity);
        if (findEntity == null) {
          event.setResult(Event.Result.ALLOW);
        } else {
          event.setResult(Event.Result.DENY);
        }
      } else {
        event.setResult(Event.Result.DENY);
      }
    } else if (event.getEntity() instanceof SupernaturalMonster) {
      event.setResult(Event.Result.DENY);
    }
  }

  @SubscribeEvent
  public static void onEntityDropItem(LivingDropsEvent event) {
    if (event.getEntity() instanceof SupernaturalMonster) {
      event.getDrops().clear();
      int ticket = ((SupernaturalMonster) event.getEntity()).getRNG().nextInt(100);
      if (ticket > SupernaturalConfig.supernaturalEntityConfig.monsterDropRingRate * 100) {
        event
            .getEntity()
            .world
            .createExplosion(
                event.getEntity(),
                event.getEntity().posX,
                event.getEntity().posY,
                event.getEntity().posZ,
                2.0f,
                false);
        return;
      }
      SupernaturalMonster monster = (SupernaturalMonster) event.getEntity();
      EntityItem entityItem =
          new EntityItem(
              monster.world,
              monster.posX,
              monster.posY + 1,
              monster.posZ,
              SupernaturalHelper.randomSelectSupernaturalRing(
                  SupernaturalHelper.calculateLevel(monster.getActiveYears()), monster.getRNG()));
      entityItem.setDefaultPickupDelay();
      event.getDrops().add(entityItem);
    }
  }
}
