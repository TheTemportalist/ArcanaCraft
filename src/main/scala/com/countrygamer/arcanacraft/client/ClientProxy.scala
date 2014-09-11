package com.countrygamer.arcanacraft.client

import com.countrygamer.arcanacraft.client.gui.{GuiArcana, ArcaneOverlay}
import com.countrygamer.arcanacraft.common.{ACOptions, CommonProxy}
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World
import net.minecraftforge.common.MinecraftForge

/**
 *
 *
 * @author CountryGamer
 */
@SideOnly(Side.CLIENT)
class ClientProxy() extends CommonProxy {

	override def registerRender(): Unit = {
		MinecraftForge.EVENT_BUS.register(ArcaneOverlay)

	}

	override def getClientGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int,
			z: Int): AnyRef = {
		if (ID == ACOptions.guiArcana) {
			return new GuiArcana(player, if (player.isSneaking) true else false)
		}
		null
	}

}
