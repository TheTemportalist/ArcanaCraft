package com.countrygamer.arcanacraft.common.item.pendant

import baubles.api.{BaubleType, IBauble}
import com.countrygamer.arcanacraft.common.item.ItemArcanaCraft
import net.minecraft.entity.EntityLivingBase
import net.minecraft.item.ItemStack

/**
 *
 *
 * @author CountryGamer
 */
class ItemPendant(name: String) extends ItemArcanaCraft(name) with IBauble {

	// Default Constructor
	{

	}
	// End Constructor

	override def getBaubleType(itemstack: ItemStack): BaubleType = {
		BaubleType.AMULET
	}

	override def onWornTick(itemstack: ItemStack, player: EntityLivingBase): Unit = {
	}

	override def canUnequip(itemstack: ItemStack, player: EntityLivingBase): Boolean = {
		true
	}

	override def onUnequipped(itemstack: ItemStack, player: EntityLivingBase): Unit = {
	}

	override def onEquipped(itemstack: ItemStack, player: EntityLivingBase): Unit = {
	}

	override def canEquip(itemstack: ItemStack, player: EntityLivingBase): Boolean = {
		true
	}

}
