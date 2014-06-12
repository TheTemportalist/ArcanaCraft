package com.countrygamer.arcanacraft.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.countrygamer.arcanacraft.common.inventory.ContainerBinder;
import com.countrygamer.arcanacraft.common.inventory.ContainerSack;
import com.countrygamer.arcanacraft.common.inventory.InventorySack;
import com.countrygamer.arcanacraft.common.tile.TileEntityBinder;
import com.countrygamer.countrygamercore.Base.Plugin.PluginCommonProxy;

public class CommonProxy implements PluginCommonProxy {
	
	@Override
	public void registerRender() {
	}
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		
		if (ID == ACOptions.sackGui) {
			return new ContainerSack(player, new InventorySack(player.getHeldItem()));
		}
		else if (tileEntity != null) {
			if (tileEntity instanceof TileEntityBinder && ID == ACOptions.binderGui) {
				return new ContainerBinder(player, (TileEntityBinder) tileEntity);
			}
		}
		
		return null;
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}
	
}
