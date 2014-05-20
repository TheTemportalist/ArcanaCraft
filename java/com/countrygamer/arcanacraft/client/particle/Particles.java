package com.countrygamer.arcanacraft.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class Particles {
	
	public static void spawnArcaneSmokeConversion(EntityPlayer player) {
		double x = player.posX;
		double y = player.posY - 1.5;
		double z = player.posZ;
		double radius = 0.7;
		
		for (int i = 0; i < 4; i++) {
			EntityFX smoke = new EntitySwirlingSmokeFX(player.worldObj, x, y, z,
					radius, i, EnumSmokeColor.BLUE);
			Minecraft.getMinecraft().effectRenderer.addEffect(smoke);
		}
	}
	
}
