package com.countrygamer.arcanacraft.client;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

import com.countrygamer.arcanacraft.client.gui.ArcaneOverlay;
import com.countrygamer.arcanacraft.client.gui.GuiArcana;
import com.countrygamer.arcanacraft.client.gui.GuiBinder;
import com.countrygamer.arcanacraft.client.gui.GuiSack;
import com.countrygamer.arcanacraft.client.render.ItemATRenderer;
import com.countrygamer.arcanacraft.client.render.ItemBinderRenderer;
import com.countrygamer.arcanacraft.client.render.TileEntityATRenderer;
import com.countrygamer.arcanacraft.client.render.TileEntityBinderRenderer;
import com.countrygamer.arcanacraft.common.ACOptions;
import com.countrygamer.arcanacraft.common.CommonProxy;
import com.countrygamer.arcanacraft.common.block.ACBlocks;
import com.countrygamer.arcanacraft.common.inventory.ContainerBinder;
import com.countrygamer.arcanacraft.common.inventory.InventorySack;
import com.countrygamer.arcanacraft.common.recipes.EnumBinderType;
import com.countrygamer.arcanacraft.common.tile.TileEntityAugmentedTank;
import com.countrygamer.arcanacraft.common.tile.TileEntityBinder;

import cpw.mods.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void registerRender() {
		MinecraftForge.EVENT_BUS.register(new ArcaneOverlay(Minecraft.getMinecraft()));
		
		TileEntityATRenderer renderAT = new TileEntityATRenderer();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAugmentedTank.class, renderAT);
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ACBlocks.augmentedTank),
				new ItemATRenderer(renderAT, new TileEntityAugmentedTank()));
		
		TileEntityBinderRenderer renderBinder = new TileEntityBinderRenderer();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBinder.class, renderBinder);
		TileEntityBinder binderTE = new TileEntityBinder();
		binderTE.setBinderType(EnumBinderType.QUOM);
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ACBlocks.quomBinder),
				new ItemBinderRenderer(renderBinder, binderTE));
		binderTE.setBinderType(EnumBinderType.ITEM);
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ACBlocks.itemBinder),
				new ItemBinderRenderer(renderBinder, binderTE));
		binderTE.setBinderType(EnumBinderType.FLUID);
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ACBlocks.fluidBinder),
				new ItemBinderRenderer(renderBinder, binderTE));
		
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (ID == ACOptions.arcanaGui || ID == ACOptions.arcanaGuiReset) {
			return new GuiArcana(player, ID == ACOptions.arcanaGuiReset);
		}
		else if (ID == ACOptions.sackGui) {
			return new GuiSack(player, new InventorySack(player.getHeldItem()));
		}
		else if (tileEntity != null) {
			if (tileEntity instanceof TileEntityBinder && ID == ACOptions.binderGui) {
				return new GuiBinder(new ContainerBinder(player, (TileEntityBinder) tileEntity));
			}
		}
		return null;
	}
	
}
