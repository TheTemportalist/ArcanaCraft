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
	public static Item arcana;
	public static Item enchantedFluxBucket;
	public static Item focusBasic;
	public static Item focusAdvanced;
	
	@Override
	public void registerItems() {
		ACItems.embroideredBook = new ItemEmbroideredBook(ArcanaCraft.pluginID,
				"Embroidered Book");
		Core.addItemToTab(ACItems.embroideredBook);
		
		ACItems.arcana = new ItemArcana(ArcanaCraft.pluginID, "The Arcana");
		Core.addItemToTab(ACItems.arcana);
		
		ACItems.focusBasic = new ItemBase(ArcanaCraft.pluginID, "Basic Focus");
		Core.addItemToTab(ACItems.focusBasic);
		
		ACItems.focusAdvanced = new ItemBase(ArcanaCraft.pluginID, "Advanced Focus");
		Core.addItemToTab(ACItems.focusAdvanced);
		
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
