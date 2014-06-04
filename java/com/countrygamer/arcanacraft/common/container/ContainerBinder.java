package com.countrygamer.arcanacraft.common.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;

import com.countrygamer.arcanacraft.common.recipes.EnumBinderType;
import com.countrygamer.arcanacraft.common.tile.TileEntityBinder;
import com.countrygamer.countrygamercore.Base.common.inventory.ContainerBase;

public class ContainerBinder extends ContainerBase {
	
	public ContainerBinder(EntityPlayer player, TileEntityBinder tileEnt) {
		super(player, tileEnt);
	}
	
	protected void registerSlots() {
		
		this.addSlotToContainer(new Slot(this.getIInventory(), 0, 51, 19));
		this.addSlotToContainer(new Slot(this.getIInventory(), 2, 51, 63));
		
		if (((TileEntityBinder) this.getTileEntity()).binderType == EnumBinderType.ITEM) {
			this.addSlotToContainer(new Slot(this.getIInventory(), 1, 72, 19));
		}
		
		this.registerPlayerSlots(0, 0);
	}
	
}
