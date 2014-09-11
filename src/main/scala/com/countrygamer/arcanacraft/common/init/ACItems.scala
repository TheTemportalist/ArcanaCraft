package com.countrygamer.arcanacraft.common.init

import com.countrygamer.arcanacraft.common.item.arcana.{ItemArcana, ItemEmbroideredBook}
import com.countrygamer.arcanacraft.common.item.pendant.ItemPendant
import com.countrygamer.cgo.wrapper.common.registries.ItemRegister
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.init.Items
import net.minecraft.item.{Item, ItemStack}

/**
 *
 *
 * @author CountryGamer
 */
object ACItems extends ItemRegister {

	var embroideredBook: Item = null
	var arcana: Item = null

	var pendant: Item = null

	override def register(): Unit = {

		ACItems.embroideredBook = new ItemEmbroideredBook("Embroidered Book")
		ACItems.arcana = new ItemArcana("Arcana")

		ACItems.pendant = new ItemPendant("Pendant")

	}

	override def registerItemsPostBlock(): Unit = {

	}

	override def registerCrafting(): Unit = {

		GameRegistry.addRecipe(new ItemStack(ACItems.embroideredBook),
			"glg", "ppp", "glg",
			Character.valueOf('g'), new ItemStack(Items.gold_nugget),
			Character.valueOf('l'), new ItemStack(Items.leather),
			Character.valueOf('p'), new ItemStack(Items.paper)
		)

	}

	override def registerSmelting(): Unit = {

	}

	override def registerOther(): Unit = {

	}

}
