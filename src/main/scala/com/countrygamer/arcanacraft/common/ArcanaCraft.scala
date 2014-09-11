package com.countrygamer.arcanacraft.common

import com.countrygamer.arcanacraft.client.KeyHandler
import com.countrygamer.arcanacraft.common.extended.ArcanePlayer
import com.countrygamer.arcanacraft.common.init.{Quoms, ACBlocks, ACItems}
import com.countrygamer.arcanacraft.common.network.PacketKeyPress
import com.countrygamer.cgo.common.RegisterHelper
import com.countrygamer.cgo.wrapper.common.PluginWrapper
import cpw.mods.fml.common.event.{FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent}
import cpw.mods.fml.common.{FMLCommonHandler, Mod, SidedProxy}
import cpw.mods.fml.relauncher.Side

/**
 *
 *
 * @author CountryGamer
 */
@Mod(modid = ArcanaCraft.pluginID, name = ArcanaCraft.pluginName, version = "@PLUGIN_VERSION@",
	guiFactory = "com.countrygamer.arcanacraft.client.gui.configFactory.ArcanaCraftFactory",
	modLanguage = "scala",
	dependencies = "required-after:Forge@[10.13,);required-after:cgo@[3.0.2,);required-after:Baubles@[1.0.1.3,)"
)
object ArcanaCraft extends PluginWrapper {

	final val pluginID = "arcanacraft"
	final val pluginName = "ArcanaCraft"

	@SidedProxy(serverSide = "com.countrygamer.arcanacraft.common.CommonProxy",
		clientSide = "com.countrygamer.arcanacraft.client.ClientProxy")
	var proxy: CommonProxy = null

	@Mod.EventHandler
	def preInit(event: FMLPreInitializationEvent): Unit = {
		super.preInitialize(this.pluginID, this.pluginName, event, proxy, ACOptions, ACItems,
			ACBlocks)

		RegisterHelper.registerExtendedPlayer("ArcanaPlayer", classOf[ArcanePlayer], deathPersistance = true)

		RegisterHelper.registerPacketHandler(this.pluginID, classOf[PacketKeyPress])

		RegisterHelper.registerHandler(DiscoveryHandler, null)

		Quoms.register()

		if (event.getSide == Side.CLIENT) {
			FMLCommonHandler.instance().bus().register(KeyHandler)
		}

	}

	@Mod.EventHandler
	def init(event: FMLInitializationEvent): Unit = {
		super.initialize(event)

	}

	@Mod.EventHandler
	def postInit(event: FMLPostInitializationEvent): Unit = {
		super.postInitialize(event)

	}

}
