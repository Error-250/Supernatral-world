package com.wxp.supernaturalworld.gui.container;

import com.wxp.supernaturalworld.block.tileentity.TileEntityAlchemyFurnace;
import com.wxp.supernaturalworld.gui.slot.SupernaturalOutSlot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

/** @author wxp */
public class GuiAlchemyFurnaceContainer extends AbstractGuiAllPlayerInventoryContainer {
  private TileEntityAlchemyFurnace tileEntityAlchemyFurnace;
  private EntityPlayer entityPlayer;

  public GuiAlchemyFurnaceContainer(
      EntityPlayer entityPlayer, TileEntityAlchemyFurnace tileEntityAlchemyFurnace) {
    super(entityPlayer);
    this.entityPlayer = entityPlayer;
    this.tileEntityAlchemyFurnace = tileEntityAlchemyFurnace;

    IItemHandler upHandler =
        tileEntityAlchemyFurnace.getCapability(
            CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
    this.addSlotToContainer(new SlotItemHandler(upHandler, 0, 34, 15));
    this.addSlotToContainer(new SlotItemHandler(upHandler, 1, 66, 15));
    this.addSlotToContainer(new SlotItemHandler(upHandler, 2, 99, 15));

    IItemHandler downHandler =
        tileEntityAlchemyFurnace.getCapability(
            CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);
    this.addSlotToContainer(new SlotItemHandler(downHandler, 0, 66, 51));

    IItemHandler outHandler =
        tileEntityAlchemyFurnace.getCapability(
            CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.EAST);
    this.addSlotToContainer(new SupernaturalOutSlot(outHandler, 0, 125, 33));
  }

  @Override
  int getGuiSlotSize() {
    return 5;
  }

  @Override
  public boolean canInteractWith(EntityPlayer playerIn) {
    return true;
  }

  @Override
  public void detectAndSendChanges() {
    super.detectAndSendChanges();
    int data = tileEntityAlchemyFurnace.getBurnTime();
    for (IContainerListener listener : this.listeners) {
      listener.sendWindowProperty(this, 0, data);
    }
  }

  @Override
  public void updateProgressBar(int id, int data) {
    super.updateProgressBar(id, data);

    if (id == 0) {
      tileEntityAlchemyFurnace.setBurnTime(data);
    }
  }

  public double burnProgress() {
    return tileEntityAlchemyFurnace.getBurnTime() / 100.0;
  }
}
