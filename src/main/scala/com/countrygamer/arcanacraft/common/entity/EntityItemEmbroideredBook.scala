package com.countrygamer.arcanacraft.common.entity

import java.util.Random

import com.countrygamer.arcanacraft.common.ArcanaCraft
import com.countrygamer.arcanacraft.common.init.ACItems
import com.countrygamer.cgo.common.lib.util.UtilDrops
import net.minecraft.entity.item.EntityItem
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraft.util.MathHelper

/**
 *
 *
 * @author CountryGamer
 */
class EntityItemEmbroideredBook(entityItem: EntityItem)
		extends EntityItem(entityItem.worldObj, 0.0D, 0.0D, 0.0D) {

	override def onUpdate(): Unit = {
		super.onUpdate()
		
		val x: Int = MathHelper.floor_double(this.posX)
		val y: Int = MathHelper.floor_double(this.posY)
		val z: Int = MathHelper.floor_double(this.posZ)
		
		if (this.worldObj.getBlock(x, y, z) == Blocks.fire) {
			this.worldObj.setBlockToAir(x, y, z)
		}
		if (this.isBurning) {
			this.extinguish()
		}
		
		if (this.worldObj.getBlock(x - 1, y + 0, z + 0) == Blocks.fire &&
				this.worldObj.getBlock(x + 1, y + 0, z + 0) == Blocks.fire &&
				this.worldObj.getBlock(x + 0, y + 0, z - 1) == Blocks.fire &&
				this.worldObj.getBlock(x + 0, y + 0, z + 1) == Blocks.fire &&
				this.worldObj.getBlock(x - 1, y + 0, z - 1) == Blocks.fire &&
				this.worldObj.getBlock(x - 1, y + 0, z + 1) == Blocks.fire &&
				this.worldObj.getBlock(x + 1, y + 0, z - 1) == Blocks.fire &&
				this.worldObj.getBlock(x + 1, y + 0, z + 1) == Blocks.fire) {
			val itemStack: ItemStack = this.getEntityItem

			// conversion sound
			//world.playSoundAtEntity(, ArcanaCraft.pluginID + ":creepy1", 1.0F, 1.0F)
			this.worldObj
					.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, ArcanaCraft.pluginID + ":creepy1",
			            5.0F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F)

			// check for more than 1 book
			if (itemStack.stackSize > 1) {
				UtilDrops.spawnItemStack(this.worldObj, this.posX, this.posY, this.posZ,
					new ItemStack(ACItems.arcana), new Random(), 10)
				itemStack.stackSize -= 1
				this.setEntityItemStack(itemStack)
			}
			else {
				this.setEntityItemStack(new ItemStack(ACItems.arcana))
			}

			this.worldObj.setBlockToAir(x - 1, y + 0, z + 0)
			this.worldObj.setBlockToAir(x + 1, y + 0, z + 0)
			this.worldObj.setBlockToAir(x + 0, y + 0, z - 1)
			this.worldObj.setBlockToAir(x + 0, y + 0, z + 1)
			this.worldObj.setBlockToAir(x - 1, y + 0, z - 1)
			this.worldObj.setBlockToAir(x - 1, y + 0, z + 1)
			this.worldObj.setBlockToAir(x + 1, y + 0, z - 1)
			this.worldObj.setBlockToAir(x + 1, y + 0, z + 1)

		}

	}

}
