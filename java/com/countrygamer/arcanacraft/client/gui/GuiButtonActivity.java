package com.countrygamer.arcanacraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.countrygamer.arcanacraft.common.ArcanaCraft;
import com.countrygamer.countrygamercore.common.lib.Activity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiButtonActivity extends GuiButton {
	
	Activity activity;
	
	public GuiButtonActivity(int id, int x, int y, Activity activity) {
		super(id, x, y, 18, 18, "");
		this.activity = activity;
		
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		if (this.visible) {
			mc.getTextureManager().bindTexture(
					new ResourceLocation(ArcanaCraft.pluginID, "textures/gui/GuiIcons.png"));
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			
			int u = 0;
			int v = 180;
			
			u += Activity.getInt(this.activity) * 18;
			
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
		this.activity = Activity.getState(Activity.getInt(this.activity) + 1);
	}
	
}
