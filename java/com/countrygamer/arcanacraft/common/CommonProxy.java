package com.countrygamer.arcanacraft.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.countrygamer.arcanacraft.common.container.ContainerBinder;
import com.countrygamer.arcanacraft.common.tile.TileEntityBinder;
import com.countrygamer.core.Base.Plugin.PluginCommonProxy;

public class CommonProxy implements PluginCommonProxy {
	
	@Override
	public void registerRender() {
	}
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		
		if (tileEntity != null) {
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
