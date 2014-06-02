package com.countrygamer.arcanacraft.client.render;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import com.countrygamer.arcanacraft.common.tile.TileEntityBinder;

public class ItemBinderRenderer implements IItemRenderer {
	
	final TileEntitySpecialRenderer render;
	final TileEntityBinder dummyTile;
	
	public ItemBinderRenderer(TileEntitySpecialRenderer render, TileEntityBinder dummy) {
		this.render = render;
		this.dummyTile = dummy;
		
	}
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}
	
	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		return true;
	}
	
	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		if (type == IItemRenderer.ItemRenderType.ENTITY) GL11.glTranslatef(-0.5F, 0.0F, -0.5F);
		
		this.render.renderTileEntityAt(this.dummyTile, 0.0D, 0.0D, 0.0D, 0.0F);
	}
	
}
