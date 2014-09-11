package com.countrygamer.arcanacraft.common.init

import java.util

import com.countrygamer.arcanacraft.common.quom.Quom

/**
 *
 *
 * @author CountryGamer
 */
object Quoms {

	val quomKeys: util.HashMap[String, Int] = new util.HashMap[String, Int]()
	private val quomRegistry: util.List[Quom] = new util.ArrayList[Quom]()

	/** Is the smallest column used to display a achievement on the GUI. */
	var minDisplayColumn: Int = 0
	/** Is the smallest row used to display a achievement on the GUI. */
	var minDisplayRow: Int = 0
	/** Is the biggest column used to display a achievement on the GUI. */
	var maxDisplayColumn: Int = 0
	/** Is the biggest row used to display a achievement on the GUI. */
	var maxDisplayRow: Int = 0

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	var dig: Quom = null
	var lightFire: Quom = null
	var furnace: Quom = null
	var extract: Quom = null
	var bind: Quom = null
	var teleport: Quom = null
	var fastTravel: Quom = null
	var flash: Quom = null
	var barrier: Quom = null
	var icePath: Quom = null
	var luminize: Quom = null
	var illuminate: Quom = null
	var data: Quom = null
	var connector: Quom = null
	var growth: Quom = null
	var tempest: Quom = null
	var filter: Quom = null

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	def register(): Unit = {

		val maxWidth: Int = 4

		var xy: Array[Int] = null
		var x: Int = 0
		var y: Int = 0

		this.dig = new Quom("Dig", x, y)

		xy = this.nextCoord(x, y, maxWidth)
		x = xy(0)
		y = xy(1)
		this.lightFire = new Quom("Light Fire", x, y)

		xy = this.nextCoord(x, y, maxWidth)
		x = xy(0)
		y = xy(1)
		this.furnace = new Quom("Furnace", x, y)

		xy = this.nextCoord(x, y, maxWidth)
		x = xy(0)
		y = xy(1)
		this.extract = new Quom("Rescindo", x, y)

		xy = this.nextCoord(x, y, maxWidth)
		x = xy(0)
		y = xy(1)
		this.bind = new Quom("Bind", x, y)

		xy = this.nextCoord(x, y, maxWidth)
		x = xy(0)
		y = xy(1)
		this.luminize = new Quom("Luminize", x, y)

		xy = this.nextCoord(x, y, maxWidth)
		x = xy(0)
		y = xy(1)
		this.fastTravel = new Quom("Evaporate", x, y)

		xy = this.nextCoord(x, y, maxWidth)
		x = xy(0)
		y = xy(1)
		this.flash = new Quom("Flash...", x, y)

		xy = this.nextCoord(x, y, maxWidth)
		x = xy(0)
		y = xy(1)
		this.barrier = new Quom("Barrier...", x, y)

		xy = this.nextCoord(x, y, maxWidth)
		x = xy(0)
		y = xy(1)
		this.icePath = new Quom("Ice Path...", x, y)

		xy = this.nextCoord(x, y, maxWidth)
		x = xy(0)
		y = xy(1)
		this.illuminate = new Quom("Illuminate...", x, y)

		xy = this.nextCoord(x, y, maxWidth)
		x = xy(0)
		y = xy(1)
		this.data = new Quom("Data", x, y)

		xy = this.nextCoord(x, y, maxWidth)
		x = xy(0)
		y = xy(1)
		this.connector = new Quom("Connector", x, y)

		xy = this.nextCoord(x, y, maxWidth)
		x = xy(0)
		y = xy(1)
		this.growth = new Quom("Growth", x, y)

		xy = this.nextCoord(x, y, maxWidth)
		x = xy(0)
		y = xy(1)
		this.tempest = new Quom("Tempest", x, y)

		xy = this.nextCoord(x, y, maxWidth)
		x = xy(0)
		y = xy(1)
		this.teleport = new Quom("Teleport", x, y)

		xy = this.nextCoord(x, y, maxWidth)
		x = xy(0)
		y = xy(1)
		this.filter = new Quom("Filter", x, y)

	}

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	def registerQuom(quom: Quom): Int = {
		val id: Int = Quoms.quomRegistry.size()
		Quoms.quomKeys.put(quom.getName(), id)
		Quoms.quomRegistry.add(quom)
		id
	}

	def getQuom(name: String): Quom = {
		if (Quoms.quomKeys.containsKey(name)) {
			return Quoms.quomRegistry.get(Quoms.quomKeys.get(name))
		}
		null
	}

	def getQuom(id: Int): Quom = {
		if (id > -1 && id < Quoms.getSize()) {
			return Quoms.quomRegistry.get(id)
		}
		null
	}

	def getSize(): Int = {
		this.quomRegistry.size()
	}

	private def nextCoord(lastX: Int, lastY: Int, maxW: Int): Array[Int] = {
		var x: Int = lastX + 2
		var y: Int = lastY
		if (x > maxW * 2) {
			x = 0
			y += 2
		}
		Array[Int](x, y)
	}

}
