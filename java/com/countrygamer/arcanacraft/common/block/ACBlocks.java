package com.countrygamer.arcanacraft.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import com.countrygamer.arcanacraft.common.ArcanaCraft;
import com.countrygamer.arcanacraft.common.tile.TileEntityAugmentedTank;
import com.countrygamer.core.Base.Plugin.registry.PluginBlockRegistry;
import com.countrygamer.core.Base.common.block.BlockBase;
import com.countrygamer.countrygamercore.common.Core;

import cpw.mods.fml.common.registry.GameRegistry;

public class ACBlocks implements PluginBlockRegistry {
	
	public static Material enchantedFluxMaterial = new MaterialEnchantedFlux(MapColor.grassColor);
	public static Block enchantedFluxBlock;
	public static Fluid enchantedFluxFluid = new Fluid("Enchanted Flux").setLuminosity(30)
			.setDensity(500).setViscosity(3000).setTemperature(500);
	public static Fluid dormantFluxFluid = new Fluid("Dormant Flux").setLuminosity(30)
			.setDensity(500).setViscosity(3000).setTemperature(500);
	
	public static Block petrified_log;
	
	public static Block bindableCore;
	public static Block quomBinder, itemBinder, liquidBinder;
	public static Block augmentedTank;
	
	public static Block falseAir;
	
	@Override
	public void registryTileEntities() {
		GameRegistry.registerTileEntity(TileEntityAugmentedTank.class, ArcanaCraft.pluginID
				+ "_AugmentedTank");
		
		// GameRegistry.registerTileEntity(TileEntityFalseAir.class, ArcanaCraft.pluginID
		// + "_FalseAir");
		
	}
	
	@Override
	public void registerBlocks() {
		FluidRegistry.registerFluid(ACBlocks.enchantedFluxFluid);
		FluidRegistry.registerFluid(ACBlocks.dormantFluxFluid);
		
		ACBlocks.enchantedFluxBlock = new BlockEnchantedFlux(ACBlocks.enchantedFluxFluid,
				ACBlocks.enchantedFluxMaterial);
		GameRegistry.registerBlock(ACBlocks.enchantedFluxBlock, "Enchanted Flux Still");
		Core.addBlockToTab(ACBlocks.enchantedFluxBlock);
		
		ACBlocks.enchantedFluxFluid.setUnlocalizedName(ACBlocks.enchantedFluxBlock
				.getUnlocalizedName());
		ACBlocks.dormantFluxFluid.setUnlocalizedName("dormant flux");
		
		ACBlocks.petrified_log = new BlockPetrifiedLog(Material.rock, ArcanaCraft.pluginID,
				"Petrified Log");
		Core.addBlockToTab(ACBlocks.petrified_log);
		
		ACBlocks.bindableCore = new BlockBindableCore(Material.ground, ArcanaCraft.pluginID,
				"Bindable Core");
		Core.addBlockToTab(ACBlocks.bindableCore);
		
		ACBlocks.quomBinder = new BlockBase(Material.ground, ArcanaCraft.pluginID, "Quom Binder");
		Core.addBlockToTab(ACBlocks.quomBinder);
		
		ACBlocks.itemBinder = new BlockBase(Material.ground, ArcanaCraft.pluginID, "Item Binder");
		Core.addBlockToTab(ACBlocks.itemBinder);
		
		ACBlocks.liquidBinder = new BlockBase(Material.ground, ArcanaCraft.pluginID,
				"Liquid Binder");
		Core.addBlockToTab(ACBlocks.liquidBinder);
		
		ACBlocks.augmentedTank = new BlockAugmentedTank(Material.ground, ArcanaCraft.pluginID,
				"Augmented Tank", TileEntityAugmentedTank.class);
		Core.addBlockToTab(ACBlocks.augmentedTank);
		
		// ACBlocks.falseAir = new BlockFalseAir(Material.cloth, ArcanaCraft.pluginID, "False Air",
		// TileEntityFalseAir.class);
		
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
