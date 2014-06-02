package com.countrygamer.arcanacraft.common.biome;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;

import com.countrygamer.core.Base.Plugin.PluginUtility;
import com.countrygamer.core.Base.Plugin.registry.PluginBiomeRegistry;

public class ACBiomes implements PluginBiomeRegistry {
	
	public static BiomeEnchantedGrove enchantedGrove;
	public static BiomeGenBase petrifiedGrove;;
	
	@Override
	public void registerBiomes() {
		ACBiomes.enchantedGrove = new BiomeEnchantedGrove(
				PluginUtility.getNewBiomeID());
		
	}
	
	@Override
	public void registerBiomesWithWorldGen() {
		BiomeDictionary.registerBiomeType(ACBiomes.enchantedGrove,
				BiomeDictionary.Type.FOREST);
		BiomeManager.addSpawnBiome(ACBiomes.enchantedGrove);
	}
	
}