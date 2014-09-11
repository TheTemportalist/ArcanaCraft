package com.countrygamer.arcanacraft.common.item.arcana

import com.countrygamer.arcanacraft.common.{ACOptions, ArcanaCraft}
import com.countrygamer.arcanacraft.common.extended.ArcanePlayer
import com.countrygamer.arcanacraft.common.item.ItemArcanaCraft
import com.countrygamer.cgo.wrapper.common.extended.ExtendedEntityHandler
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.MathHelper
import net.minecraft.world.World

/**
 *
 *
 * @author CountryGamer
 */
class ItemArcana(name: String) extends ItemArcanaCraft(name) {

	// Default Constructor
	{

	}

	// End Constructor
	/**
	 * Called whenever this item is equipped and the right mouse button is pressed.
	 *
	 * @param itemStack
	 * @param world
	 * @param player
	 * @return
	 */
	override def onItemRightClick(itemStack: ItemStack, world: World,
			player: EntityPlayer): ItemStack = {

		val arcanePlayer: ArcanePlayer = ExtendedEntityHandler
				.getExtended(player, classOf[ArcanePlayer]).asInstanceOf[ArcanePlayer]

		if (!arcanePlayer.isArcaic()) {
			arcanePlayer.setArcaic(value = true)
		}

		player.openGui(ArcanaCraft, ACOptions.guiArcana, world,
			MathHelper.floor_double(player.posX),
			MathHelper.floor_double(player.posY),
			MathHelper.floor_double(player.posZ)
		)

		itemStack
	}

}
