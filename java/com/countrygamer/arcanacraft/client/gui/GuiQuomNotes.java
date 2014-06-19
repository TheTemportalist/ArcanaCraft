package com.countrygamer.arcanacraft.client.gui;

import com.countrygamer.arcanacraft.common.quom.Quom;
import com.countrygamer.countrygamercore.Base.client.gui.GuiScreenBase;

public class GuiQuomNotes extends GuiScreenBase {
	
	GuiScreenBase ownerGui;
	Quom quom;
	
	public GuiQuomNotes(GuiScreenBase ownerGui, Quom quom) {
		super();
		
		this.ownerGui = ownerGui;
		this.quom = quom;
		
	}
	
	@Override
	protected void mouseClicked(int x, int y, int button) {
		if (button == 1) {
			this.mc.displayGuiScreen(this.ownerGui);
		}
		else {
			super.mouseClicked(x, y, button);
		}
	}
	
}
