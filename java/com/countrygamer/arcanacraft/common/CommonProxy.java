package com.countrygamer.arcanacraft.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import com.countrygamer.core.Base.Plugin.PluginCommonProxy;

public class CommonProxy implements PluginCommonProxy {
	
	@Override
	public void registerRender() {
	}
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		return null;
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		return null;
	}
	
}
