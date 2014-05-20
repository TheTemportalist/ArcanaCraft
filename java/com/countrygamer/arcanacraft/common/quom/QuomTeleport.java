package com.countrygamer.arcanacraft.common.quom;

import net.minecraft.world.World;

import com.countrygamer.arcanacraft.common.extended.ExtendedArcanePlayer;

public class QuomTeleport extends Quom {
	
	public QuomTeleport(String name) {
		super(name);
	}
	
	@Override
	public void onUse(ExtendedArcanePlayer arcanePlayer, World world, int x, int y,
			int z, int side) {
		//CoreUtil.teleportVector(world, arcanePlayer.player);
		System.out.println("Quom Teleport");
	}
	
}
