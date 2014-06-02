package com.countrygamer.arcanacraft.client.gui;

import java.util.List;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import com.countrygamer.arcanacraft.common.ArcanaCraft;
import com.countrygamer.arcanacraft.common.extended.ExtendedArcanePlayer;
import com.countrygamer.core.Base.Plugin.extended.ExtendedEntity;
import com.countrygamer.core.Base.client.gui.GuiContainerBase;
import com.countrygamer.core.Base.common.inventory.ContainerBase;

public class GuiManusPowered extends GuiContainerBase {
	
	protected static final ResourceLocation guiIcons = new ResourceLocation(ArcanaCraft.pluginID,
			"textures/gui/GuiIcons.png");
	
	ExtendedArcanePlayer arcanePlayer;
	int powerGuiX, powerGuiY;
	
	public GuiManusPowered(ContainerBase container, int liquidPowerX, int liquidPowerY) {
		super(0, 0, container);
		
		this.arcanePlayer = (ExtendedArcanePlayer) ExtendedEntity.getExtended(this.getPlayer(),
				ExtendedArcanePlayer.class);
		
		this.powerGuiX = liquidPowerX;
		this.powerGuiY = liquidPowerY;
		
	}
	
	@Override
	protected void addHoverInfomation(int mouseX, int mouseY, List<String> hoverInfo) {
		
	}
	
	@Override
	protected void foregroundText() {
		
	}
	
	@Override
	protected void backgroundObjects() {
		this.drawManusTank();
	}
	
	private void drawManusTank() {
		this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
		
		int powerPosX = this.guiLeft + this.powerGuiX;
		int powerPosY = this.guiTop + this.powerGuiY;
		
		FluidStack storedFluid = this.getTileEntity().getFluidStack();
		if (storedFluid != null && storedFluid.getFluid().getStillIcon() != null) {
			IIcon renderIndex = storedFluid.getFluid().getStillIcon();
			
			int x = powerPosX + 2;
			int y = powerPosY + 62;
			float buckets = (float) storedFluid.amount / 1000.0F;
			
			int liquidIconHeight = (int) buckets * 6;
			int fullIcons = liquidIconHeight / 16;
			int leftOverIconHeight = liquidIconHeight - (fullIcons * 16);
			for (int icon = 1; icon <= fullIcons; icon++) {
				this.drawLiquidRect(x, y - (icon * 16), renderIndex, 16, 16);
				this.drawLiquidRect(x + 16, y - (icon * 16), renderIndex, 16, 16);
			}
			this.drawLiquidRect(x, y - (fullIcons * 16) - leftOverIconHeight, renderIndex, 16,
					leftOverIconHeight);
			this.drawLiquidRect(x + 16, y - (fullIcons * 16) - leftOverIconHeight, renderIndex, 16,
					leftOverIconHeight);
			
		}
		
		this.mc.getTextureManager().bindTexture(guiIcons);
		/*
		x += 63;
		y += 14;
		u = 0;
		v = 38;
		w = 36;
		h = 68;
		 */
		this.drawTexturedModalRect(powerPosX, powerPosY, 0, 38, 36, 64);
	}
	
	protected void drawLiquidRect(int startU, int startV, IIcon par3Icon, int endU, int endV) {
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(startU + 0, startV + endV, this.zLevel, par3Icon.getMinU(),
				par3Icon.getMaxV());// Bottom left
		tessellator.addVertexWithUV(startU + endU, startV + endV, this.zLevel, par3Icon.getMaxU(),
				par3Icon.getMaxV());// Bottom right
		tessellator.addVertexWithUV(startU + endU, startV + 0, this.zLevel, par3Icon.getMaxU(),
				par3Icon.getMinV());// Top right
		tessellator.addVertexWithUV(startU + 0, startV + 0, this.zLevel, par3Icon.getMinU(),
				par3Icon.getMinV()); // Top left
		tessellator.draw();
	}
	
}
