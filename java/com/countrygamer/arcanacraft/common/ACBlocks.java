package com.countrygamer.arcanacraft.common;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import com.countrygamer.arcanacraft.common.block.BlockEnchantedFlux;
import com.countrygamer.arcanacraft.common.block.MaterialEnchantedFlux;
import com.countrygamer.core.Base.Plugin.PluginBlockRegistry;
import com.countrygamer.countrygamercore.common.Core;

import cpw.mods.fml.common.registry.GameRegistry;

public class ACBlocks implements PluginBlockRegistry {
	
	public static Material enchantedFluxMaterial = new MaterialEnchantedFlux(
			MapColor.grassColor);
	public static Block enchantedFluxBlock;
	public static Fluid enchantedFluxFluid = new Fluid("Enchanted Flux")
			.setLuminosity(30).setDensity(500).setViscosity(3000).setTemperature(500);
	
	@Override
	public void registryTileEntities() {
		
	}
	
	@Override
	public void registerBlocks() {
		FluidRegistry.registerFluid(ACBlocks.enchantedFluxFluid);
		
		ACBlocks.enchantedFluxBlock = new BlockEnchantedFlux(
				ACBlocks.enchantedFluxFluid, ACBlocks.enchantedFluxMaterial);
		GameRegistry.registerBlock(ACBlocks.enchantedFluxBlock,
				"Enchanted Flux Still");
		Core.addBlockToTab(ACBlocks.enchantedFluxBlock);
		
		ACBlocks.enchantedFluxFluid.setUnlocalizedName(ACBlocks.enchantedFluxBlock
				.getUnlocalizedName());
		
	}
	
	@Override
	public void registerBlockCraftingRecipes() {
		
	}
	
	@Override
	public void registerBlockSmeltingRecipes() {
		
	}
	
	@Override
	public void registerOtherBlockRecipes() {
		
	}
	
}
