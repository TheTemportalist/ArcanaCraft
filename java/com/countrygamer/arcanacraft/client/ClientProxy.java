package com.countrygamer.arcanacraft.client;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import com.countrygamer.arcanacraft.client.gui.ArcaneOverlay;
import com.countrygamer.arcanacraft.client.gui.GuiArcana;
import com.countrygamer.arcanacraft.client.render.TileEntityATRenderer;
import com.countrygamer.arcanacraft.common.ACOptions;
import com.countrygamer.arcanacraft.common.CommonProxy;
import com.countrygamer.arcanacraft.common.tile.TileEntityAugmentedTank;

import cpw.mods.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void registerRender() {
		MinecraftForge.EVENT_BUS.register(new ArcaneOverlay(Minecraft.getMinecraft()));
		
		// MinecraftForgeClient.registerItemRenderer(ACItems.basicFocus,
		// (IItemRenderer) (new ItemRenderFocus()));
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAugmentedTank.class,
				new TileEntityATRenderer());
		
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == ACOptions.arcanaGui || ID == ACOptions.arcanaGuiReset) {
			return new GuiArcana(player, ID == ACOptions.arcanaGuiReset);
		}
		return null;
	}
	
}
