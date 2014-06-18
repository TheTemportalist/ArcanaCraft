package com.countrygamer.arcanacraft.client.gui;

import com.countrygamer.arcanacraft.common.ACOptions;
import com.countrygamer.arcanacraft.common.quom.Quom;
import com.countrygamer.countrygamercore.Base.client.gui.GuiScreenBase;
import com.countrygamer.countrygamercore.Base.client.gui.widget.Component;
import com.countrygamer.countrygamercore.lib.ResourceHelper;

public class ComponentTreeItem extends Component {
	
	Quom quom;
	
	public ComponentTreeItem(GuiScreenBase ownerGui, Quom quom) {
		super(ownerGui, quom.displayColumn, quom.displayRow);
		this.quom = quom;
		
	}
	
	@Override
	public void draw(GuiScreenBase gui, int x, int y) {
		ResourceHelper.bindResource(ACOptions.icons);
		this.owner.drawTexturedModalRect(x, y, 0, 128, 26, 26);
		this.quom.draw(this.owner.mc, this.owner, x + 5, y + 5);
	}
	
}
