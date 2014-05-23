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
	public double getReachLength(Tiers.Cast castTier) {
		return Tiers.Cast.tierToInt(castTier) * 5.0D;
	}
	
	/*
	@Override
	public boolean onEntityUse(EntityPlayer player, ExtendedArcanePlayer arcanePlayer, World world,
			EntityLivingBase entity, Tiers.Cast castTier) {
		if (!player.isSneaking()) {
			MovingObjectPosition mop = UtilCursor.getMOPFromPlayer(world, player,
					this.getReachLength(castTier));
			if (mop == null) ArcanaCraft.logger.info("Null MOP");
			ArcanaCraft.logger.info(mop.typeOfHit.name());
			if (mop.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
				mop.entityHit.setFire(60);
				return true;
			}
		}
		return false;
	}
	*/
	
	@Override
	public void onUse(EntityPlayer player, ExtendedArcanePlayer arcanePlayer, World world, int x,
			int y, int z, int side, Tiers.Cast castTier) {
		int[] newCoords = UtilCursor.getNewCoordsFromSide(x, y, z, side);
		x = newCoords[0];
		y = newCoords[1];
		z = newCoords[2];
		
		if (!player.canPlayerEdit(x, y, z, side, new ItemStack(Items.flint_and_steel))) {
			return;
		}
		else {
			if (world.isAirBlock(x, y, z)) {
				world.playSoundEffect((double) x + 0.5D, (double) y + 0.5D, (double) z + 0.5D,
						"fire.ignite", 1.0F, (new Random()).nextFloat() * 0.4F + 0.8F);
				world.setBlock(x, y, z, Blocks.fire);
			}
		}
	}
	
}
