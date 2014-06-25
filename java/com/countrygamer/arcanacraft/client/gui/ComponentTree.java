package com.countrygamer.arcanacraft.client.gui;

import java.util.List;

import net.minecraft.util.ResourceLocation;

import com.countrygamer.arcanacraft.common.ArcanaCraft;
import com.countrygamer.arcanacraft.common.extended.ExtendedArcanePlayer;
import com.countrygamer.arcanacraft.common.quom.Quom;
import com.countrygamer.arcanacraft.common.quom.QuomRegistry;
import com.countrygamer.countrygamercore.base.client.gui.GuiScreenBase;
import com.countrygamer.countrygamercore.base.client.gui.widget.WidgetTree;

public class ComponentTree extends ComponentPage {
	
	final ResourceLocation background = new ResourceLocation(ArcanaCraft.pluginID,
			"textures/gui/SkillTree_Background.png");
	
	ExtendedArcanePlayer arcancePlayer;
	WidgetTree tree;
	
	public ComponentTree(GuiScreenBase ownerGui, int guiLeft, int xSize, int guiTop, int ySize,
			int grayColor, ExtendedArcanePlayer arcanePlayer) {
		super(ownerGui, guiLeft, xSize, guiTop, ySize, grayColor);
		this.arcancePlayer = arcanePlayer;
		
	}
	
	@Override
	public void init() {
		
		Quom quomStart = QuomRegistry.quomRegistry.get(0);
		this.tree = new WidgetTree(this.ownerGui, this.ownerGui, this.guiLeft + 10,
				this.guiTop + 11, 180, 180, QuomRegistry.minDisplayColumn,
				QuomRegistry.maxDisplayColumn, QuomRegistry.minDisplayRow,
				QuomRegistry.maxDisplayRow, quomStart.displayColumn, quomStart.displayRow,
				this.background);
		for (int i = 0; i < QuomRegistry.quomRegistry.size(); i++) {
			Quom quom = QuomRegistry.quomRegistry.get(i);
			if (this.arcancePlayer.hasDiscoveredQuom(quom)) {
				this.tree.addComponent(new ComponentTreeItem(this.ownerGui, quom,
						this.arcancePlayer.hasLearnedQuom(quom)));
			}
		}
		
	}
	
	@Override
	public void update() {
		this.tree.updateWidget();
	}
	
	@Override
	public void drawForeground() {
		String title = "Quomi Skill Tree";
		this.ownerGui.mc.fontRenderer.drawString(title, this.guiLeft + (this.xSize / 2)
				- (this.ownerGui.mc.fontRenderer.getStringWidth(title) / 2), this.guiTop + 3,
				this.grayColor);
	}
	
	@Override
	public void drawBackground(int mouseX, int mouseY, float renderPartialTicks) {
		this.tree.drawWidget(mouseX, mouseY, renderPartialTicks);
	}
	
	public void onMouseClick(int x, int y, int mouseButton) {
		this.tree.onMouseClick(x, y, mouseButton);
	}
	
	@Override
	public void addHoverInformation(int mouseX, int mouseY, List<String> hoverInfo) {
		this.tree.addHoverInformation(mouseX, mouseY, hoverInfo);
	}
	
}
