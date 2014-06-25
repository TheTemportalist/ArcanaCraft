package com.countrygamer.arcanacraft.common.item;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;

import com.countrygamer.arcanacraft.common.ArcanaCraft;
import com.countrygamer.arcanacraft.common.block.ACBlocks;
import com.countrygamer.countrygamercore.base.registry.PluginItemRegistry;
import com.countrygamer.countrygamercore.common.Core;
import com.countrygamer.countrygamercore.common.item.ItemBase;

import cpw.mods.fml.common.registry.GameRegistry;

public class ACItems implements PluginItemRegistry {
	
	public static Item embroideredBook;
	public static Item arcana;
	public static Item enchantedFluxBucket;
	public static Item focusBasic;
	public static Item focusAdvanced;
	public static Item stave;
	
	public static Item charm;
	public static Item inventorySack;
	
	public static Item cowl;
	public static Item robe;
	public static Item pants;
	public static Item boots;
	
	public static Item gauntlet;
	
	@Override
	public void registerItems() {
		ACItems.embroideredBook = new ItemEmbroideredBook(ArcanaCraft.pluginID, "Embroidered Book");
		Core.addItemToTab(ACItems.embroideredBook);
		
		ACItems.arcana = new ItemArcana(ArcanaCraft.pluginID, "The Arcana");
		Core.addItemToTab(ACItems.arcana);
		
		ACItems.focusBasic = new ItemBase(ArcanaCraft.pluginID, "Basic Focus");
		Core.addItemToTab(ACItems.focusBasic);
		
		ACItems.focusAdvanced = new ItemBase(ArcanaCraft.pluginID, "Advanced Focus");
		Core.addItemToTab(ACItems.focusAdvanced);
		
		ACItems.stave = new ItemBase(ArcanaCraft.pluginID, "Stave");
		Core.addItemToTab(ACItems.stave);
		
		CharmRegister.INSTANCE.registerObjects();
		ACItems.charm = new ItemCharm(ArcanaCraft.pluginID, "Charm");
		Core.addItemToTab(ACItems.charm);
		
		ACItems.inventorySack = new ItemInventorySack(ArcanaCraft.pluginID, "Player Sack");
		Core.addItemToTab(ACItems.inventorySack);
		
		int arcane = Core.proxy.addArmor("arcaic");
		ACItems.cowl = new ItemArcaicArmor(ArcanaCraft.pluginID, "Cowl of the Arcane",
				ItemArmor.ArmorMaterial.DIAMOND, arcane, "helmet");
		Core.addItemToTab(ACItems.cowl);
		ACItems.robe = new ItemArcaicArmor(ArcanaCraft.pluginID, "Robe of the Arcane",
				ItemArmor.ArmorMaterial.DIAMOND, arcane, "chestplate");
		Core.addItemToTab(ACItems.robe);
		ACItems.pants = new ItemArcaicArmor(ArcanaCraft.pluginID, "Arcaic Pants",
				ItemArmor.ArmorMaterial.DIAMOND, arcane, "pants");
		Core.addItemToTab(ACItems.pants);
		ACItems.boots = new ItemArcaicArmor(ArcanaCraft.pluginID, "Slippers of the Arcane",
				ItemArmor.ArmorMaterial.DIAMOND, arcane, "boots");
		Core.addItemToTab(ACItems.boots);
		
		ACItems.gauntlet = new ItemGauntlet(ArcanaCraft.pluginID, "Arcane Gauntlet");
		Core.addItemToTab(ACItems.gauntlet);
		
	}
	
	public void registerItemsPostBlock() {
		ACItems.enchantedFluxBucket = new ItemEnchantedFluxBucket(ArcanaCraft.pluginID,
				ACBlocks.enchantedFluxBlock);
		GameRegistry.registerItem(ACItems.enchantedFluxBucket, "Enchanted Flux Bucket");
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack(
				"Enchanted Flux".toLowerCase(), FluidContainerRegistry.BUCKET_VOLUME),
				new ItemStack(ACItems.enchantedFluxBucket), new ItemStack(Items.bucket));
		ArcanaCraft.buckets.put(ACBlocks.enchantedFluxBlock, ACItems.enchantedFluxBucket);
		Core.addItemToTab(ACItems.enchantedFluxBucket);
		
	}
	
	@Override
	public void registerItemCraftingRecipes() {
		GameRegistry.addRecipe(new ItemStack(ACItems.embroideredBook, 1, 0), new Object[] {
				"glg", "ppp", "glg", 'g', Items.gold_nugget, 'l', Items.leather, 'p', Items.paper
		});
		GameRegistry.addRecipe(new ItemStack(ACItems.inventorySack), "s s", "wcw", "www", 's',
				Items.string, 'w', Blocks.wool, 'c', Blocks.chest);
		
	}
	
	@Override
	public void registerItemSmeltingRecipes() {
		
	}
	
	@Override
	public void registerOtherItemRecipes() {
		
	}
	
}
