package com.countrygamer.arcanacraft.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

import com.countrygamer.countrygamercore.Base.common.inventory.ContainerBase;

public class ContainerActuator extends ContainerBase {

	public ContainerActuator(EntityPlayer player, IInventory inventory) {
		super(player, inventory);
	}
	
	@Override
	protected void registerSlots() {
		
		this.registerSlot(0, 26, 26);
		
		this.registerPlayerSlots(0, 0);
	}
	
}
