package com.countrygamer.arcanacraft.common.item;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.countrygamer.arcanacraft.common.ACOptions;
import com.countrygamer.arcanacraft.common.ArcanaCraft;
import com.countrygamer.arcanacraft.common.extended.ExtendedArcanePlayer;
import com.countrygamer.arcanacraft.common.lib.TempTreeGen;
import com.countrygamer.core.Base.Plugin.extended.ExtendedEntity;
import com.countrygamer.core.Base.common.item.ItemBase;

public class ItemArcana extends ItemBase {
	
	boolean debug = true;
	
	public ItemArcana(String modid, String name) {
		super(modid, name);
		this.setMaxStackSize(1);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		ExtendedArcanePlayer arcanePlayer = (ExtendedArcanePlayer) ExtendedEntity.getExtended(
				player, ExtendedArcanePlayer.class);
		if (arcanePlayer == null) return itemStack;
		
		if (!arcanePlayer.isPlayerArcaic()) arcanePlayer.setArcaic(true);
		
		if (debug) {
			String cS = (player.worldObj.isRemote ? "Client" : "Server");
			
			// System.out.println("Manus|" + cS + ": " + arcanePlayer.getManus());
			
		}
		else {
			int id = player.isSneaking() ? ACOptions.arcanaGuiReset : ACOptions.arcanaGui;
			player.openGui(ArcanaCraft.pluginID, id, world, (int) player.posX, (int) player.posY,
					(int) player.posZ);
		}
		
		return itemStack;
	}
	
	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y,
			int z, int side, float par8, float par9, float par10) {
		if (debug) {
			if (!player.worldObj.isRemote)
				TempTreeGen.generatePetrifiedTree(world, x, y + 1, z,
						(new Random()).nextInt(11 - 4) + 4);
		}
		return false;
	}
	
}
