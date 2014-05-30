package com.countrygamer.arcanacraft.common.quom;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import com.countrygamer.arcanacraft.common.extended.ExtendedArcanePlayer;
import com.countrygamer.arcanacraft.common.quom.Tiers.Cast;

public class QuomTempest extends Quom {

	public QuomTempest(String name, Quom parent) {
		super(name, parent);
	}

	@Override
	public boolean onUse(EntityPlayer player, ExtendedArcanePlayer arcanePlayer, World world, int x,
			int y, int z, int side, Cast castTier) {
		return false;
	}

	@Override
	public void onRightClick(EntityPlayer player, ExtendedArcanePlayer arcanePlayer, World world,
			Cast castTier) {		
		// TODO call or banish rain
	}
	
}
