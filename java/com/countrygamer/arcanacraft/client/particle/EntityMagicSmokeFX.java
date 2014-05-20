package com.countrygamer.arcanacraft.client.particle;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EntityMagicSmokeFX extends EntityFX {
	
	public EntityMagicSmokeFX(World world, double x, double y, double z, EnumSmokeColor color) {
		super(world, x, y, z);
		this.particleRed = color.r;
		this.particleGreen = color.g;
		this.particleBlue = color.b;
		
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		this.setParticleTextureIndex(7 - this.particleAge * 8 / this.particleMaxAge);
	}
	
}
