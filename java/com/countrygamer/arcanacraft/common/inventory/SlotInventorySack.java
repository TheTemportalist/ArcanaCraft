package com.countrygamer.arcanacraft.common.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotInventorySack extends Slot {
	
	private final boolean canExtract;
	
	public SlotInventorySack(IInventory inv, int id, int x, int y) {
		this(inv, id, x, y, true);
	}
	
	public SlotInventorySack(IInventory inv, int id, int x, int y, boolean canTake) {
		super(inv, id, x, y);
		this.canExtract = canTake;
	}
	
	@Override
	public boolean isItemValid(ItemStack itemStack) {
		return this.canExtract;
	}
	
}
