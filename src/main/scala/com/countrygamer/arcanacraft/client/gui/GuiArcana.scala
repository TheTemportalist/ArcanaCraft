package com.countrygamer.arcanacraft.client.gui

import java.util
import java.util.List

import com.countrygamer.arcanacraft.common.ArcanaCraft
import com.countrygamer.arcanacraft.common.extended.ArcanePlayer
import com.countrygamer.cgo.client.gui.GuiScreenWrapper
import com.countrygamer.cgo.common.lib.util.UtilRender
import com.countrygamer.cgo.wrapper.common.extended.ExtendedEntityHandler
import net.minecraft.client.gui.GuiButton
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.ResourceLocation
import net.minecraftforge.common.IExtendedEntityProperties

/**
 *
 *
 * @author CountryGamer
 */
class GuiArcana(player: EntityPlayer, revertPages: Boolean) extends GuiScreenWrapper(200, 200) {

	final val frameForTree: ResourceLocation =
		UtilRender.getResource(ArcanaCraft.pluginID, "gui/", "Scroll_Frame")

	private var arcanePlayer: ArcanePlayer = null

	private var currentPage: String = "Info"
	private var infoTab, quomiTab, skillTab: GuiButton = null
	private val pages: util.HashMap[String, Page] = new util.HashMap[String, Page]()

	// Default Constructor
	{
		this.setupGui("", UtilRender.getResource(ArcanaCraft.pluginID, "gui/", "Scroll"))

		val props: IExtendedEntityProperties = ExtendedEntityHandler
				.getExtended(player, classOf[ArcanePlayer])
		if (props != null) {
			this.arcanePlayer = props.asInstanceOf[ArcanePlayer]
		}

		if (!revertPages) {
			//this.currentPage = this.arcanePlayer.getCurrentArcanaPage()
		}

	}

	// End Constructor

	override def initGui(): Unit = {
		super.initGui()

		var bID: Int = -1
		bID += 1
		this.infoTab =
				new GuiButton(bID, this.guiLeft + this.xSize - 10, this.guiTop + 5, 20, 20, "")
		this.buttonList.asInstanceOf[List[GuiButton]].add(this.infoTab)
		bID += 1
		this.quomiTab =
				new GuiButton(bID, this.guiLeft + this.xSize - 10, this.guiTop + 35, 20, 20, "")
		this.buttonList.asInstanceOf[List[GuiButton]].add(this.quomiTab)
		bID += 1
		this.skillTab =
				new GuiButton(bID, this.guiLeft + this.xSize - 10, this.guiTop + 65, 20, 20, "")
		this.buttonList.asInstanceOf[List[GuiButton]].add(this.skillTab)

		this.initPages()

	}

	def initPages(): Unit = {

		this.pages.put("Info", new Page(this) {

			override def init(): Unit = {

			}

			override def drawForeground(): Unit = {
				this.drawTitle("ArcanaCraft; The Arcana")
			}

			override def drawBackground(mouseX: Int, mouseY: Int,
					renderPartialTicks: Float): Unit = {

			}

		})

		this.pages.put("Quomi", new PageQuomi(this, this.arcanePlayer))

		this.pages.put("Skill Tree", new PageTree(this, this.arcanePlayer))

		val iterator: util.Iterator[String] = this.pages.keySet().iterator()
		while (iterator.hasNext) {
			this.pages.get(iterator.next()).init()

		}

	}

	override def updateScreen(): Unit = {
		super.updateScreen()

		if (this.pages.containsKey(this.currentPage)) {
			this.pages.get(this.currentPage).onUpdate()

		}

	}

	override def actionPerformed(button: GuiButton): Unit = {
		if (button.id == this.infoTab.id) {
			this.currentPage = "Info"
		}
		else if (button.id == this.quomiTab.id) {
			this.currentPage = "Quomi"
		}
		else if (button.id == this.skillTab.id) {
			this.currentPage = "Skill Tree"
		}

	}

	override def mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int): Unit = {
		super.mouseClicked(mouseX, mouseY, mouseButton)
		if (this.pages.containsKey(this.currentPage)) {
			this.pages.get(this.currentPage).onMouseClick(mouseX, mouseY, mouseButton)
		}

	}

	override protected def drawGuiForegroundLayer(mouseX: Int, mouseY: Int,
			renderPartialTicks: Float): Unit = {
		super.drawGuiForegroundLayer(mouseX, mouseY, renderPartialTicks)

		if (this.pages.containsKey(this.currentPage)) {
			this.pages.get(this.currentPage).drawForeground()
		}
	}

	override protected def drawGuiBackgroundLayer(mouseX: Int, mouseY: Int,
			renderPartialTicks: Float): Unit = {

		if (this.pages.containsKey(this.currentPage) && this.currentPage.equals("Skill Tree")) {
			this.pages.get(this.currentPage).drawBackground(mouseX, mouseY, renderPartialTicks)
		}

		super.drawGuiBackgroundLayer(mouseX, mouseY, renderPartialTicks)

		if (this.pages.containsKey(this.currentPage) && !this.currentPage.equals("Skill Tree")) {
			this.pages.get(this.currentPage).drawBackground(mouseX, mouseY, renderPartialTicks)
		}

	}

	override def getBackgound(): ResourceLocation = {
		if (this.currentPage.equals("Skill Tree"))
			this.frameForTree
		else
			super.getBackgound()
	}

	override protected def addInformationOnHover(mouseX: Int, mouseY: Int,
			renderPartialTicks: Float, hoverInfo: List[String]): Unit = {
		if (this.pages.containsKey(this.currentPage)) {
			this.pages.get(this.currentPage).addHoverInformation(mouseX, mouseY, hoverInfo)
		}

	}

}
