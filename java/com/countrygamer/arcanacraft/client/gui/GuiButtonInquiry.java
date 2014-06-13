package com.countrygamer.arcanacraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.countrygamer.arcanacraft.common.ArcanaCraft;
import com.countrygamer.arcanacraft.common.quom.Quom;

public class GuiButtonInquiry extends GuiButton {
	
	final ResourceLocation texture = new ResourceLocation(ArcanaCraft.pluginID,
			"textures/gui/GuiIcons.png");
	private int tier;
	private Quom quom;
	
	public GuiButtonInquiry(int id, int x, int y, int tier, Quom quom) {
		super(id, x, y, 26, 26, "");
		this.tier = tier;
		this.quom = quom.copy();
		
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		this.drawButtonWithOffsets(mc, mouseX, mouseY, 0, 0, 0, 0, 0, 0);
	}
	
	public void drawButtonWithOffsets(Minecraft mc, int mouseX, int mouseY, int xOffset,
			int yOffset, int uOffset, int vOffset, int wOffset, int hOffset) {
		if (this.visible) {
			mc.getTextureManager().bindTexture(this.texture);
			
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			
			this.field_146123_n = mouseX >= (this.xPosition + xOffset)
					&& mouseY >= (this.yPosition + yOffset)
					&& mouseX < (this.xPosition + xOffset) + this.width
					&& mouseY < (this.yPosition + yOffset) + this.height;
			
			int k = this.getHoverState(this.field_146123_n);
			
			GL11.glEnable(GL11.GL_BLEND);
			
			OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			
			this.drawTexturedModalRect(this.xPosition + xOffset, this.yPosition + yOffset,
					(this.tier - 1) * 26 + uOffset, 102 + (k * 26) + vOffset, this.width - wOffset,
					this.height - hOffset);
			
			this.quom.draw(mc, this, this.xPosition + xOffset + 5, this.yPosition + yOffset + 5);
			
			this.mouseDragged(mc, mouseX, mouseY);
			
		}
	}
	
}
