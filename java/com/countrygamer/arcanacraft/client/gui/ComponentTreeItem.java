package com.countrygamer.arcanacraft.client.gui;

import java.util.List;

import com.countrygamer.arcanacraft.common.ACOptions;
import com.countrygamer.arcanacraft.common.quom.Quom;
import com.countrygamer.countrygamercore.Base.client.gui.GuiScreenBase;
import com.countrygamer.countrygamercore.Base.client.gui.widget.Component;
import com.countrygamer.countrygamercore.lib.UtilRender;

public class ComponentTreeItem extends Component {
	
	boolean learned = false;
	int parents;
	Quom quom;
	
	public ComponentTreeItem(GuiScreenBase ownerGui, Quom quom, boolean learned) {
		super(ownerGui, quom.displayColumn, quom.displayRow);
		this.quom = quom;
		
		this.learned = learned;
		
		this.parents = 0;
		Quom varQ = quom.getParent();
		while (varQ != null) {
			this.parents++;
			varQ = varQ.getParent();
		}
		
	}
	
	@Override
	public void draw(GuiScreenBase gui, int x, int y, int leftOffset, int rightOffset,
			int topOffset, int bottomOffset) {
		UtilRender.bindResource(ACOptions.icons);
		UtilRender.drawTextureWithOffsets(this.owner, x, y, this.parents > 0 ? 26 : 0, 128 - (this.learned ? 0 : 26), 26, 26,
				leftOffset, rightOffset, topOffset, bottomOffset);
		this.quom.draw(this.owner, x + 5, y + 5, leftOffset > 0 ? leftOffset - 5 : 0,
				rightOffset - 5 > 0 ? rightOffset - 5 : 0, topOffset > 0 ? topOffset - 5 : 0,
				bottomOffset - 5 > 0 ? bottomOffset - 5 : 0);
	}
	
	@Override
	public void onClick() {
		if (this.learned) {
			this.owner.mc.displayGuiScreen(new GuiQuomNotes(this.owner, this.quom));
		}
		else {
			((GuiArcana)this.owner).arcanePlayer.learnQuom(this.quom);
			((GuiArcana)this.owner).initGui();
		}
	}
	
	@Override
	public void onHover(List<String> hoverInfo) {
		hoverInfo.add(this.quom.getName());
		
	}
	
}
