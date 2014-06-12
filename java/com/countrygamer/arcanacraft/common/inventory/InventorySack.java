package com.countrygamer.arcanacraft.common.inventory;

import net.minecraft.item.ItemStack;

import com.countrygamer.arcanacraft.common.item.ItemInventorySack;
import com.countrygamer.countrygamercore.Base.common.inventory.InventoryItemBase;

public class InventorySack extends InventoryItemBase {
	
	public InventorySack(ItemStack itemstack) {
		super("Player Sack", 36, itemstack, ItemInventorySack.class);
	}
	
}
