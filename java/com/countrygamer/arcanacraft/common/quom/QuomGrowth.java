package com.countrygamer.arcanacraft.common.quom;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import com.countrygamer.arcanacraft.common.extended.ExtendedArcanePlayer;
import com.countrygamer.arcanacraft.common.quom.Tiers.Cast;
import com.countrygamer.countrygamercore.common.lib.util.UtilCursor;

public class QuomGrowth extends Quom {
	
	public QuomGrowth(String name, Quom parent, int col, int row) {
		super(name, parent, col, row);
	}
	
	@Override
	public boolean onUse(EntityPlayer player, ExtendedArcanePlayer arcanePlayer, World world,
			int x, int y, int z, int side, Cast castTier) {
		Block block = world.getBlock(x, y, z);
		if (!(block instanceof IGrowable)) {
			int[] newCoords = UtilCursor.getNewCoordsFromSide(x, y, z, side);
			block = world.getBlock(newCoords[0], newCoords[1], newCoords[2]);
		}
		
		if (block instanceof IGrowable) {
			if (castTier == Tiers.Cast.ADVANCED) {
				
				for (int i = 0; i < 10; i++) {
					world.scheduleBlockUpdate(x, y, z, block, 1);
				}
				
				return true;
			}
			else {
				world.scheduleBlockUpdate(x, y, z, block, 10);
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
