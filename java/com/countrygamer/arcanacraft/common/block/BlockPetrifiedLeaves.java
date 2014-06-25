package com.countrygamer.arcanacraft.common.block;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

import com.countrygamer.countrygamercore.base.common.block.BlockBase;

public class BlockPetrifiedLeaves extends BlockBase {
	
	public BlockPetrifiedLeaves(Material mat, String modid, String name) {
		super(mat, modid, name);
	}
	
	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
		return Item.getItemFromBlock(Blocks.cobblestone);
	}
	
	@Override
	public int quantityDropped(Random random) {
		return random.nextInt(2);
	}
	
}
