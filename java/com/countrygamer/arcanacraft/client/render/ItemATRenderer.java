package com.countrygamer.arcanacraft.client.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import com.countrygamer.arcanacraft.common.block.ACBlocks;
import com.countrygamer.arcanacraft.common.tile.TileEntityAugmentedTank;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ItemATRenderer implements IItemRenderer {
	
	TileEntitySpecialRenderer	render;
	private TileEntity			dummytile;
	
	public ItemATRenderer(TileEntitySpecialRenderer render, TileEntity dummy) {
		this.render = render;
		this.dummytile = dummy;
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
		
		if (this.dummytile instanceof TileEntityAugmentedTank)
			if (item != null && Block.getBlockFromItem(item.getItem()) == ACBlocks.augmentedTank) {
				if (item.hasTagCompound()) {
					NBTTagCompound tagCom = item.getTagCompound();
					if (tagCom.hasKey("tank")) {
						((TileEntityAugmentedTank) this.dummytile)
								.loadTankFromNBT(tagCom.getCompoundTag("tank"));
					}
				}
			}
		this.render.renderTileEntityAt(this.dummytile, 0.0D, 0.0D, 0.0D, 0.0F);
		((TileEntityAugmentedTank)this.dummytile).clearTank();
	}
	
}
