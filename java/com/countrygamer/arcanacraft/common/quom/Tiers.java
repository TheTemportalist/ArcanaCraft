package com.countrygamer.arcanacraft.common.quom;

import net.minecraft.item.ItemStack;

import com.countrygamer.arcanacraft.common.ACItems;

public class Tiers {
	
	public static enum Cast {
		HAND, BASIC, ADVANCED;
		
		public static Tiers.Cast getTier(ItemStack heldStack) {
			if (heldStack == null)
				return Tiers.Cast.HAND;
			else if (heldStack.getItem() == ACItems.focusBasic)
				return Tiers.Cast.BASIC;
			else if (heldStack.getItem() == ACItems.focusAdvanced)
				return Tiers.Cast.ADVANCED;
			else
				return null;
		}
		
	}
	
	public static enum MANUS {
		TIER1, TIER2, TIER3, TIER4;
		
		public static Tiers.MANUS getTier(Tiers.Cast castTier, Quom quom) {
			// TODO
			return Tiers.MANUS.TIER1;
		}
		
	}
	
}
