package com.countrygamer.arcanacraft.client.gui

import java.util.List

import com.countrygamer.cgo.client.gui.GuiScreenWrapper

/**
 *
 *
 * @author CountryGamer
 */
abstract class Page(protected final val ownerGui: GuiScreenWrapper) {

	def init(): Unit

	def drawForeground(): Unit

	def drawTitle(title: String): Unit = {
		this.ownerGui.drawString(title,
			this.ownerGui.getGuiLeft() + (this.ownerGui.getXSize() / 2) -
					(this.ownerGui.getStringWidth(title) / 2),
			this.ownerGui.getGuiTop() + 5
		)
	}

	def drawBackground(mouseX: Int, mouseY: Int, renderPartialTicks: Float): Unit

	def onMouseClick(mouseX: Int, mouseY: Int, mouseButton: Int): Unit = {
	}

	def onUpdate(): Unit = {
	}

	def addHoverInformation(mouseX: Int, mouseY: Int, hoverInfo: List[String]): Unit = {
	}

}
