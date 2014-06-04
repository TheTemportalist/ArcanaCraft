package com.countrygamer.arcanacraft.common.quom;

import net.minecraft.item.ItemStack;

import com.countrygamer.arcanacraft.common.item.ACItems;

public class Tiers {
	
	public static enum Cast {
		HAND, BASIC, ADVANCED;
		
		public static Tiers.Cast getTier(ItemStack heldStack) {
			if (heldStack == null)
				return Tiers.Cast.HAND;
			else if (heldStack.getItem() == ACItems.focusBasic)
				return Tiers.Cast.BASIC;
			else if (heldStack.getItem() == ACItems.stave)
				return Tiers.Cast.ADVANCED;
			else
				return null;
		}
		
		public static int tierToInt(Tiers.Cast tier) {
			if (tier == HAND)
				return 1;
			else if (tier == BASIC)
				return 2;
			else if (tier == ADVANCED)
				return 3;
			else
				return 0;
		}
		
	}
	
	public static enum MANUS {
		TIER1, TIER2, TIER3, TIER4;
		
		public static Tiers.MANUS getTier(Tiers.Cast castTier, Quom quom) {
			// TODO
			return Tiers.MANUS.TIER1;
		}
		
		public int getTierValue() {
			switch (this) {
				case TIER1:
					return 1;
				case TIER2:
					return 2;
				case TIER3:
					return 3;
				case TIER4:
					return 4;
				default:
					return 0;
			}
		}
		
	}
	
}
