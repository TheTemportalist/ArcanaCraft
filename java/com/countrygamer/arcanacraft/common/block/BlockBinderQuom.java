package com.countrygamer.arcanacraft.common.block;

import net.minecraft.block.material.Material;

import com.countrygamer.arcanacraft.common.recipes.EnumBinderType;

public class BlockBinderQuom extends BlockBinderBase {
	
	public BlockBinderQuom(Material mat, String modid, String name) {
		super(mat, modid, name);
	}
	
	@Override
	protected EnumBinderType getBinderType() {
		return EnumBinderType.QUOM;
	}
	
}
