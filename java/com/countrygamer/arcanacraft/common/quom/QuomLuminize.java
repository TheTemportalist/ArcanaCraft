package com.countrygamer.arcanacraft.common.quom;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.countrygamer.arcanacraft.common.extended.ExtendedArcanePlayer;
import com.countrygamer.arcanacraft.common.quom.Tiers.Cast;

public class QuomLuminize extends Quom {
	
	public QuomLuminize(String name, Quom parent) {
		super(name, parent);
	}
	
	public boolean canDiscover_Craft(ExtendedArcanePlayer arcanePlayer, ItemStack itemStack) {
		return itemStack.getItem() == Item.getItemFromBlock(Blocks.torch);
	}
	
	@Override
	public void onUse(EntityPlayer player, ExtendedArcanePlayer arcanePlayer, World world, int x,
			int y, int z, int side, Cast castTier) {
		
	}
	
}
