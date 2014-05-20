package com.countrygamer.arcanacraft.client.particle;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class EntitySwirlingSmokeFX extends EntityMagicSmokeFX {
	
	private double centerX, centerZ;
	private double radius;
	private int tickCount, facingSide;
	
	public EntitySwirlingSmokeFX(World world, double centerX, double y,
			double centerZ, double radius, int facingSide, EnumSmokeColor color) {
		super(world, centerX, y, centerZ, color);
		this.centerX = centerX;
		this.centerZ = centerZ;
		this.radius = radius;
		this.facingSide = facingSide;
		
		switch (this.facingSide) {
			case 0:
				this.posZ += this.radius;
				break;
			case 1:
				this.posX -= this.radius;
				break;
			case 2:
				this.posZ -= this.radius;
				break;
			case 3:
				this.posX += this.radius;
				break;
			default:
				break;
		}
		this.tickCount = ((this.facingSide + 1) * 10);
		
		int minAge = 20 * 2 * 3;
		int maxAge = minAge + 40;
		this.particleMaxAge = (new Random()).nextInt(maxAge - minAge) + minAge;
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		if (this.particleAge > this.particleMaxAge) this.setDead();
		
		// 20 = 1 sec
		// 2 sec = 360 deg
		// 40 t = 360 d
		
		double degree = (++this.tickCount) * 9;
		
		double cos = Math.cos(Math.toRadians(degree));
		double adjLength = cos * radius;
		this.posX = this.centerX + adjLength;
		
		double sin = Math.sin(Math.toRadians(degree));
		double oppLength = sin * radius;
		this.posZ = this.centerZ + oppLength;
		
		this.posY = this.prevPosY + 0.015D;
		
	}
}
