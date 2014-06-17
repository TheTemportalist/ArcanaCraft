package com.countrygamer.arcanacraft.common.quom;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

import com.countrygamer.arcanacraft.common.extended.ExtendedArcanePlayer;
import com.countrygamer.arcanacraft.common.quom.Tiers.Cast;
import com.countrygamer.countrygamercore.lib.UtilCursor;

public class QuomFire extends Quom {
	
	public QuomFire(String name, Quom parent, int col, int row) {
		super(name, parent, col, row);
	}
	
	@Override
	public boolean canDiscover_Use(ExtendedArcanePlayer arcanePlayer,
			PlayerInteractEvent.Action action, ItemStack itemStack) {
		return super.canDiscover_Use(arcanePlayer, action, itemStack)
				&& (action == Action.RIGHT_CLICK_BLOCK && itemStack.getItem() == Items.flint_and_steel);
	}
	
	@Override
	public double getReachLength(Tiers.Cast castTier, boolean isCreative) {
		return isCreative ? 1000.0 : Tiers.Cast.tierToInt(castTier) * 5.0D;
	}
	
	@Override
	public boolean onUse(EntityPlayer player, ExtendedArcanePlayer arcanePlayer, World world,
			int x, int y, int z, int side, Tiers.Cast castTier) {
		int[] newCoords = UtilCursor.getNewCoordsFromSide(x, y, z, side);
		x = newCoords[0];
		y = newCoords[1];
		z = newCoords[2];
		
		if (!player.canPlayerEdit(x, y, z, side, new ItemStack(Items.flint_and_steel))) {
			return false;
		}
		else {
			if (world.isAirBlock(x, y, z)) {
				world.playSoundEffect((double) x + 0.5D, (double) y + 0.5D, (double) z + 0.5D,
						"fire.ignite", 1.0F, (new Random()).nextFloat() * 0.4F + 0.8F);
				world.setBlock(x, y, z, Blocks.fire);
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
