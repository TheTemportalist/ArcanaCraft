package com.countrygamer.arcanacraft.common.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.countrygamer.arcanacraft.common.extended.ExtendedArcanePlayer;
import com.countrygamer.core.Base.Plugin.ExtendedEntity;
import com.countrygamer.core.Base.common.item.ItemBase;

public class ItemArcana extends ItemBase {
	
	public ItemArcana(String modid, String name) {
		super(modid, name);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world,
			EntityPlayer player) {
		/*
		if (!player.isSneaking())
			player.openGui(ArcanaCraft.pluginID, ACOptions.arcanaGui, world, (int) player.posX,
					(int) player.posY, (int) player.posZ);
		 */
		ExtendedArcanePlayer arcanePlayer = (ExtendedArcanePlayer) ExtendedEntity
				.getExtended(player, ExtendedArcanePlayer.class);
		if (player.isSneaking())
			arcanePlayer.setStamina(arcanePlayer.getStamina() + 2);
		else
			System.out.println((player.worldObj.isRemote ? "Client" : "Server") + ": " + arcanePlayer.getStamina());
		return itemStack;
	}
	
}
