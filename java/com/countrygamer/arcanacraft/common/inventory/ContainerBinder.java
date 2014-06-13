package com.countrygamer.arcanacraft.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.countrygamer.arcanacraft.common.recipes.EnumBinderType;
import com.countrygamer.arcanacraft.common.tile.TileEntityBinder;
import com.countrygamer.countrygamercore.Base.common.inventory.ContainerBase;
import com.countrygamer.countrygamercore.Base.common.inventory.SlotOutput;

public class ContainerBinder extends ContainerBase {
	
	public ContainerBinder(EntityPlayer player, TileEntityBinder tileEnt) {
		super(player, tileEnt);
	}
	
	protected void registerSlots() {
		
		this.addSlotToContainer(new Slot(this.getIInventory(), 0, 51, 19));
		this.addSlotToContainer(new SlotOutput(this.getIInventory(), 2, 72, 19));
		
		if (((TileEntityBinder) this.getTileEntity()).binderType == EnumBinderType.ITEM) {
			this.addSlotToContainer(new Slot(this.getIInventory(), 1, 51, 63));
		}
		
		this.registerPlayerSlots(0, 0);
	}
	
	@Override
	protected int getSlotIDForItemStack(ItemStack stackToProcess) {
		return 0;
	}
	
	@Override
	protected int getExcludedMaximumSlotIDForItemStack(ItemStack stackToProcess) {
		return 2;
	}
	
}
