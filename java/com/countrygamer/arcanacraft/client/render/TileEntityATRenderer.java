package com.countrygamer.arcanacraft.client.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import org.lwjgl.opengl.GL11;

import com.countrygamer.arcanacraft.common.block.ACBlocks;
import com.countrygamer.arcanacraft.common.tile.TileEntityAugmentedTank;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileEntityATRenderer extends TileEntitySpecialRenderer {
	
	RenderBlocks	renderBlocks	= new RenderBlocks();
	
	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x1, double y1, double z1, float f) {
		this.bindTexture(TextureMap.locationBlocksTexture);
		TileEntityAugmentedTank tankTE = (TileEntityAugmentedTank) tileEntity;
		
		GL11.glPushMatrix();
		GL11.glTranslated(x1 + 0.5, y1 + 0.5, z1 + 0.5);
		
		this.renderSide(+0.475, -0.501, -0.501, +0.501, +0.501, -0.475, ACBlocks.augmentedTank, 0);
		this.renderSide(+0.475, -0.501, +0.475, +0.501, +0.501, +0.501, ACBlocks.augmentedTank, 0);
		this.renderSide(+0.475, -0.501, -0.501, +0.501, -0.475, +0.501, ACBlocks.augmentedTank, 0);
		this.renderSide(+0.475, +0.475, -0.501, +0.501, +0.501, +0.501, ACBlocks.augmentedTank, 0);
		
		this.renderSide(-0.501, -0.501, -0.501, -0.475, +0.501, -0.475, ACBlocks.augmentedTank, 0);
		this.renderSide(-0.501, -0.501, +0.475, -0.475, +0.501, +0.501, ACBlocks.augmentedTank, 0);
		this.renderSide(-0.501, -0.501, -0.501, -0.475, -0.475, +0.501, ACBlocks.augmentedTank, 0);
		this.renderSide(-0.501, +0.475, -0.501, -0.475, +0.501, +0.501, ACBlocks.augmentedTank, 0);
		
		this.renderSide(-0.501, +0.475, -0.501, +0.501, +0.501, -0.475, ACBlocks.augmentedTank, 0);
		this.renderSide(-0.501, -0.501, -0.501, +0.501, -0.475, -0.475, ACBlocks.augmentedTank, 0);
		
		this.renderSide(-0.501, +0.475, +0.475, +0.501, +0.501, +0.501, ACBlocks.augmentedTank, 0);
		this.renderSide(-0.501, -0.501, +0.475, +0.501, -0.475, +0.501, ACBlocks.augmentedTank, 0);
		
		FluidStack fluidStored = tankTE.getFluidStored();
		if (fluidStored != null && fluidStored.getFluid() != null
				&& fluidStored.getFluid().getStillIcon() != null) {
			GL11.glDisable(GL11.GL_LIGHTING);
			final Fluid fluid = fluidStored.getFluid();
			
			final IIcon texture = fluid.getStillIcon();
			final int color = fluid.getColor(fluidStored);
			
			Tessellator t = Tessellator.instance;
			
			final double uMinTex = texture.getInterpolatedU(0.0);
			final double uMaxTex = texture.getInterpolatedU(16.0);
			final double vMinTex = texture.getInterpolatedV(0.0);
			final double vMaxTex = texture.getInterpolatedV(16.0);
			final double centerU = texture.getInterpolatedU(8.0);
			final double centerV = texture.getInterpolatedV(8.0);
			double uMin = uMinTex;
			double uMax = uMaxTex;
			double vMin = vMinTex;
			double vMax = vMaxTex;
			
			final double vHeight = vMaxTex - vMinTex;
			
			final float r = (color >> 16 & 0xFF) / 255.0F;
			final float g = (color >> 8 & 0xFF) / 255.0F;
			final float b = (color & 0xFF) / 255.0F;
			
			boolean renderNormal = false;
			
			double center = 0.5;
			double volumeRatio = tankTE.getFluidRatio();
			double radius = volumeRatio * center;
			double radiusOffset = center + radius;
			
			if (!renderNormal) {
				//double scaledRadius = radius / 16.0 * 0.5;
				//uMin = centerU - scaledRadius;
				//uMax = centerU + scaledRadius;
				//vMin = centerV - scaledRadius;
				//vMax = centerV + scaledRadius;
			}
			
			t.startDrawingQuads();
			t.setColorOpaque_F(r, g, b);
			
			if (!renderNormal) {
				
				// north
				// bottom left
				t.addVertexWithUV(
						-center + radiusOffset,
						+center - radiusOffset,
						+center - radiusOffset,
						uMax, vMin);
				// bottom right
				t.addVertexWithUV(
						+center - radiusOffset,
						+center - radiusOffset,
						+center - radiusOffset,
						uMin, vMin);
				// top right
				t.addVertexWithUV(
						+center - radiusOffset,
						-center + radiusOffset,
						+center - radiusOffset,
						uMin, vMax);
				// top left
				t.addVertexWithUV(
						-center + radiusOffset,
						-center + radiusOffset,
						+center - radiusOffset,
						uMax, vMax);
				
				// south side
				// bottom right
				t.addVertexWithUV(
						-center + radiusOffset,
						+center - radiusOffset,
						-center + radiusOffset,
						uMin, vMin);
				// top right
				t.addVertexWithUV(
						-center + radiusOffset,
						-center + radiusOffset,
						-center + radiusOffset,
						uMin, vMax);
				// top left
				t.addVertexWithUV(
						+center - radiusOffset,
						-center + radiusOffset,
						-center + radiusOffset,
						uMax, vMax);
				// bottom left
				t.addVertexWithUV(
						+center - radiusOffset,
						+center - radiusOffset,
						-center + radiusOffset,
						uMax, vMin);
				
				// east side
				t.addVertexWithUV(
						-center + radiusOffset,
						+center - radiusOffset,
						+center - radiusOffset,
						uMin, vMin);
				t.addVertexWithUV(
						-center + radiusOffset,
						-center + radiusOffset,
						+center - radiusOffset,
						uMin, vMax);
				t.addVertexWithUV(
						-center + radiusOffset,
						-center + radiusOffset,
						-center + radiusOffset,
						uMax, vMax);
				t.addVertexWithUV(
						-center + radiusOffset,
						+center - radiusOffset,
						-center + radiusOffset,
						uMax, vMin);
				
				// west side
				t.addVertexWithUV(
						+center - radiusOffset,
						+center - radiusOffset,
						-center + radiusOffset,
						uMin, vMin);
				t.addVertexWithUV(
						+center - radiusOffset,
						-center + radiusOffset,
						-center + radiusOffset,
						uMin, vMax);
				t.addVertexWithUV(
						+center - radiusOffset,
						-center + radiusOffset,
						+center - radiusOffset,
						uMax, vMax);
				t.addVertexWithUV(
						+center - radiusOffset,
						+center - radiusOffset,
						+center - radiusOffset,
						uMax, vMin);
				
				// top
				// top left
				t.addVertexWithUV(
						-center + radiusOffset,
						-center + radiusOffset,
						-center + radiusOffset,
						uMax, vMin);
				// bottom left
				t.addVertexWithUV(
						-center + radiusOffset,
						-center + radiusOffset,
						+center - radiusOffset,
						uMin, vMin);
				// bottom right
				t.addVertexWithUV(
						+center - radiusOffset,
						-center + radiusOffset,
						+center - radiusOffset,
						uMin, vMax);
				// top right
				t.addVertexWithUV(
						+center - radiusOffset,
						-center + radiusOffset,
						-center + radiusOffset,
						uMax, vMax);
				
				// bottom
				t.addVertexWithUV(
						-center + radiusOffset,
						+center - radiusOffset,
						+center - radiusOffset,
						uMax, vMin);
				t.addVertexWithUV(
						-center + radiusOffset,
						+center - radiusOffset,
						-center + radiusOffset,
						uMin, vMin);
				t.addVertexWithUV(
						+center - radiusOffset,
						+center - radiusOffset,
						-center + radiusOffset,
						uMin, vMax);
				t.addVertexWithUV(
						+center - radiusOffset,
						+center - radiusOffset,
						+center - radiusOffset,
						uMax, vMax);
				
			}
			else {
				// north
				t.addVertexWithUV(0.5, -0.5, -0.5, uMax, vMin); // bottom
				t.addVertexWithUV(-0.5, -0.5, -0.5, uMin, vMin); // bottom
				// top north/west
				t.addVertexWithUV(-0.5, -0.5 + volumeRatio, -0.5, uMin, vMin
						+ (vHeight * volumeRatio));
				// top north/east
				t.addVertexWithUV(0.5, -0.5 + volumeRatio, -0.5, uMax, vMin
						+ (vHeight * volumeRatio));
				
				// south side
				t.addVertexWithUV(0.5, -0.5, 0.5, uMin, vMin);
				// top south east
				t.addVertexWithUV(0.5, -0.5 + volumeRatio, 0.5, uMin, vMin
						+ (vHeight * volumeRatio));
				// top south west
				t.addVertexWithUV(-0.5, -0.5 + volumeRatio, 0.5, uMax, vMin
						+ (vHeight * volumeRatio));
				t.addVertexWithUV(-0.5, -0.5, 0.5, uMax, vMin);
				
				// east side
				t.addVertexWithUV(0.5, -0.5, -0.5, uMin, vMin);
				// top north/east
				t.addVertexWithUV(0.5, -0.5 + volumeRatio, -0.5, uMin, vMin
						+ (vHeight * volumeRatio));
				// top south/east
				t.addVertexWithUV(0.5, -0.5 + volumeRatio, 0.5, uMax, vMin
						+ (vHeight * volumeRatio));
				t.addVertexWithUV(0.5, -0.5, 0.5, uMax, vMin);
				
				// west side
				t.addVertexWithUV(-0.5, -0.5, 0.5, uMin, vMin);
				// top south/west
				t.addVertexWithUV(-0.5, -0.5 + volumeRatio, 0.5, uMin, vMin
						+ (vHeight * volumeRatio));
				// top north/west
				t.addVertexWithUV(-0.5, -0.5 + volumeRatio, -0.5, uMax, vMin
						+ (vHeight * volumeRatio));
				t.addVertexWithUV(-0.5, -0.5, -0.5, uMax, vMin);
				
				// top
				// south east
				t.addVertexWithUV(0.5, -0.5 + volumeRatio, 0.5, uMax, vMin);
				// north east
				t.addVertexWithUV(0.5, -0.5 + volumeRatio, -0.5, uMin, vMin);
				// north west
				t.addVertexWithUV(-0.5, -0.5 + volumeRatio, -0.5, uMin, vMax);
				// south west
				t.addVertexWithUV(-0.5, -0.5 + volumeRatio, 0.5, uMax, vMax);
				
				// bottom
				t.addVertexWithUV(0.5, -0.5, -0.5, uMax, vMin);
				t.addVertexWithUV(0.5, -0.5, 0.5, uMin, vMin);
				t.addVertexWithUV(-0.5, -0.5, 0.5, uMin, vMax);
				t.addVertexWithUV(-0.5, -0.5, -0.5, uMax, vMax);
			}
			
			t.draw();
			GL11.glEnable(GL11.GL_LIGHTING);
		}
		
		GL11.glPopMatrix();
	}
	
	private void renderSide(double x1, double y1, double z1, double x2, double y2, double z2,
			Block block, int meta) {
		GL11.glPushMatrix();
		Tessellator t = Tessellator.instance;
		
		GL11.glColor4f(1, 1, 1, 1);
		
		renderBlocks.setRenderBounds(x1, y1, z1, x2, y2, z2);
		
		t.startDrawingQuads();
		
		IIcon icon = block.getIcon(0, meta);
		t.setNormal(0.0F, -1.0F, 0.0F);
		renderBlocks.renderFaceYNeg(block, 0, 0, 0, icon);
		
		t.setNormal(0.0F, 1.0F, 0.0F);
		renderBlocks.renderFaceYPos(block, 0, 0, 0, icon);
		
		icon = block.getIcon(2, meta);
		t.setNormal(0.0F, 0.0F, -1.0F);
		renderBlocks.renderFaceZNeg(block, 0, 0, 0, icon);
		
		icon = block.getIcon(3, meta);
		t.setNormal(0.0F, 0.0F, 1.0F);
		renderBlocks.renderFaceZPos(block, 0, 0, 0, icon);
		
		icon = block.getIcon(4, meta);
		t.setNormal(-1.0F, 0.0F, 0.0F);
		renderBlocks.renderFaceXNeg(block, 0, 0, 0, icon);
		
		icon = block.getIcon(5, meta);
		t.setNormal(1.0F, 0.0F, 0.0F);
		renderBlocks.renderFaceXPos(block, 0, 0, 0, icon);
		t.draw();
		
		GL11.glPopMatrix();
	}
	
}
