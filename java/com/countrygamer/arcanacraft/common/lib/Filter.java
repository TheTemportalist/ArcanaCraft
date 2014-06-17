package com.countrygamer.arcanacraft.common.lib;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class Filter {
	
	public final ItemStack[] inputs;
	public final boolean isWhitelist, useMetadata, useOreDict, useNBT;
	
	public Filter(ItemStack[] inputs, boolean isWhitelist, boolean useMetadata, boolean useOreDict,
			boolean useNBT) {
		this.inputs = inputs;
		this.isWhitelist = isWhitelist;
		this.useMetadata = useMetadata;
		this.useOreDict = useOreDict;
		this.useNBT = useNBT;
		
	}
	
	public boolean isItemValid(ItemStack input) {
		boolean isValid = false;
		for (int i = 0; i < this.inputs.length; i++) {
			if (this.inputs[i] != null) {
				isValid = isValid || this.isItemValid_do(input, this.inputs[i].copy());
				if (!isValid && this.useOreDict) {
					isValid = isValid || this.checkOreDictionary(input, this.inputs[i].copy());
				}
			}
		}
		return this.isWhitelist ? isValid : !isValid;
	}
	
	private boolean isItemValid_do(ItemStack input, ItemStack filter) {
		boolean isValid = false;
		
		isValid = filter.getItem() == input.getItem();
		
		if (this.useMetadata) {
			isValid = isValid && filter.getItemDamage() == input.getItemDamage();
		}
		
		if (this.useNBT) {
			isValid = isValid && ItemStack.areItemStackTagsEqual(input, filter);
		}
		
		return isValid;
	}
	
	private boolean checkOreDictionary(ItemStack input, ItemStack filter) {
		boolean isValid = false;
		
		int[] oreIDs = OreDictionary.getOreIDs(filter);
		for (int idIndex = 0; idIndex < oreIDs.length && !isValid; idIndex++) {
			int oreID = oreIDs[idIndex];
			
			ArrayList<ItemStack> oreDictStacks = OreDictionary.getOres(OreDictionary
					.getOreName(oreID));
			for (final ItemStack filterOreDictStack : oreDictStacks) {
				isValid = this.isItemValid_do(input, filterOreDictStack);
			}
		}
		
		return isValid;
	}
	
	public Filter copy() {
		return new Filter(this.inputs, this.isWhitelist, this.useMetadata, this.useOreDict,
				this.useNBT);
	}
	
}
