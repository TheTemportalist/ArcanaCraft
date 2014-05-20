package com.countrygamer.arcanacraft.client;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

import com.countrygamer.arcanacraft.client.gui.ArcaneOverlay;
import com.countrygamer.arcanacraft.common.CommonProxy;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void registerRender() {
		MinecraftForge.EVENT_BUS
				.register(new ArcaneOverlay(Minecraft.getMinecraft()));
		
		//MinecraftForgeClient.registerItemRenderer(ACItems.basicFocus,
		//		(IItemRenderer) (new ItemRenderFocus()));
	}
	
}
