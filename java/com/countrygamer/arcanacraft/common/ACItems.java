package com.countrygamer.arcanacraft.common;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;

import com.countrygamer.arcanacraft.common.item.ItemArcana;
import com.countrygamer.arcanacraft.common.item.ItemEmbroideredBook;
import com.countrygamer.arcanacraft.common.item.ItemEnchantedFluxBucket;
import com.countrygamer.core.Base.Plugin.PluginItemRegistry;
import com.countrygamer.core.Base.common.item.ItemBase;
import com.countrygamer.countrygamercore.common.Core;

import cpw.mods.fml.common.registry.GameRegistry;

public class ACItems implements PluginItemRegistry {
	
	public static Item embroideredBook;
	public static ItemStack embroideredBook_Stack;
	public static Item arcana;
	public static ItemStack arcana_Stack;
	public static Item arcaneFocus;
	public static ItemStack arcanaFocus_Stack;
	public static Item arcaneStave;
	public static ItemStack arcanaStave_Stack;
	public static ItemStack arcanaStave_Enchanted_Stack;
	public static Item enchantedFluxBucket;
	
	@Override
	public void registerItems() {
		ACItems.embroideredBook = new ItemEmbroideredBook(ArcanaCraft.pluginID,
				"Embroidered Book");
		ACItems.embroideredBook_Stack = new ItemStack(ACItems.embroideredBook, 1, 0);
		Core.addItemToTab(ACItems.embroideredBook);
		
		ACItems.arcana = new ItemArcana(ArcanaCraft.pluginID, "The Arcana");
		ACItems.arcana_Stack = new ItemStack(ACItems.arcana, 1, 0);
		Core.addItemToTab(ACItems.arcana);
		
		ACItems.arcaneFocus = new ItemBase(ArcanaCraft.pluginID, "Arcane Focus");
		ACItems.arcanaFocus_Stack = new ItemStack(ACItems.arcaneFocus, 1, 0);
		Core.addItemToTab(ACItems.arcaneFocus);
		
		ACItems.arcaneStave = new ItemBase(ArcanaCraft.pluginID, "Arcane Stave");
		ACItems.arcanaStave_Stack = new ItemStack(ACItems.arcaneStave, 1, 0);
		// TODO tag compound properties
		ACItems.arcanaStave_Enchanted_Stack = new ItemStack(ACItems.arcaneStave, 1,
				0);
		// TODO tag compound properties
		Core.addItemToTab(ACItems.arcaneStave);
		
	}
	
	public void registerItemsPostBlock() {
		ACItems.enchantedFluxBucket = new ItemEnchantedFluxBucket(
				ArcanaCraft.pluginID, ACBlocks.enchantedFluxBlock);
		GameRegistry.registerItem(ACItems.enchantedFluxBucket,
				"Enchanted Flux Bucket");
		FluidContainerRegistry.registerFluidContainer(
				FluidRegistry.getFluidStack("Enchanted Flux".toLowerCase(),
						FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(
						ACItems.enchantedFluxBucket), new ItemStack(Items.bucket));
		ArcanaCraft.buckets.put(ACBlocks.enchantedFluxBlock,
				ACItems.enchantedFluxBucket);
		Core.addItemToTab(ACItems.enchantedFluxBucket);
		
	}
	
	@Override
	public void registerItemCraftingRecipes() {
		GameRegistry.addRecipe(new ItemStack(ACItems.embroideredBook, 1, 0),
				new Object[] {
						"glg", "ppp", "glg", 'g', Items.gold_nugget, 'l',
						Items.leather, 'p', Items.paper
				});
		
	}
	
	@Override
	public void registerItemSmeltingRecipes() {
		
	}
	
	@Override
	public void registerOtherItemRecipes() {
		
	}
	
}
