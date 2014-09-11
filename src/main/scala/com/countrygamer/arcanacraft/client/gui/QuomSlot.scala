package com.countrygamer.arcanacraft.client.gui

import com.countrygamer.arcanacraft.common.ACOptions
import com.countrygamer.arcanacraft.common.init.Quoms
import com.countrygamer.arcanacraft.common.quom.Quom
import com.countrygamer.cgo.client.gui.GuiScreenWrapper
import com.countrygamer.cgo.common.lib.util.UtilRender
import net.minecraft.util.ResourceLocation

/**
 *
 *
 * @author CountryGamer
 */
class QuomSlot(val x: Int, val y: Int, val u: Int, val v: Int, val reLoc: ResourceLocation) {

	var quomID: Int = -1

	def this(x: Int, y: Int) {
		this(x, y, 0, 20, ACOptions.icons)

	}

	def draw(gui: GuiScreenWrapper): Unit = {
		UtilRender.bindResource(this.reLoc)
		gui.drawTexturedModalRect(this.x, this.y, this.u, this.v, 18, 18)
		if (this.getQuom() != null) {
			this.getQuom().draw(gui, this.x + 1, this.y + 1)
		}

	}

	def getQuom(): Quom = {
		Quoms.getQuom(this.quomID)
	}

	def setQuom(quom: Quom): Unit = {
		if (quom == null) {
			this.setQuom(-1)
		}
		else {
			this.setQuom(quom.getID())
		}
	}

	def setQuom(id: Int): Unit = {
		this.quomID = id
	}

	def isMouseWithin(gui: GuiScreenWrapper, mouseX: Int, mouseY: Int): Boolean = {
		if (gui.isMouseInArea(this.x, this.y, 18, 18, mouseX, mouseY)) {
			true
		}
		else {
			false
		}
	}

}
