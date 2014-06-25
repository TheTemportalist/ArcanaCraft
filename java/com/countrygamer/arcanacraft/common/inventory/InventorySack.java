package com.countrygamer.arcanacraft.common.inventory;

import net.minecraft.item.ItemStack;

import com.countrygamer.countrygamercore.common.inventory.InventoryBase;

public class InventorySack extends InventoryBase {
	
	public InventorySack(ItemStack itemstack) {
		super("Player Sack", 36, itemstack);
	}
	
}
