package com.countrygamer.arcanacraft.client.gui;

public abstract class ComponentPage {
	
	protected final int guiLeft, xSize, guiTop, ySize, grayColor;
	
	public ComponentPage(int guiLeft, int xSize, int guiTop, int ySize, int grayColor) {
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
	
}
