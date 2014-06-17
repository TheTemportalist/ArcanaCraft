package com.countrygamer.arcanacraft.client.gui;

import net.minecraft.client.Minecraft;

import org.lwjgl.input.Mouse;

import com.countrygamer.countrygamercore.Base.client.gui.GuiScreenBase;
import com.countrygamer.countrygamercore.Base.client.gui.advanced.ScrollBar;

public class GuiFilter extends GuiScreenBase {
	
	ScrollBar scroll;
	
	public GuiFilter() {
		super("Filter");
		this.ySize = 234;
		
	}
	
	@Override
	public void initGui() {
		super.initGui();
		
		this.scroll = new ScrollBar(20, 5, 186, 171, 69, 200);
		
	}
	
	@Override
	protected void backgroundObjects() {
		super.backgroundObjects();
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(GuiScreenBase.MAP_TEXTURE);
		this.scroll.draw(this);
		
	}
	
	@Override
	public void handleMouseInput() {
		super.handleMouseInput();
		
		int x = Mouse.getEventX() * this.width / this.mc.displayWidth - this.guiLeft;
		int y = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1
				- this.guiTop;
		
		int scroll = Mouse.getEventDWheel();
		if (scroll != 0) {
			this.scroll.onScroll(this, x, y, scroll);
		}
		
	}
	
	@Override
	protected void mouseClicked(int mX, int mY, int mouseButton) {
		super.mouseClicked(mX, mY, mouseButton);
		
		int x = mX - this.guiLeft;
		int y = mY - this.guiTop;
		
		this.scroll.onClick(this, x, y);
		
	}
	
	@Override
	protected void mouseMovedOrUp(int mX, int mY, int mouseButton) {
		super.mouseMovedOrUp(mX, mY, mouseButton);
		
		int x = mX - this.guiLeft;
		int y = mY - this.guiTop;
		
		this.scroll.onRelease(this, x, y);
		
	}
	
	@Override
	protected void mouseClickMove(int mX, int mY, int mouseButton, long ticks) {
		super.mouseClickMove(mX, mY, mouseButton, ticks);
		
		int x = mX - this.guiLeft;
		int y = mY - this.guiTop;
		
		this.scroll.onDrag(this, x, y);
		
	}
	
}
