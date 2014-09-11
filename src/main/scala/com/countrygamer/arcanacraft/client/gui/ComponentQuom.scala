package com.countrygamer.arcanacraft.client.gui

import java.util

import com.countrygamer.arcanacraft.common.ACOptions
import com.countrygamer.arcanacraft.common.quom.Quom
import com.countrygamer.cgo.client.gui.GuiScreenWrapper
import com.countrygamer.cgo.client.gui.widget.Component
import com.countrygamer.cgo.common.lib.util.UtilRender

/**
 *
 *
 * @author CountryGamer
 */
class ComponentQuom(ownerGui: GuiScreenWrapper, val quom: Quom, val learned: Boolean)
		extends Component(ownerGui, quom.col, quom.row) {

	var numberOfParents: Int = 0

	// Default Constructor
	{

		var varQuom: Quom = this.quom.getParent()
		while (varQuom != null) {
			numberOfParents += 1
			varQuom = varQuom.getParent()

		}

	}
	// End Constructor

	override def draw(gui: GuiScreenWrapper, x: Int, y: Int, leftOffset: Int, rightOffset: Int,
			topOffset: Int, bottomOffset: Int): Unit = {
		UtilRender.bindResource(ACOptions.icons)
		UtilRender.drawTextureWithOffsets(this.ownerGui, x, y,
			if (this.numberOfParents > 0) 0 else 26,
			128 - (if (this.learned) 0 else 26), 26, 26,
			leftOffset, rightOffset, topOffset, bottomOffset
		)
		this.quom.draw(this.ownerGui, x + 5, y + 5,
			if (leftOffset > 0) leftOffset - 5 else 0,
			if (rightOffset - 5 > 0) rightOffset - 5 else 0,
			if (topOffset > 0) topOffset - 5 else 0,
			if (bottomOffset - 5 > 0) bottomOffset - 5 else 0
		)
	}

	override def onClick(): Unit = {
		if (this.learned) {
			// TODO this.ownerGui.mc.displayGuiScreen(new GuiQuomNotes(this.ownerGui, this.quom))
		}
	}

	override def onHover(hoverInfo: util.List[String]): Unit = {
		hoverInfo.add(this.quom.getName())
	}

}
