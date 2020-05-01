package com.wxp.supernaturalworld.world;

import net.minecraft.entity.Entity;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

/**
 * @author wxp
 */
public class SupernaturalWorldTeleporter extends Teleporter {
  public SupernaturalWorldTeleporter(WorldServer worldIn) {
    super(worldIn);
  }

  @Override
  public boolean placeInExistingPortal(Entity entityIn, float rotationYaw) {
    return false;
  }

  @Override
  public void placeInPortal(Entity entityIn, float rotationYaw) {
  }

  @Override
  public boolean makePortal(Entity entityIn) {
    return false;
  }

  @Override
  public void removeStalePortalLocations(long worldTime) {
  }
}
