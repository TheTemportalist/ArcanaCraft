package com.countrygamer.arcanacraft.client.gui.configFactory

import com.countrygamer.arcanacraft.common.ArcanaCraft
import com.countrygamer.cgo.wrapper.client.gui.configFactory.GuiConfigWrapper
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.client.gui.GuiScreen

/**
 *
 *
 * @author CountryGamer
 */
@SideOnly(Side.CLIENT)
class GuiConfig(guiScreen: GuiScreen)
		extends GuiConfigWrapper(guiScreen, ArcanaCraft.pluginID, ArcanaCraft) {

}
