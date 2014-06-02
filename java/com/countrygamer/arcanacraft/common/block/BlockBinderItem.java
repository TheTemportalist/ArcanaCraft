package com.countrygamer.arcanacraft.common.block;

import net.minecraft.block.material.Material;

import com.countrygamer.arcanacraft.common.tile.EnumBinderType;

public class BlockBinderItem extends BlockBinderBase {
	
	public BlockBinderItem(Material mat, String modid, String name) {
		super(mat, modid, name);
	}
	
	@Override
	protected EnumBinderType getBinderType() {
		return EnumBinderType.ITEM;
	}
	
}
