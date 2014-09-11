package com.countrygamer.arcanacraft.client.gui

import java.util

import scala.util.control.Breaks

import com.countrygamer.arcanacraft.common.ACOptions
import com.countrygamer.arcanacraft.common.extended.ArcanePlayer
import com.countrygamer.arcanacraft.common.init.Quoms
import com.countrygamer.arcanacraft.common.quom.Quom
import com.countrygamer.cgo.client.gui.GuiScreenWrapper
import com.countrygamer.cgo.common.lib.util.UtilRender

/**
 *
 *
 * @author CountryGamer
 */
class PageQuomi(ownerGui: GuiScreenWrapper, val arcanePlayer: ArcanePlayer) extends Page(ownerGui) {

	var totalSlots: Int = 0
	var selected: Int = arcanePlayer.getCurrentIndex()
	var quomSlots: Array[QuomSlot] = null

	val hotBarX: Int = this.ownerGui.getGuiLeft() + 12
	val hotBarY: Int = this.ownerGui.getGuiTop() + 28
	val listX: Int = hotBarX
	val listY: Int = hotBarY + 18 + 2

	// The far most right a slot's x pos can be
	val rightXBoundry: Int = this.hotBarX + (8 * 18)

	// Default Constructor
	{
		this.totalSlots = 9 + Quoms.getSize()
		this.quomSlots = new Array[QuomSlot](this.totalSlots)

	}

	// End Constructor

	override def init(): Unit = {

		val quomHotBar: Array[Int] = this.arcanePlayer.getHotBar()
		for (i <- 0 until quomHotBar.length) {
			this.quomSlots(i) = new QuomSlot(this.hotBarX + (i * 18), this.hotBarY)
			this.quomSlots(i).setQuom(quomHotBar(i))
		}

		var x1: Int = this.listX
		var y1: Int = this.listY
		for (quomID <- 0 until Quoms.getSize()) {
			if (x1 > rightXBoundry) {
				x1 = this.listX
				y1 += 18
			}

			this.quomSlots(quomID + 9) = new QuomSlot(x1, y1)
			if (this.arcanePlayer.hasDiscoveredQuom(quomID)) {
				this.quomSlots(quomID + 9).setQuom(quomID)
			}

			x1 += 18
		}

	}

	override def drawForeground(): Unit = {
		this.drawTitle("My Quomi")

	}

	override def drawBackground(mouseX: Int, mouseY: Int, renderPartialTicks: Float): Unit = {

		for (i <- 0 until this.quomSlots.length) {
			if (this.quomSlots(i) != null) {
				this.quomSlots(i).draw(this.ownerGui)
			}
		}

		UtilRender.bindResource(ACOptions.icons)
		this.ownerGui.drawTexturedModalRect(
			this.hotBarX + (this.selected * 18), this.hotBarY, 18, 20, 18, 18
		)

	}

	override def onMouseClick(mouseX: Int, mouseY: Int, mouseButton: Int): Unit = {

		var hotbar_has_changed: Boolean = false
		val clicked_on_hotbar: Boolean =
			this.hotBarX <= mouseX && mouseX <= this.rightXBoundry + 18 &&
			this.hotBarY <= mouseY && mouseY <= this.hotBarY + 18

		val slotLoop: Breaks = new Breaks()
		slotLoop.breakable {
			for (i <- 0 until this.quomSlots.length) {
				val slot: QuomSlot = this.quomSlots(i)

				if (slot != null && slot.isMouseWithin(this.ownerGui, mouseX, mouseY)) {
					if (clicked_on_hotbar) {
						if (mouseButton == 1) {
							this.quomSlots(i).setQuom(null)
						}
						else {
							this.selected = i
							this.arcanePlayer.selectSlot(this.selected)
						}
						hotbar_has_changed = true
					}
					else {
						if (mouseButton == 0) {
							this.quomSlots(this.selected).setQuom(slot.getQuom())
							hotbar_has_changed = true
						}
					}

					slotLoop.break()

				}
			}
		}

		if (hotbar_has_changed) {
			val updatedHotBar: Array[Int] = new Array[Int](9)
			for (i <- 0 until 9) {
				val quom: Quom = this.quomSlots(i).getQuom()
				updatedHotBar(i) = if (quom == null) -1 else quom.getID()
			}
			this.arcanePlayer.updateHotbar(updatedHotBar)
		}

	}

	override def addHoverInformation(mouseX: Int, mouseY: Int,
			hoverInfo: util.List[String]): Unit = {
		val slotLoop: Breaks = new Breaks
		slotLoop.breakable {
			for (i <- 0 until this.quomSlots.length) {
				if (this.quomSlots(i) != null &&
						this.quomSlots(i).isMouseWithin(this.ownerGui, mouseX, mouseY) &&
						this.quomSlots(i).getQuom() != null) {
					hoverInfo.add(this.quomSlots(i).getQuom().getName())
					this.quomSlots(i).getQuom().addHoverInformation(this.arcanePlayer, hoverInfo)
				}
			}
		}
	}

}
