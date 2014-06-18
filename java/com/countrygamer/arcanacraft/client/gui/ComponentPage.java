package com.countrygamer.arcanacraft.client.gui;

import java.util.List;

import com.countrygamer.countrygamercore.Base.client.gui.GuiScreenBase;

public abstract class ComponentPage {
	
	protected final GuiScreenBase ownerGui;
	protected final int guiLeft, xSize, guiTop, ySize, grayColor;
	
	public ComponentPage(GuiScreenBase ownerGui, int guiLeft, int xSize, int guiTop, int ySize,
			int grayColor) {
		this.ownerGui = ownerGui;
		this.guiLeft = guiLeft;
		this.xSize = xSize;
		this.guiTop = guiTop;
		this.ySize = ySize;
		this.grayColor = grayColor;
		
	}
	
	public abstract void init();
	
	public abstract void drawForeground();
	
	public abstract void drawBackground(int mouseX, int mouseY, float renderPartialTicks);
	
	public void onMouseClick(int x, int y, int mouseButton) {
	}
	
	public void update() {
	}
	
	public void addHoverInformation(int mouseX, int mouseY, List<String> hoverInfo) {
		
	}
	
}
