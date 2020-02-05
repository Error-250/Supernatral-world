package com.wxp.supernaturalworld.gui.container;

import com.wxp.supernaturalworld.capability.SupernaturalEntityI;
import com.wxp.supernaturalworld.gui.slot.SupernaturalRingSlot;
import com.wxp.supernaturalworld.manager.CapabilityManager;
import lombok.Getter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.items.IItemHandler;

/** @author wxp */
@Getter
public class GuiSupernaturalPlayerContainer extends AbstractGuiAllPlayerInventoryContainer {
  private EntityPlayer entityPlayer;
  private IItemHandler handler;

  public GuiSupernaturalPlayerContainer(EntityPlayer entityPlayer) {
    super(entityPlayer);
    this.entityPlayer = entityPlayer;
    SupernaturalEntityI supernaturalEntityI =
        entityPlayer.getCapability(CapabilityManager.supernaturalEntityICapability, null);
    if (supernaturalEntityI != null) {
      handler = supernaturalEntityI.getSupernaturalRingHandler();

      for (int i = 0; i < 9; i++) {
        this.addSlotToContainer(new SupernaturalRingSlot(entityPlayer, supernaturalEntityI, i));
      }
    }
  }

  @Override
  int getGuiSlotSize() {
    return 9;
  }

  @Override
  public boolean canInteractWith(EntityPlayer playerIn) {
    return Boolean.TRUE;
  }
}
