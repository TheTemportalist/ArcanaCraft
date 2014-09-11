package com.countrygamer.arcanacraft.common.item.arcana

import com.countrygamer.arcanacraft.common.entity.EntityItemEmbroideredBook
import com.countrygamer.arcanacraft.common.item.ItemArcanaCraft
import net.minecraft.entity.Entity
import net.minecraft.entity.item.EntityItem
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.world.World

/**
 *
 *
 * @author CountryGamer
 */
class ItemEmbroideredBook(name: String) extends ItemArcanaCraft(name) {

	// Default Constructor
	{

	}

	// End Constructor

	override def hasCustomEntity(stack: ItemStack): Boolean = {
		true
	}

	override def createEntity(world: World, entity: Entity, itemstack: ItemStack): Entity = {
		entity match {
			case entityItem: EntityItem =>
				val ent: EntityItemEmbroideredBook = new EntityItemEmbroideredBook(entityItem)
				ent.copyLocationAndAnglesFrom(entityItem)
				val tagCom: NBTTagCompound = new NBTTagCompound
				entityItem.writeToNBT(tagCom)
				ent.readFromNBT(tagCom)
				ent.delayBeforeCanPickup = 40
				ent
			case _ =>
				null
		}
	}

}
