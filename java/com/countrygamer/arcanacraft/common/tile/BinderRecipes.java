package com.countrygamer.arcanacraft.common.tile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.crash.CrashReport;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import com.countrygamer.arcanacraft.common.ArcanaCraft;
import com.countrygamer.arcanacraft.common.block.ACBlocks;
import com.countrygamer.arcanacraft.common.item.ACItems;
import com.countrygamer.arcanacraft.common.quom.Quom;
import com.countrygamer.arcanacraft.common.quom.QuomRegistry;
import com.countrygamer.countrygamercore.lib.ItemMeta;

import cpw.mods.fml.common.FMLCommonHandler;

public class BinderRecipes {
	
	public static class BindRecipe {
		
		public final EnumBinderType bindType;
		
		public final ItemMeta input;
		
		public final Quom bindingQuom;
		public final ItemMeta bindingItem;
		public final FluidStack bindingFluid;
		
		public final int manusCost;
		
		public final ItemMeta output;
		public final int outputQuantity;
		
		public BindRecipe(EnumBinderType type, ItemMeta input, Quom bindingQuom, int manusCost,
				ItemStack output) {
			this.bindType = type;
			
			this.input = input;
			this.bindingQuom = bindingQuom;
			this.bindingItem = null;
			this.bindingFluid = null;
			
			this.manusCost = manusCost;
			
			this.output = ItemMeta.getFromStack(output);
			this.outputQuantity = output.stackSize;
			
			if (this.bindType != EnumBinderType.QUOM) {
				this.throwCrashReport(this.bindType);
			}
		}
		
		public BindRecipe(EnumBinderType type, ItemMeta input, ItemMeta bindingItem, int manusCost,
				ItemStack output) {
			this.bindType = type;
			
			this.input = input;
			this.bindingQuom = null;
			this.bindingItem = bindingItem;
			this.bindingFluid = null;
			
			this.manusCost = manusCost;
			
			this.output = ItemMeta.getFromStack(output);
			this.outputQuantity = output.stackSize;
			
			if (this.bindType != EnumBinderType.BIND_QUOM && this.bindType != EnumBinderType.ITEM) {
				this.throwCrashReport(this.bindType);
			}
		}
		
		public BindRecipe(EnumBinderType type, ItemMeta input, FluidStack bindingFluid,
				int manusCost, ItemStack output) {
			this.bindType = type;
			
			this.input = input;
			this.bindingQuom = null;
			this.bindingItem = null;
			this.bindingFluid = bindingFluid;
			
			this.manusCost = manusCost;
			
			this.output = ItemMeta.getFromStack(output);
			this.outputQuantity = output.stackSize;
			
			if (this.bindType != EnumBinderType.FLUID) {
				this.throwCrashReport(this.bindType);
			}
		}
		
		private void throwCrashReport(EnumBinderType type) {
			CrashReport crashreport = new CrashReport(
					"Cannot send item binding info to a non item binding recipe type!",
					new Throwable("Cannot send item info to a recipe with type " + type.toString()));
			File file1 = new File(new File(new File("."), "crash-reports"), "crash-"
					+ (new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")).format(new Date())
					+ "-server.txt");
			
			if (crashreport.saveToFile(file1)) {
				ArcanaCraft.logger.info("This crash report has been saved to: "
						+ file1.getAbsolutePath());
			}
			else {
				ArcanaCraft.logger.info("We were unable to save this crash report to disk.");
			}
			
			FMLCommonHandler.instance().expectServerStopped(); // has to come before finalTick
			// to avoid race conditions
			Minecraft.getMinecraft().crashed(crashreport);
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
			if (this.input.equals(ItemMeta.getFromStack(input))) {
				if (binding instanceof Quom && this.bindType == EnumBinderType.QUOM) {
					return this.bindingQuom.equals((Quom) binding);
				}
				else if (binding instanceof ItemMeta
						&& (this.bindType == EnumBinderType.BIND_QUOM || this.bindType == EnumBinderType.ITEM)) {
					return this.bindingItem.equals((ItemMeta) binding);
				}
				else if (binding instanceof FluidStack && this.bindType == EnumBinderType.FLUID) {
					FluidStack inputFluid = (FluidStack) binding;
					if (this.bindingFluid.fluidID == inputFluid.fluidID) {
						boolean validTags = this.bindingFluid.tag != null ? (inputFluid.tag != null ? this.bindingFluid.tag
								.equals(inputFluid.tag) : false)
								: inputFluid.tag == null;
						if (validTags) {
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
				new ItemMeta(Blocks.iron_block, 0),
				new ItemMeta(ACItems.focusBasic, 0),
				0,
				new ItemStack(ACBlocks.bindableCore, 1, 0)));
		
		recipes.addRecipe(new BindRecipe(
				EnumBinderType.QUOM,
				new ItemMeta(ACBlocks.bindableCore, 0),
				QuomRegistry.bind.copy(),
				0,
				new ItemStack(ACBlocks.itemBinder, 1, 0)));
		
		recipes.addRecipe(new BindRecipe(
				EnumBinderType.ITEM,
				new ItemMeta(ACBlocks.bindableCore, 0),
				new ItemMeta(Items.bucket, 0),
				0,
				new ItemStack(ACBlocks.fluidBinder, 1, 0)));
		
		recipes.addRecipe(new BindRecipe(
				EnumBinderType.FLUID,
				new ItemMeta(Items.stick, 0),
				new FluidStack(FluidRegistry.LAVA, 1000),
				0,
				new ItemStack(Items.blaze_rod, 2, 0)));
		
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
							fluidAmount
					};
				}
			}
		}
		return null;
	}
	
}
