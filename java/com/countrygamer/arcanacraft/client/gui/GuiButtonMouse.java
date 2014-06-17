package com.countrygamer.arcanacraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

import org.lwjgl.opengl.GL11;

import com.countrygamer.arcanacraft.common.ACOptions;

public class GuiButtonMouse extends GuiButton {
	
	int type;
	
	public GuiButtonMouse(int id, int x, int y, int type) {
		super(id, x, y, 18, 18, "");
		this.type = type;
		if (this.type < 0) this.type = 0;
		if (this.type > 2) this.type = 2;
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		if (this.visible) {
			mc.getTextureManager().bindTexture(ACOptions.icons);
			GL11.glColor3f(1.0F, 1.0F, 1.0F);
			
			int u = 50;
			int v = 180;
			
			u += this.type * 18;
			
			boolean flag = mouseX >= this.xPosition && mouseY >= this.yPosition
					&& mouseX < this.xPosition + this.width
					&& mouseY < this.yPosition + this.height;
			int hoverState = this.getHoverState(flag);
			v += hoverState * 18;
			
			this.drawTexturedModalRect(this.xPosition, this.yPosition, u, v, this.width,
					this.height);
			
		}
	}
	
	public void nextState() {
		this.type++;
		if (this.type > 2) {
			this.type = 0;
		}
	}
	
}
