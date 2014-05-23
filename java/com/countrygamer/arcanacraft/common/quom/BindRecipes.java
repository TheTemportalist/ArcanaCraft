package com.countrygamer.arcanacraft.common.quom;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import com.countrygamer.arcanacraft.common.block.ACBlocks;
import com.countrygamer.arcanacraft.common.item.ACItems;
import com.countrygamer.countrygamercore.lib.ItemMeta;

public class BindRecipes {
	
	private static BindRecipes recipes = new BindRecipes();
	
	public static void registerRecipes() {
		
		recipes.addRecipe(new BindRecipe(new ItemMeta(Blocks.iron_block, 0),
				new ItemMeta(ACItems.focusBasic, 0), new ItemMeta(
						ACBlocks.bindableCore, 0)));
		
	}
	
	public static class BindRecipe {
		
		public final List<ItemMeta> inputs;
		public final ItemMeta output;
		
		public BindRecipe(ItemMeta... itemMetas) {
			this.inputs = new ArrayList<ItemMeta>();
			for (int i = 0; i < itemMetas.length - 1; i++) {
				this.inputs.add(itemMetas[i]);
			}
			this.output = itemMetas[itemMetas.length - 1];
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof BindRecipe) {
				BindRecipe that = (BindRecipe) obj;
				if (this.inputs.size() == that.inputs.size()) {
					if (this.output.equals(that.output)) {
						for (int i = 0; i < this.inputs.size(); i++) {
							if (!this.inputs.get(i).equals(that.inputs.get(i)))
								return false;
						}
						return true;
					}
				}
			}
			return false;
		}
		
		public boolean hasInputs(ItemStack[] inputs) {
			//ArcanaCraft.logger.info("Checking inputs");
			if (this.inputs.size() == inputs.length) {
				//ArcanaCraft.logger.info("Same input length");
				for (int i = 0; i < inputs.length; i++) {
					if (!this.inputs.contains(ItemMeta.getFromStack(inputs[i]))) {
						//ArcanaCraft.logger.info("Does not contain stack " + i);
						return false;
					}
				}
				return true;
			}
			return false;
		}
		
	}
	
	public static BindRecipes getRecipes() {
		return BindRecipes.recipes;
	}
	
	public static List<BindRecipe> getRecipeList() {
		return BindRecipes.getRecipes().recipeList;
	}
	
	public List<BindRecipe> recipeList = new ArrayList<BindRecipe>();
	
	public void addRecipe(BindRecipe recipe) {
		this.recipeList.add(recipe);
	}
	
	public ItemStack getRecipeOutput(ItemStack[] inputs) {
		List<BindRecipe> recipes = BindRecipes.getRecipeList();
		for (int i = 0; i < recipes.size(); i++) {
			if (recipes.get(i).hasInputs(inputs)) {
				return recipes.get(i).output.getItemStack(1).copy();
			}
		}
		return null;
	}
	
}
