package com.countrygamer.arcanacraft.common.quom;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import com.countrygamer.arcanacraft.common.extended.ExtendedArcanePlayer;
import com.countrygamer.arcanacraft.common.quom.Tiers.Cast;
import com.countrygamer.countrygamercore.lib.UtilDrops;

public class QuomDig extends Quom {
	
	public QuomDig(String name) {
		super(name);
	}
	
	@Override
	public boolean onUse(EntityPlayer player, ExtendedArcanePlayer arcanePlayer, World world,
			int x, int y, int z, int side, Cast castTier) {
		if (!arcanePlayer.player.canPlayerEdit(x, y, z, side, null)) {
			return false;
		}
		else {
			Block block = world.getBlock(x, y, z);
			int meta = world.getBlockMetadata(x, y, z);
			if (block.getMaterial() != Material.air) {
				boolean flag = block.removedByPlayer(world, arcanePlayer.player, x, y, z);
				
				if (flag) {
					world.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(block) + (meta << 12));
					block.onBlockDestroyedByPlayer(world, x, y, z, meta);
					if (!arcanePlayer.player.capabilities.isCreativeMode) {
						/*
						for (final ItemStack stack : block.getDrops(world, x, y, z, meta, 0)) {
							CoreUtil.dropItemStack(world, stack.copy(), x, y, z);
							UtilDrops.spawnItemStack(world, x, y, z, itemStack, random);
						}
						*/
						UtilDrops.spawnDrops(world, x + 0.5, y, z + 0.5,
								block.getDrops(world, x, y, z, meta, 0));
					}
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public void onRightClick(EntityPlayer player, ExtendedArcanePlayer arcanePlayer, World world,
			Cast castTier) {
	}
	
}
