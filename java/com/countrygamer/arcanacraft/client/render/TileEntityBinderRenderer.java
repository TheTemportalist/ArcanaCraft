package com.countrygamer.arcanacraft.client.render;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import org.lwjgl.opengl.GL11;

import com.countrygamer.arcanacraft.client.model.ModelBinder;
import com.countrygamer.arcanacraft.common.ArcanaCraft;
import com.countrygamer.arcanacraft.common.recipes.EnumBinderType;
import com.countrygamer.arcanacraft.common.tile.TileEntityBinder;
import com.countrygamer.countrygamercore.lib.CoreUtilRender;

public class TileEntityBinderRenderer extends TileEntitySpecialRenderer {
	
	final ResourceLocation texture = new ResourceLocation(ArcanaCraft.pluginID,
			"textures/render/Binder.png");
	
	final RenderItem bindableRender;
	final RenderItem bindingItemRender;
	
	static final ModelBinder model = new ModelBinder();
	
	public TileEntityBinderRenderer() {
		
		this.bindableRender = CoreUtilRender.basicRender;
		this.bindableRender.setRenderManager(RenderManager.instance);
		
		this.bindingItemRender = CoreUtilRender.basicRender;
		this.bindingItemRender.setRenderManager(RenderManager.instance);
		
	}
	
	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x1, double y1, double z1, float f) {
		TileEntityBinder binderTE = (TileEntityBinder) tileEntity;
		this.bindTexture(texture);
		
		GL11.glPushMatrix();
		GL11.glTranslated(x1 + 0.5, y1 + 0.5, z1 + 0.5);
		
		float f5 = 0.0625F;
		
		TileEntityBinderRenderer.model.renderModel(f5);
		
		if (binderTE.getStackInSlot(0) != null) {
			CoreUtilRender.renderItem(binderTE, this.bindableRender, binderTE.getStackInSlot(0)
					.copy(), 0.0F, +0.15F, 0.0F);
		}
		
		if (binderTE.binderType == EnumBinderType.QUOM) {
			
		}
		else if (binderTE.binderType == EnumBinderType.ITEM) {
			if (binderTE.getStackInSlot(1) != null) {
				CoreUtilRender.renderItem(binderTE, this.bindingItemRender, binderTE
						.getStackInSlot(1).copy(), 0.0F, -0.37F, 0.0F);
			}
		}
		else if (binderTE.binderType == EnumBinderType.FLUID) {
			//this.renderInternalFluid(binderTE.getBindingFluidStack(),
			//		binderTE.getBindingTankCapacity());
		}
		
		GL11.glPopMatrix();
	}
	
	@SuppressWarnings("unused")
	private void renderInternalFluid(FluidStack storedFluid, int capacity) {
		GL11.glDisable(GL11.GL_LIGHTING);
		if (storedFluid == null || storedFluid.getFluid() == null
				|| storedFluid.getFluid().getStillIcon() == null) return;
		this.bindTexture(TextureMap.locationBlocksTexture);
		
		Fluid fluid = storedFluid.getFluid();
		
		final IIcon texture = fluid.getStillIcon();
		final int color = fluid.getColor(storedFluid);
		
		Tessellator t = Tessellator.instance;
		
		final double uMin = texture.getInterpolatedU(0.0);
		final double uMax = texture.getInterpolatedU(16.0);
		final double vMin = texture.getInterpolatedV(0.0);
		final double vMax = texture.getInterpolatedV(16.0);
		
		final double vHeight = vMax - vMin;
		
		final float r = (color >> 16 & 0xFF) / 255.0F;
		final float g = (color >> 8 & 0xFF) / 255.0F;
		final float b = (color & 0xFF) / 255.0F;
		
		double volumeRatio = (double) storedFluid.amount / (double) capacity;
		float radius = 0.13F;
		float bottom = 0.374F;
		float maxHeight = 0.30F;
		double scaledHeight = volumeRatio * maxHeight;
		
		t.startDrawingQuads();
		t.setColorOpaque_F(r, g, b);
		
		// north
		t.addVertexWithUV(radius, -bottom, -radius, uMax, vMin); // bottom
		t.addVertexWithUV(-radius, -bottom, -radius, uMin, vMin); // bottom
		// top north/west
		t.addVertexWithUV(-radius, -bottom + scaledHeight, -radius, uMin, vMin
				+ (vHeight * volumeRatio));
		// top north/east
		t.addVertexWithUV(radius, -bottom + scaledHeight, -radius, uMax, vMin
				+ (vHeight * volumeRatio));
		
		// south side
		t.addVertexWithUV(radius, -bottom, radius, uMin, vMin);
		// top south east
		t.addVertexWithUV(radius, -bottom + scaledHeight, radius, uMin, vMin
				+ (vHeight * volumeRatio));
		// top south west
		t.addVertexWithUV(-radius, -bottom + scaledHeight, radius, uMax, vMin
				+ (vHeight * volumeRatio));
		t.addVertexWithUV(-radius, -bottom, radius, uMax, vMin);
		
		// east side
		t.addVertexWithUV(radius, -bottom, -radius, uMin, vMin);
		// top north/east
		t.addVertexWithUV(radius, -bottom + scaledHeight, -radius, uMin, vMin
				+ (vHeight * volumeRatio));
		// top south/east
		t.addVertexWithUV(radius, -bottom + scaledHeight, radius, uMax, vMin
				+ (vHeight * volumeRatio));
		t.addVertexWithUV(radius, -bottom, radius, uMax, vMin);
		
		// west side
		t.addVertexWithUV(-radius, -bottom, radius, uMin, vMin);
		// top south/west
		t.addVertexWithUV(-radius, -bottom + scaledHeight, radius, uMin, vMin
				+ (vHeight * volumeRatio));
		// top north/west
		t.addVertexWithUV(-radius, -bottom + scaledHeight, -radius, uMax, vMin
				+ (vHeight * volumeRatio));
		t.addVertexWithUV(-radius, -bottom, -radius, uMax, vMin);
		
		// top
		// south east
		t.addVertexWithUV(radius, -bottom + scaledHeight, radius, uMax, vMin);
		// north east
		t.addVertexWithUV(radius, -bottom + scaledHeight, -radius, uMin, vMin);
		// north west
		t.addVertexWithUV(-radius, -bottom + scaledHeight, -radius, uMin, vMax);
		// south west
		t.addVertexWithUV(-radius, -bottom + scaledHeight, radius, uMax, vMax);
		
		// bottom
		/*
		t.addVertexWithUV(radius, -bottom, -radius, uMax, vMin);
		t.addVertexWithUV(radius, -bottom, radius, uMin, vMin);
		t.addVertexWithUV(-radius, -bottom, radius, uMin, vMax);
		t.addVertexWithUV(-radius, -bottom, -radius, uMax, vMax);
		 */
		
		t.draw();
		GL11.glEnable(GL11.GL_LIGHTING);
		
	}
	
}
