package com.countrygamer.arcanacraft.common.extended

import java.util

import com.countrygamer.arcanacraft.common.ArcanaCraft
import com.countrygamer.arcanacraft.common.init.{ACItems, Quoms}
import com.countrygamer.arcanacraft.common.quom.Quom
import com.countrygamer.cgo.common.lib.LogHelper
import com.countrygamer.cgo.wrapper.common.extended.ExtendedEntity
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.nbt.{NBTTagList, NBTTagCompound}
import net.minecraft.world.World

/**
 *
 *
 * @author CountryGamer
 */
class ArcanePlayer(player: EntityPlayer) extends ExtendedEntity(player) {

	private var isActive: Boolean = false

	private var discoveredQuoms: util.HashMap[Int, Int] = new util.HashMap[Int, Int]()
	private var hotBar: Array[Int] = null
	private var hotbarIndex: Int = 0

	private var magicData: util.HashMap[Int, NBTTagCompound] = new
					util.HashMap[Int, NBTTagCompound]()

	// Default Constructor
	{
		this.isActive = false
		for (i <- 0 until Quoms.getSize()) {
			this.discoveredQuoms.put(i, 1)
		}
		this.hotBar = Array[Int](-1, -1, -1, -1, -1, -1, -1, -1, -1)
		this.hotbarIndex = 0

	}

	// End Constructor

	override def init(entity: Entity, world: World): Unit = {
		for (i <- 0 until Quoms.getSize()) {
			if (!this.discoveredQuoms.containsKey(i)) {
				this.discoveredQuoms.put(i, 1)
			}
		}
	}

	override def saveNBTData(tagCom: NBTTagCompound): Unit = {
		tagCom.setBoolean("isActive", this.isActive)

		var list: NBTTagList = new NBTTagList

		val discoveredIter: util.Iterator[Int] = this.discoveredQuoms.keySet().iterator()
		while (discoveredIter.hasNext) {
			val quomID: Int = discoveredIter.next()
			val tag: NBTTagCompound = new NBTTagCompound
			tag.setInteger("id", quomID)
			tag.setInteger("data", this.discoveredQuoms.get(quomID))
			list.appendTag(tag)

		}
		tagCom.setTag("discoveredQuoms", list)
		list = null

		list = new NBTTagList
		for (slotID <- 0 until this.hotBar.length) {
			val tag: NBTTagCompound = new NBTTagCompound
			tag.setInteger("slotID", slotID)
			tag.setInteger("quomID", this.hotBar(slotID))
			list.appendTag(tag)
		}
		tagCom.setTag("hotBar", list)
		list = null

		tagCom.setInteger("hotbarIndex", this.hotbarIndex)

		list = new NBTTagList
		val magicDataIter: util.Iterator[Int] = this.magicData.keySet().iterator()
		while (magicDataIter.hasNext) {
			val quomID: Int = magicDataIter.next()
			val tag: NBTTagCompound = new NBTTagCompound
			tag.setInteger("quomID", quomID)
			tag.setTag("data", this.magicData.get(quomID))
			list.appendTag(tag)
		}
		tagCom.setTag("magicData", list)
		list = null

	}

	override def loadNBTData(tagCom: NBTTagCompound): Unit = {
		this.isActive = tagCom.getBoolean("isActive")

		var list: NBTTagList = tagCom.getTagList("discoveredQuoms", 10)

		this.discoveredQuoms = new util.HashMap[Int, Int]()
		for (i <- 0 until Quoms.getSize()) {
			this.discoveredQuoms.put(i, -1)
		}
		for (i <- 0 until list.tagCount()) {
			val tag: NBTTagCompound = list.getCompoundTagAt(i)
			this.discoveredQuoms.put(tag.getInteger("id"), tag.getInteger("data"))
		}
		list = null

		list = tagCom.getTagList("hotBar", 10)
		this.hotBar = Array[Int](-1, -1, -1, -1, -1, -1, -1, -1, -1)
		for (i <- 0 until list.tagCount()) {
			val tag: NBTTagCompound = list.getCompoundTagAt(i)
			this.hotBar(tag.getInteger("slotID")) = tag.getInteger("quomID")
		}
		list = null

		this.hotbarIndex = tagCom.getInteger("hotbarIndex")

		list = tagCom.getTagList("magicData", 10)
		this.magicData = new util.HashMap[Int, NBTTagCompound]()
		for (i <- 0 until list.tagCount()) {
			val tag: NBTTagCompound = list.getCompoundTagAt(i)
			this.magicData.put(tag.getInteger("quomID"), tag.getCompoundTag("data"))
		}
		list = null

	}

	def setArcaic(value: Boolean): Unit = {
		this.isActive = value
		this.syncEntity()

	}

	def isArcaic(): Boolean = {
		this.isActive
	}

	def changeCurrentQuom(direction: Int): Unit = {
		var unit: Int = direction

		// Make sure that direction is either -1 or 1
		if (Math.abs(unit) > 1) {
			unit /= Math.abs(unit)
		}

		do {
			this.hotbarIndex += unit

			if (this.hotbarIndex < 0) {
				this.hotbarIndex = 8
			}
			if (this.hotbarIndex >= this.hotBar.length) {
				this.hotbarIndex = 0
			}

		}
		while (this.hotBar(this.hotbarIndex) == -1)

		this.syncEntity()
	}

	def getCurrentIndex(): Int = {
		this.hotbarIndex
	}

	def getCurrentQuom(): Quom = {
		if (this.hotBar(this.hotbarIndex) > -1)
			Quoms.getQuom(this.hotBar(this.hotbarIndex))
		else
			null
	}

	def selectSlot(slot: Int): Unit = {
		if (slot >= 0 && slot <= 8) {
			this.hotbarIndex = slot
			this.syncEntity()
		}
	}

	def getCastingTier(itemStack: ItemStack, quom: Quom): Int = {
		var baseTier: Int = -1
		if (this.hasDiscoveredQuom(quom)) {
			baseTier = 0
		}
		if (this.hasLearnedQuom(quom)) {
			baseTier = 1
		}

		var itemBonusTier: Int = 0
		if (itemStack == null) {
			itemBonusTier = 0
		}
		else if (itemStack.getItem == ACItems.embroideredBook) {
			itemBonusTier = 1
		}

		baseTier + itemBonusTier
	}

	def hasDiscoveredQuom(quom: Quom): Boolean = {
		this.hasDiscoveredQuom(quom.getID())
	}

	def hasDiscoveredQuom(id: Int): Boolean = {
		if (this.discoveredQuoms.containsKey(id)) {
			return this.discoveredQuoms.get(id) >= 0
		}
		else {
			LogHelper.error(ArcanaCraft.pluginName, "ERROR, Missing quom with ID " + id)
		}
		false
	}

	def hasLearnedQuom(quom: Quom): Boolean = {
		this.hasLearnedQuom(quom.getID())
	}

	def hasLearnedQuom(id: Int): Boolean = {
		if (this.hasDiscoveredQuom(id)) {
			return this.discoveredQuoms.get(id) >= 1
		}
		false
	}

	def discoverQuom(quom: Quom): Unit = {
		if (!this.hasDiscoveredQuom(quom)) {
			this.discoveredQuoms.put(quom.getID(), 0)
			this.syncEntity()
		}
	}

	def learnQuom(quom: Quom): Unit = {
		if (this.hasDiscoveredQuom(quom)) {
			this.discoveredQuoms.put(quom.getID(), 1)
			this.syncEntity()
		}
	}

	def isHoldingValidCaster(): Boolean = {
		val itemStack: ItemStack = this.player.getHeldItem

		if (itemStack == null) {
			return true
		}
		else if (itemStack.getItem == ACItems.embroideredBook) {
			return true
		}

		false
	}

	def getHotBar(): Array[Int] = {
		this.hotBar
	}

	def updateHotbar(updated: Array[Int]): Unit = {
		for (i <- 0 until 9) {
			this.hotBar(i) = if (i < updated.length) updated(i) else -1
		}
		this.syncEntity()
	}

	def getDiscoveredQuoms(): util.HashMap[Int, Int] = {
		this.discoveredQuoms
	}

}
