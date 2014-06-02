package com.countrygamer.arcanacraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

import com.countrygamer.arcanacraft.common.ACOptions;
import com.countrygamer.arcanacraft.common.quom.Quom;

public class QuomSlot {
	
	Minecraft mc = Minecraft.getMinecraft();
	Gui g = new Gui();
	
	int x, y, u, v;
	ResourceLocation reLoc;
	Quom quom;
	
	public QuomSlot(int x, int y) {
		this(x, y, 0, 20, ACOptions.icons);
	}
	
	public QuomSlot(int x, int y, int u, int v, ResourceLocation rl) {
		this.x = x;
		this.y = y;
		this.u = u;
		this.v = v;
		this.reLoc = rl;
	}
	
	public void draw() {
		this.mc.getTextureManager().bindTexture(this.reLoc);
		g.drawTexturedModalRect(this.x, this.y, this.u, this.v, 18, 18);
		if (this.quom != null) {
			this.quom.draw(this.mc, g, this.x + 1, this.y + 1);
			this.mc.getTextureManager().bindTexture(this.reLoc);
		}
	}
	
	public int[] getCoords() {
		return new int[] {
				x, y
		};
	}
	
	public void setQuom(Quom quom) {
		this.quom = quom;
	}
	
	public Quom getQuom() {
		return this.quom == null ? null : this.quom.copy();
	}
	
}
