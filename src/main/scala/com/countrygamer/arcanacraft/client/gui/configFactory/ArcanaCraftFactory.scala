package com.countrygamer.arcanacraft.client.gui.configFactory

import com.countrygamer.arcanacraft.common.ACOptions
import com.countrygamer.cgo.wrapper.client.gui.configFactory.GuiFactoryWrapper
import cpw.mods.fml.relauncher.{Side, SideOnly}

/**
 *
 *
 * @author CountryGamer
 */
@SideOnly(Side.CLIENT)
class ArcanaCraftFactory() extends GuiFactoryWrapper(ACOptions) {
}
