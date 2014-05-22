package com.countrygamer.arcanacraft.common.quom;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.countrygamer.arcanacraft.common.extended.ExtendedArcanePlayer;
import com.countrygamer.countrygamercore.lib.UtilCursor;

public class QuomFire extends Quom {
	
	public QuomFire(String name) {
		super(name);
	}
	
	@Override
	public void onUse(ExtendedArcanePlayer arcanePlayer, World world, int x, int y,
			int z, int side, Tiers.Cast castTier, Tiers.MANUS manusTier) {
		EntityPlayer player = arcanePlayer.player;
		
		int[] newCoords = UtilCursor.getNewCoordsFromSide(x, y, z, side);
		x = newCoords[0];
		y = newCoords[1];
		z = newCoords[2];
		
		if (!player.canPlayerEdit(x, y, z, side, new ItemStack(
				Items.flint_and_steel))) {
			return;
		}
		else {
			if (world.isAirBlock(x, y, z)) {
				world.playSoundEffect((double) x + 0.5D, (double) y + 0.5D,
						(double) z + 0.5D, "fire.ignite", 1.0F,
						(new Random()).nextFloat() * 0.4F + 0.8F);
				world.setBlock(x, y, z, Blocks.fire);
			}
		}
	}
	
}
