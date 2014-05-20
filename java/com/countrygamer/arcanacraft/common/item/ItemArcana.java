package com.countrygamer.arcanacraft.common.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.countrygamer.arcanacraft.common.ACOptions;
import com.countrygamer.arcanacraft.common.ArcanaCraft;
import com.countrygamer.arcanacraft.common.extended.EnumSmokeAction;
import com.countrygamer.arcanacraft.common.extended.ExtendedArcanePlayer;
import com.countrygamer.core.Base.Plugin.ExtendedEntity;
import com.countrygamer.core.Base.common.item.ItemBase;

public class ItemArcana extends ItemBase {
	
	public ItemArcana(String modid, String name) {
		super(modid, name);
		this.setMaxStackSize(1);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world,
			EntityPlayer player) {
		ExtendedArcanePlayer arcanePlayer = (ExtendedArcanePlayer) ExtendedEntity
				.getExtended(player, ExtendedArcanePlayer.class);
		if (!arcanePlayer.isPlayerArcaic()) arcanePlayer.setActiveState(true);

		boolean debug = false;
		if (debug) {
			String cS = (player.worldObj.isRemote ? "Client" : "Server");
			
			System.out.println("Manus|" + cS + ": " + arcanePlayer.getManus());
			
			if (!player.isSneaking()) {
				arcanePlayer.setChanging(EnumSmokeAction.TELEPORT);
				// TODO preselect location
			}
		}
		else {
			int id = player.isSneaking() ? ACOptions.arcanaGuiReset
					: ACOptions.arcanaGui;
			player.openGui(ArcanaCraft.pluginID, id, world, (int) player.posX,
					(int) player.posY, (int) player.posZ);
		}
		
		return itemStack;
	}
	
	
}
