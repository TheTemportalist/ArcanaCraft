package com.countrygamer.arcanacraft.common.recipes;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import com.countrygamer.countrygamercore.lib.ItemMeta;

public class ExtractRecipes {
	
	private static ExtractRecipes	recipes		= new ExtractRecipes();
	
	public Map<ItemMeta, ItemMeta>	recipeList	= new HashMap<ItemMeta, ItemMeta>();
	
	public static void registerRecipes() {
		
		ExtractRecipes.recipes.addRecipe(new ItemMeta(Blocks.cobblestone, 0), new ItemMeta(
				Blocks.gravel, 0));
		
		ExtractRecipes.recipes.addRecipe(new ItemMeta(Blocks.gravel, 0), new ItemMeta(Blocks.sand,
				0));
		
		
		
	}
	
	public void addRecipe(ItemMeta input, ItemMeta output) {
		ExtractRecipes.recipes.recipeList.put(input, output);
		
	}
	
	public static ItemStack getOutput(ItemMeta input) {
		Map<ItemMeta, ItemMeta> list = ExtractRecipes.recipes.recipeList;
		for (final ItemMeta key : list.keySet()) {
			if (key.equals(input)) {
				return list.get(key).getItemStack(1);
			}
		}
		return null;
	}
	
}
