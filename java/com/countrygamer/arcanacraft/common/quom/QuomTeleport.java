package com.countrygamer.arcanacraft.common.quom;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import com.countrygamer.arcanacraft.common.extended.EnumSmokeAction;
import com.countrygamer.arcanacraft.common.extended.ExtendedArcanePlayer;
import com.countrygamer.arcanacraft.common.quom.Tiers.Cast;

public class QuomTeleport extends Quom {
	
	public QuomTeleport(String name, Quom parent) {
		super(name, parent);
	}
	
	@Override
	public double getReachLength(Cast castTier) {
		return 500.0D;
	}
	
	@Override
	public void onUse(EntityPlayer player, ExtendedArcanePlayer arcanePlayer, World world, int x,
			int y, int z, int side, Tiers.Cast castTier) {		
		if (!world.isRemote) {
			arcanePlayer.setChanging(EnumSmokeAction.TELEPORT);
			arcanePlayer.setTeleportDestination(new double[] {
					x + 0.5, y + 1, z + 0.5
			});
		}
	}
	
}
