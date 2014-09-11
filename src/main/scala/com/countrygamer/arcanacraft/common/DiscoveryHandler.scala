package com.countrygamer.arcanacraft.common

import com.countrygamer.arcanacraft.common.extended.ArcanePlayer
import com.countrygamer.cgo.wrapper.common.extended.ExtendedEntityHandler
import cpw.mods.fml.common.eventhandler.SubscribeEvent
import cpw.mods.fml.common.gameevent.PlayerEvent.{ItemCraftedEvent, ItemSmeltedEvent}
import net.minecraftforge.event.entity.player.PlayerInteractEvent

/**
 *
 *
 * @author CountryGamer
 */
object DiscoveryHandler {

	@SubscribeEvent
	def onInteract(event: PlayerInteractEvent): Unit = {

		val arcanePlayer: ArcanePlayer = ExtendedEntityHandler
				.getExtended(event.entityPlayer, classOf[ArcanePlayer]).asInstanceOf[ArcanePlayer]

		if (arcanePlayer.isArcaic()) {

		}

	}

	@SubscribeEvent
	def onCraft(event: ItemCraftedEvent): Unit = {

		val arcanePlayer: ArcanePlayer = ExtendedEntityHandler
				.getExtended(event.player, classOf[ArcanePlayer]).asInstanceOf[ArcanePlayer]

		if (arcanePlayer.isArcaic()) {

		}

	}

	@SubscribeEvent
	def onSmelt(event: ItemSmeltedEvent): Unit = {

		val arcanePlayer: ArcanePlayer = ExtendedEntityHandler
				.getExtended(event.player, classOf[ArcanePlayer]).asInstanceOf[ArcanePlayer]

		if (arcanePlayer.isArcaic()) {

		}

	}



}
