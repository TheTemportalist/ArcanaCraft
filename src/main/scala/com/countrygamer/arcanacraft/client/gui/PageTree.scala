package com.countrygamer.arcanacraft.client.gui

import java.util

import com.countrygamer.arcanacraft.common.ArcanaCraft
import com.countrygamer.arcanacraft.common.extended.ArcanePlayer
import com.countrygamer.arcanacraft.common.init.Quoms
import com.countrygamer.cgo.client.gui.GuiScreenWrapper
import com.countrygamer.cgo.client.gui.widget.WidgetTree
import com.countrygamer.cgo.common.lib.util.UtilRender
import net.minecraft.util.ResourceLocation

/**
 *
 *
 * @author CountryGamer
 */
class PageTree(ownerGui: GuiScreenWrapper, val arcanePlayer: ArcanePlayer) extends Page(ownerGui) {

	final val background: ResourceLocation = UtilRender
			.getResource(ArcanaCraft.pluginID, "gui/", "SkillTree_Background")

	var tree: WidgetTree = null

	override def init(): Unit = {

		var centerCol: Int = 0
		var centerRow: Int = 0
		if (Quoms.getQuom(0) != null) {
			centerCol = Quoms.getQuom(0).col
			centerRow = Quoms.getQuom(0).row
		}
		else {
			centerCol = 0
			centerRow = 0
		}

		this.tree = new WidgetTree(this.ownerGui, this.ownerGui,
			this.ownerGui.getGuiLeft() + 10,
			this.ownerGui.getGuiTop() + 11,
			180, 180,
			Quoms.minDisplayColumn, Quoms.maxDisplayColumn,
			Quoms.minDisplayRow, Quoms.maxDisplayRow,
			centerCol, centerRow, this.background
		)

		for (i <- 0 until Quoms.getSize()) {
			if (this.arcanePlayer.hasDiscoveredQuom(i)) {
				this.tree.addComponent(new ComponentQuom(this.ownerGui, Quoms.getQuom(i),
					this.arcanePlayer.hasLearnedQuom(i)))
			}
		}

	}

	override def onUpdate(): Unit = {
		if (this.tree != null)
			this.tree.updateWidget()

	}

	override def drawForeground(): Unit = {
		this.drawTitle("Quom Skill Tree")
	}

	override def drawTitle(title: String): Unit = {
		this.ownerGui.drawString(title,
			this.ownerGui.getGuiLeft() + (this.ownerGui.getXSize() / 2) -
					(this.ownerGui.getStringWidth(title) / 2),
			this.ownerGui.getGuiTop() + 3
		)
	}

	override def drawBackground(mouseX: Int, mouseY: Int, renderPartialTicks: Float): Unit = {
		if (this.tree != null)
			this.tree.drawWidget(mouseX, mouseY, renderPartialTicks)
	}

	override def onMouseClick(mouseX: Int, mouseY: Int, mouseButton: Int): Unit = {
		if (this.tree != null)
			this.tree.onMouseClick(mouseX, mouseY, mouseButton)
	}

	override def addHoverInformation(mouseX: Int, mouseY: Int,
			hoverInfo: util.List[String]): Unit = {
		if (this.tree != null)
			this.tree.addHoverInformation(mouseX, mouseY, hoverInfo)
	}

}
