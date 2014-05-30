package com.countrygamer.arcanacraft.common.quom;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.countrygamer.arcanacraft.common.extended.ExtendedArcanePlayer;
import com.countrygamer.arcanacraft.common.quom.Tiers.Cast;

public class QuomQuell extends Quom {
	
	public static final String dataKey = "QuomQuell";
	public static final int radius = 6;
	
	public QuomQuell(String name, Quom parent) {
		super(name, parent);
	}
	
	@Override
	public boolean onUse(EntityPlayer player, ExtendedArcanePlayer arcanePlayer, World world,
			int x, int y, int z, int side, Cast castTier) {
		return false;
	}
	
	@Override
	public void onRightClick(EntityPlayer player, ExtendedArcanePlayer arcanePlayer, World world,
			Cast castTier) {
		NBTTagCompound quomData = arcanePlayer.getQuomData(QuomQuell.dataKey);
		boolean isSuppressing = quomData != null ? quomData.getBoolean("isActive") : false;
		if (isSuppressing) {
			arcanePlayer.removeQuomData(QuomQuell.dataKey);
		}
		else {
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("isActive", true);
			arcanePlayer.addQuomData(QuomQuell.dataKey, data);
		}
		// NOTE: The actual suppressing of liquids is performed in the playerTick in
		// ArcanaCraft.java
	}
	
}
