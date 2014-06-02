package com.countrygamer.arcanacraft.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import com.countrygamer.arcanacraft.common.ArcanaCraft;
import com.countrygamer.arcanacraft.common.tile.TileEntityAugmentedTank;
import com.countrygamer.arcanacraft.common.tile.TileEntityBinder;
import com.countrygamer.arcanacraft.common.tile.TileEntityDeriver;
import com.countrygamer.core.Base.Plugin.registry.PluginBlockRegistry;
import com.countrygamer.countrygamercore.common.Core;

import cpw.mods.fml.common.registry.GameRegistry;

public class ACBlocks implements PluginBlockRegistry {
	
	public static Material enchantedFluxMaterial = new MaterialEnchantedFlux(MapColor.grassColor);
	public static Block enchantedFluxBlock;
	public static Fluid enchantedFluxFluid = new Fluid("Enchanted Flux").setLuminosity(30)
			.setDensity(500).setViscosity(3000).setTemperature(500);
	public static Fluid dormantFluxFluid = new Fluid("Dormant Flux").setLuminosity(30)
			.setDensity(500).setViscosity(3000).setTemperature(500);
	public static Fluid liquidManus = new Fluid("Liquid Manus");
	
	public static Block petrified_log;
	public static Block petrified_leaves;
	
	public static Block bindableCore;
	public static Block quomBinder, itemBinder, fluidBinder;
	public static Block augmentedTank;
	
	public static Block falseAir;
	
	public static Block deriver;
	
	@Override
	public void registryTileEntities() {
		GameRegistry.registerTileEntity(TileEntityAugmentedTank.class, ArcanaCraft.pluginID
				+ "_AugmentedTank");
		
		// GameRegistry.registerTileEntity(TileEntityFalseAir.class, ArcanaCraft.pluginID
		// + "_FalseAir");
		
		GameRegistry.registerTileEntity(TileEntityBinder.class, ArcanaCraft.pluginID + "_Binder");
		
		GameRegistry.registerTileEntity(TileEntityDeriver.class, ArcanaCraft.pluginID + "_Deriver");
		
	}
	
	@Override
	public void registerBlocks() {
		FluidRegistry.registerFluid(ACBlocks.enchantedFluxFluid);
		FluidRegistry.registerFluid(ACBlocks.dormantFluxFluid);
		FluidRegistry.registerFluid(ACBlocks.liquidManus);
		
		ACBlocks.enchantedFluxBlock = new BlockEnchantedFlux(ACBlocks.enchantedFluxFluid,
				ACBlocks.enchantedFluxMaterial);
		GameRegistry.registerBlock(ACBlocks.enchantedFluxBlock, "Enchanted Flux Still");
		Core.addBlockToTab(ACBlocks.enchantedFluxBlock);
		
		ACBlocks.enchantedFluxFluid.setUnlocalizedName(ACBlocks.enchantedFluxBlock
				.getUnlocalizedName());
		ACBlocks.dormantFluxFluid.setUnlocalizedName("dormant flux");
		ACBlocks.liquidManus.setUnlocalizedName("liquid manus");
		
		ACBlocks.petrified_log = new BlockPetrifiedLog(Material.rock, ArcanaCraft.pluginID,
				"Petrified Log");
		Core.addBlockToTab(ACBlocks.petrified_log);
		
		ACBlocks.petrified_leaves = new BlockPetrifiedLeaves(Material.rock, ArcanaCraft.pluginID,
				"Petrified Leaves");
		Core.addBlockToTab(ACBlocks.petrified_leaves);
		
		ACBlocks.bindableCore = new BlockBindableCore(Material.ground, ArcanaCraft.pluginID,
				"Bindable Core");
		Core.addBlockToTab(ACBlocks.bindableCore);
		
		ACBlocks.quomBinder = new BlockBinderQuom(Material.ground, ArcanaCraft.pluginID,
				"Quom Binder");
		Core.addBlockToTab(ACBlocks.quomBinder);
		
		ACBlocks.itemBinder = new BlockBinderItem(Material.ground, ArcanaCraft.pluginID,
				"Item Binder");
		Core.addBlockToTab(ACBlocks.itemBinder);
		
		ACBlocks.fluidBinder = new BlockBinderFluid(Material.ground, ArcanaCraft.pluginID,
				"Fluid Binder");
		Core.addBlockToTab(ACBlocks.fluidBinder);
		
		ACBlocks.augmentedTank = new BlockAugmentedTank(Material.ground, ArcanaCraft.pluginID,
				"Augmented Tank", TileEntityAugmentedTank.class);
		Core.addBlockToTab(ACBlocks.augmentedTank);
		
		// ACBlocks.falseAir = new BlockFalseAir(Material.cloth, ArcanaCraft.pluginID, "False Air",
		// TileEntityFalseAir.class);
		
		ACBlocks.deriver = new BlockDeriver(Material.ground, ArcanaCraft.pluginID, "Deriver", null);
		Core.addBlockToTab(ACBlocks.deriver);
		
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
