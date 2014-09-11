package com.countrygamer.arcanacraft.common.quom

import com.countrygamer.arcanacraft.common.ArcanaCraft
import com.countrygamer.arcanacraft.common.extended.ArcanePlayer
import com.countrygamer.arcanacraft.common.init.Quoms
import com.countrygamer.cgo.common.lib.util._
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.{MovingObjectPosition, ResourceLocation}
import net.minecraft.world.World

/**
 *
 *
 * @author CountryGamer
 */
class Quom(private var name: String, parent: Quom, var col: Int, var row: Int) {

	private var parentName: String = ""
	private var id: Int = 0
	private var icon: ResourceLocation = null

	// Default Constructor
	{
		if (parent != null) {
			this.parentName = parent.getName()

		}

		this.icon = new ResourceLocation(ArcanaCraft.pluginID,
			"textures/quoms/" + this.name + ".png")

		if (this.col < Quoms.minDisplayColumn) {
			Quoms.minDisplayColumn = this.col
		}

		if (this.row < Quoms.minDisplayRow) {
			Quoms.minDisplayRow = this.row
		}

		if (this.col > Quoms.maxDisplayColumn) {
			Quoms.maxDisplayColumn = this.col
		}

		if (this.row > Quoms.maxDisplayRow) {
			Quoms.maxDisplayRow = this.row
		}

		this.id = Quoms.registerQuom(this)

	}

	// End Constructor

	// Other Constructors
	def this(name: String, col: Int, row: Int) {
		this(name, null, col, row)

	}

	def this(name: String, parent: Quom) {
		this(name, parent, 0, 0)

	}

	def this(name: String) {
		this(name, null)

	}

	// End Constructors

	def getName(): String = {
		this.name
	}

	def getID(): Int = {
		this.id
	}

	def getParent(): Quom = {
		Quoms.getQuom(this.parentName)
	}

	// Client Side things

	@SideOnly(Side.CLIENT)
	def draw(gui: net.minecraft.client.gui.Gui, x: Int, y: Int): Unit = {
		this.draw(gui, x, y, 0, 0, 0, 0)
	}

	@SideOnly(Side.CLIENT)
	def draw(gui: net.minecraft.client.gui.Gui, x: Int, y: Int, leftOffset: Int, rightOffset: Int,
			topOffset: Int, bottomOffset: Int): Unit = {
		UtilRender.bindResource(this.icon)
		UtilRender.drawTextureWithOffsets(
			gui, x, y, 0, 0, 16, 16, leftOffset, rightOffset, topOffset, bottomOffset)

	}

	// End Client Side things

	def saveToNBT(tagCom: NBTTagCompound): Unit = {
		tagCom.setString("name", this.name)
		tagCom.setString("parentName", this.parentName)
		tagCom.setInteger("id", this.id)
		tagCom.setInteger("col", this.col)
		tagCom.setInteger("row", this.row)
		tagCom.setString("iconSource_dir", this.icon.getResourceDomain())
		tagCom.setString("iconSource_path", this.icon.getResourcePath())

	}

	def loadFromNBT(tagCom: NBTTagCompound): Unit = {
		this.name = tagCom.getString("name")
		this.parentName = tagCom.getString("parentName")
		this.id = tagCom.getInteger("id")
		this.col = tagCom.getInteger("col")
		this.row = tagCom.getInteger("row")
		this.icon = new ResourceLocation(tagCom.getString("iconSource_dir"),
			tagCom.getString("iconSource_path"))

	}

	override def equals(obj: scala.Any): Boolean = {
		obj match {
			case that: Quom =>
				this.id == that.getID() && this.name.equals(that.getName()) &&
						this.parentName.equals(that.getParent().getName())
			case _ =>
				false
		}
	}

	//

	final def onUse_do(player: EntityPlayer, arcanePlayer: ArcanePlayer, world: World,
			tier: Int): Unit = {
		val reachLength: Double = this.getReachLength(tier, player.capabilities.isCreativeMode)

		var usedOnEnity: Boolean = false
		var usedOnBlock: Boolean = false
		var used: Boolean = false

		val mop: MovingObjectPosition = Cursor.rayTrace(player, reachLength)

		if (mop != null && mop.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
			mop.entityHit match {
				case entity: EntityLivingBase =>
					usedOnEnity = this.onUseEntity(player, arcanePlayer, world,
						tier, mop.entityHit.asInstanceOf[EntityLivingBase])
				case _ =>
			}
		}

		if (!usedOnEnity) {
			val mopT: MovingObjectPositionTarget = Cursor.getBlockFromCursor(world, player, reachLength)
			if (mopT != null) {
				usedOnBlock = this
						.onUseBlock(player, arcanePlayer, world, mopT.x, mopT.y, mopT.z, mopT.side,
				            tier)
			}

			if (!usedOnBlock) {
				used = this.onUse(player, arcanePlayer, world, tier)
			}

		}

	}

	//

	def getReachLength(tier: Int, isCreative: Boolean): Double = {
		5.0D
	}

	def onUseEntity(player: EntityPlayer, arcanePlayer: ArcanePlayer, world: World,
			tier: Int, entity: EntityLivingBase): Boolean = {
		false
	}

	def onUseBlock(player: EntityPlayer, arcanePlayer: ArcanePlayer, world: World, x: Int, y: Int,
			z: Int, side: Int, tier: Int): Boolean = {
		false
	}

	def onUse(player: EntityPlayer, arcanePlayer: ArcanePlayer, world: World,
			tier: Int): Boolean = {
		false
	}

	//

	def addHoverInformation(arcanePlayer: ArcanePlayer, hoverInfo: java.util.List[String]): Unit = {
	}

}
