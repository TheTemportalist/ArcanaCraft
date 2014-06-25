package com.countrygamer.arcanacraft.common.recipes;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import com.countrygamer.arcanacraft.common.block.ACBlocks;
import com.countrygamer.arcanacraft.common.item.ACItems;
import com.countrygamer.arcanacraft.common.quom.Quom;
import com.countrygamer.arcanacraft.common.quom.QuomRegistry;
import com.countrygamer.countrygamercore.common.lib.ItemMeta;
import com.countrygamer.countrygamercore.common.lib.util.UtilCrash;

public class BinderRecipes {
	
	public static class BindRecipe {
		
		public final EnumBinderType bindType;
		
		public final ItemStack input;
		
		public final Quom bindingQuom;
		public final ItemStack bindingItem;
		public final FluidStack bindingFluid;
		
		public final int manusCost;
		
		public final ItemMeta output;
		public final int outputQuantity;
		
		public BindRecipe(EnumBinderType type, ItemStack input, Object binding, int manusCost,
				ItemStack output) {
			this.bindType = type;
			this.input = input;
			this.manusCost = manusCost;
			this.output = ItemMeta.getFromStack(output);
			this.outputQuantity = output.stackSize;
			
			if (binding != null) {
				if (this.bindType == EnumBinderType.QUOM) {
					if (!(binding instanceof Quom)) {
						this.throwCrashReport(this.bindType);
						
					}
					this.bindingQuom = (Quom) binding;
					this.bindingItem = null;
					this.bindingFluid = null;
					
				}
				else if (this.bindType == EnumBinderType.BIND_QUOM
						|| this.bindType == EnumBinderType.ITEM) {
					if (!(binding instanceof ItemStack)) {
						this.throwCrashReport(this.bindType);
						
					}
					this.bindingQuom = null;
					this.bindingItem = (ItemStack) binding;
					this.bindingFluid = null;
					
				}
				else if (this.bindType == EnumBinderType.FLUID) {
					if (!(binding instanceof FluidStack)) {
						this.throwCrashReport(this.bindType);
						
					}
					this.bindingQuom = null;
					this.bindingItem = null;
					this.bindingFluid = (FluidStack) binding;
					
				}
				else {
					UtilCrash.throwCrashReport("No valid BinderType found", "EnumBinderType "
							+ this.bindType + " is not a valid type to pass!");
					
					this.bindingQuom = null;
					this.bindingItem = null;
					this.bindingFluid = null;
					
				}
				
			}
			else {
				this.bindingQuom = null;
				this.bindingItem = null;
				this.bindingFluid = null;
				
			}
			
		}
		
		private void throwCrashReport(EnumBinderType type) {
			UtilCrash.makeCrashReport(
					"Cannot send item binding info to a non item binding recipe type!",
					"Cannot send item info to a recipe with type " + type.toString());
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof BindRecipe) {
				BindRecipe that = (BindRecipe) obj;
				if (this.bindType.equals(that.bindType)) {
					if (this.input.equals(that.input) && this.output.equals(that.output)) {
						boolean sameQuom = this.bindingQuom != null ? (that.bindingQuom != null && this.bindingQuom
								.equals(that.bindingQuom)) : that.bindingQuom == null;
						boolean sameItem = this.bindingItem != null ? (that.bindingItem != null && this.bindingItem
								.equals(that.bindingItem)) : that.bindingItem == null;
						boolean sameFluid = this.bindingFluid != null ? (that.bindingFluid != null && this.bindingFluid
								.equals(that.bindingFluid)) : that.bindingFluid == null;
						return sameQuom && sameItem && sameFluid;
					}
				}
			}
			return false;
		}
		
		public boolean hasInputs(ItemStack input, Object binding) {
			if (binding == null) return false;
			// ArcanaCraft.logger.info("Checking inputs");
			if (this.input.getItem() == input.getItem()
					&& this.input.getItemDamage() == input.getItemDamage()) {
				
				// System.out.println("Valid input");
				
				if (binding instanceof Quom && this.bindType == EnumBinderType.QUOM) {
					
					// System.out.println("Valid Quom");
					
					return this.bindingQuom.equals((Quom) binding);
				}
				else if (binding instanceof ItemStack
						&& (this.bindType == EnumBinderType.BIND_QUOM || this.bindType == EnumBinderType.ITEM)) {
					
					// System.out.println("Valid BindingItemStack");
					ItemStack bindingStack = (ItemStack) binding;
					
					return this.bindingItem.getItem() == bindingStack.getItem()
							&& this.bindingItem.getItemDamage() == bindingStack.getItemDamage()
							&& this.bindingItem.stackSize <= bindingStack.stackSize;
				}
				else if (binding instanceof FluidStack && this.bindType == EnumBinderType.FLUID) {
					FluidStack inputFluid = (FluidStack) binding;
					if (this.bindingFluid.fluidID == inputFluid.fluidID) {
						boolean validTags = this.bindingFluid.tag != null ? (inputFluid.tag != null ? this.bindingFluid.tag
								.equals(inputFluid.tag) : false)
								: inputFluid.tag == null;
						if (validTags) {
							
							// System.out.println("Valid Fluidstack");
							
							return this.bindingFluid.amount <= inputFluid.amount;
						}
					}
				}
			}
			return false;
		}
		
	}
	
	private static BinderRecipes recipes = new BinderRecipes();
	
	public static void registerRecipes() {
		// @formatter:off
		recipes.addRecipe(new BindRecipe(
				EnumBinderType.BIND_QUOM,
				new ItemStack(Blocks.iron_block, 1, 0),
				new ItemStack(ACItems.focusBasic, 1, 0),
				0,
				new ItemStack(ACBlocks.bindableCore, 1, 0)));
		
		recipes.addRecipe(new BindRecipe(
				EnumBinderType.QUOM,
				new ItemStack(ACBlocks.bindableCore, 1, 0),
				QuomRegistry.bind.copy(),
				0,
				new ItemStack(ACBlocks.itemBinder, 1, 0)));
		
		recipes.addRecipe(new BindRecipe(
				EnumBinderType.ITEM,
				new ItemStack(ACBlocks.bindableCore, 1, 0),
				new ItemStack(Items.bucket, 1, 0),
				0,
				new ItemStack(ACBlocks.fluidBinder, 1, 0)));
		
		recipes.addRecipe(new BindRecipe(
				EnumBinderType.ITEM,
				new ItemStack(Items.water_bucket, 1, 0),
				new ItemStack(Items.blaze_powder, 4, 0),
				0,
				new ItemStack(Items.ghast_tear, 4, 0)));
		
		recipes.addRecipe(new BindRecipe(
				EnumBinderType.ITEM,
				new ItemStack(Blocks.stone, 2, 0),
				new ItemStack(Items.blaze_powder, 1, 0),
				0,
				new ItemStack(Blocks.netherrack, 2, 0)));
		
		recipes.addRecipe(new BindRecipe(
				EnumBinderType.ITEM,
				new ItemStack(Blocks.gravel, 2, 0),
				new ItemStack(Items.blaze_powder, 1, 0),
				0,
				new ItemStack(Blocks.soul_sand, 2, 0)));
		
		recipes.addRecipe(new BindRecipe(
				EnumBinderType.ITEM,
				new ItemStack(Items.brick, 3, 0),
				new ItemStack(Items.blaze_powder, 1, 0),
				0,
				new ItemStack(Items.netherbrick, 3, 0)));
		
		recipes.addRecipe(new BindRecipe(
				EnumBinderType.FLUID,
				new ItemStack(Items.stick, 4, 0),
				new FluidStack(FluidRegistry.LAVA, 1000),
				0,
				new ItemStack(Items.blaze_rod, 2, 0)));
		
		recipes.addRecipe(new BindRecipe(
				EnumBinderType.FLUID,
				new ItemStack(Items.string, 1, 0),
				null,
				100,
				new ItemStack(Items.lead, 1, 0)));
		
		recipes.addRecipe(new BindRecipe(
				EnumBinderType.FLUID,
				new ItemStack(Items.rotten_flesh, 1, 0),
				null,
				50,
				new ItemStack(Items.leather, 2, 0)));
		
		recipes.addRecipe(new BindRecipe(
				EnumBinderType.FLUID,
				new ItemStack(Items.spider_eye, 1, 0),
				new FluidStack(ACBlocks.dormantFluxFluid, 100),
				0,
				new ItemStack(Items.fermented_spider_eye, 1, 0)));
		
		recipes.addRecipe(new BindRecipe(
				EnumBinderType.FLUID,
				new ItemStack(Items.sugar, 3, 0),
				new FluidStack(ACBlocks.dormantFluxFluid, 100),
				0,
				new ItemStack(Items.rotten_flesh, 1, 0)));
		
		// @formatter:on
	}
	
	public static BinderRecipes getRecipes() {
		return BinderRecipes.recipes;
	}
	
	public static List<BindRecipe> getRecipeList() {
		return BinderRecipes.getRecipes().recipeList;
	}
	
	public List<BindRecipe> recipeList = new ArrayList<BindRecipe>();
	
	public void addRecipe(BindRecipe recipe) {
		this.recipeList.add(recipe);
	}
	
	public Object[] getRecipeOutput(EnumBinderType bindType, ItemStack input, Object binding) {
		List<BindRecipe> recipes = BinderRecipes.getRecipeList();
		for (int i = 0; i < recipes.size(); i++) {
			BindRecipe recipe = recipes.get(i);
			if (recipe.bindType == bindType) {
				if (recipe.hasInputs(input, binding)) {
					int fluidAmount = 0;
					if (recipe.bindType == EnumBinderType.FLUID) {
						fluidAmount = recipe.bindingFluid.amount;
					}
					return new Object[] {
							recipe.output.getItemStack(recipes.get(i).outputQuantity).copy(),
							fluidAmount, recipe.bindingItem.stackSize, recipe.manusCost,
							recipe.input.stackSize
					};
				}
			}
		}
		return null;
	}
	
}
