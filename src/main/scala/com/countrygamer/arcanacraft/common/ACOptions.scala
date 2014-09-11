package com.countrygamer.arcanacraft.common

import com.countrygamer.cgo.common.lib.util.UtilRender
import com.countrygamer.cgo.wrapper.common.registries.OptionRegister
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.client.gui.GuiScreen
import net.minecraft.util.ResourceLocation

/**
 *
 *
 * @author CountryGamer
 */
object ACOptions extends OptionRegister {

	val guiArcana: Int = 0

	val icons: ResourceLocation = UtilRender
			.getResource(ArcanaCraft.pluginID, "gui/", "GuiIcons")

	override def register(): Unit = {



	}

	@SideOnly(Side.CLIENT)
	override def getGuiConfigClass: Class[_ <: GuiScreen] = {
		classOf[com.countrygamer.arcanacraft.client.gui.configFactory.GuiConfig]
	}

}
