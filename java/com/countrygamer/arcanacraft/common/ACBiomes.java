package com.countrygamer.arcanacraft.common;

import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;

import com.countrygamer.arcanacraft.common.biome.BiomeEnchantedGrove;
import com.countrygamer.core.Base.Plugin.PluginBiomeRegistry;
import com.countrygamer.core.Base.Plugin.PluginUtility;

public class ACBiomes implements PluginBiomeRegistry {
	
	public static BiomeEnchantedGrove enchantedGrove;
	
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
