package com.countrygamer.arcanacraft.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import com.countrygamer.countrygamercore.common.inventory.ContainerBase;
import com.countrygamer.countrygamercore.common.inventory.GhostCamouflageSlot;

public class ContainerActuator extends ContainerBase {
	
	public ContainerActuator(EntityPlayer player, IInventory inventory) {
		super(player, inventory);
	}
	
	@Override
	protected void registerSlots() {
		
		this.registerSlot(0, 62, 31);
		this.addSlotToContainer(new GhostCamouflageSlot(this.getIInventory(), 1, 101, 40, 1));
		
		this.registerPlayerSlots(0, 0);
	}
	
	@Override
	protected int getSlotIDForItemStack(ItemStack stackToProcess) {
		return 0;
	}
	
	
}
