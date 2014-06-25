package com.countrygamer.arcanacraft.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import com.countrygamer.countrygamercore.common.inventory.ContainerBase;

public class ContainerSack extends ContainerBase {
	
	public ContainerSack(EntityPlayer player, IInventory inventory) {
		super(player, inventory);
	}
	
	@Override
	protected void registerSlots() {
		int i;
		int yoffset = -82;
		for (i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				int id = j + i * 9 + 9;
				if (id < this.getIInventory().getSizeInventory())
					this.addSlotToContainer(new SlotInventorySack(this.getIInventory(), id,
							8 + j * 18, 98 + i * 18 + yoffset));
			}
		}
		
		for (i = 0; i < 9; ++i) {
			if (i < this.getIInventory().getSizeInventory())
				this.addSlotToContainer(new SlotInventorySack(this.getIInventory(), i, 8 + i * 18,
						156 + yoffset));
		}
		
		this.registerPlayerSlots(0, 14, new int[] {
			this.player.inventory.currentItem
		});
	}
	
	@Override
	protected int getSlotIDForItemStack(ItemStack stackToProcess) {
		return 0;
	}
	
	
}
