package com.countrygamer.arcanacraft.client.render;

import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderFocus implements IItemRenderer{

	public ItemRenderFocus() {
		
	}
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		if (type.equals(ItemRenderType.EQUIPPED)
				|| type.equals(ItemRenderType.EQUIPPED_FIRST_PERSON)
				|| type.equals(ItemRenderType.ENTITY)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		
	}
	
}
