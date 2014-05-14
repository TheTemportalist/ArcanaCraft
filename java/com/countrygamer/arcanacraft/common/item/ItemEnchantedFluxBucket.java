package com.countrygamer.arcanacraft.common.item;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;

public class ItemEnchantedFluxBucket extends ItemBucket {

	public ItemEnchantedFluxBucket(String modid, Block fluidBlock) {
		super(fluidBlock);
		this.setUnlocalizedName("Enchanted Flux Bucket");
		this.setContainerItem(Items.bucket);
		this.setTextureName(modid + ":" + this.getUnlocalizedName().substring(5));
	}
	
}
