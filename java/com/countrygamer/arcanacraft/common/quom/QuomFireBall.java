package com.countrygamer.arcanacraft.common.quom;

import net.minecraft.world.World;

import com.countrygamer.arcanacraft.common.extended.ExtendedArcanePlayer;
import com.countrygamer.arcanacraft.common.quom.Tiers.Cast;
import com.countrygamer.arcanacraft.common.quom.Tiers.MANUS;

public class QuomFireBall extends Quom {
	
	public QuomFireBall(String name, String parent) {
		super(name, parent);
	}

	@Override
	public void onUse(ExtendedArcanePlayer arcanePlayer, World world, int x, int y,
			int z, int side, Cast castTier, MANUS manusTier) {
		
	}
	
}
