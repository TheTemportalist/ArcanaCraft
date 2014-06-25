package com.countrygamer.arcanacraft.common.quom;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.countrygamer.arcanacraft.common.extended.ExtendedArcanePlayer;
import com.countrygamer.arcanacraft.common.quom.Tiers.Cast;
import com.countrygamer.arcanacraft.common.recipes.ExtractRecipes;
import com.countrygamer.countrygamercore.common.lib.ItemMeta;

public class QuomExtract extends Quom {
	
	public QuomExtract(String name, int col, int row) {
		super(name, null, col, row);
	}
	
	@Override
	public boolean onUse(EntityPlayer player, ExtendedArcanePlayer arcanePlayer, World world,
			int x, int y, int z, int side, Cast castTier) {
		Block block = world.getBlock(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		
		if (!arcanePlayer.player.canPlayerEdit(x, y, z, side, null)) {
			return false;
		}
		
		ItemStack result = ExtractRecipes.getOutput(new ItemMeta(block, meta));
		if (result != null && Block.getBlockFromItem(result.getItem()) != Blocks.air) {
			boolean flag = world.setBlockToAir(x, y, z);//block.removedByPlayer(world, arcanePlayer.player, x, y, z);
			
			if (flag) {
				world.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(block) + (meta << 12));
				block.onBlockDestroyedByPlayer(world, x, y, z, meta);
				
				Block newBlock = Block.getBlockFromItem(result.getItem());
				int newMeta = result.getItemDamage();
				world.setBlock(x, y, z, newBlock, newMeta, 3);
				return true;
				
			}
		}
		return false;
	}
	
	@Override
	public void onRightClick(EntityPlayer player, ExtendedArcanePlayer arcanePlayer, World world,
			Cast castTier) {
	}
	
}
