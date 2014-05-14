package com.countrygamer.arcanacraft.common.biome;

import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeEnchantedGrove extends BiomeGenBase {

	public BiomeEnchantedGrove(int biomeID) {
		super(biomeID);

		this.setBiomeName("Enchanted Forest");
		this.setColor(0x004BFF);
		
		this.setDisableRain();
		this.enableSnow = false;
		
		this.topBlock = Blocks.grass;
		this.fillerBlock = Blocks.dirt;
		
		this.flowers.clear();
		
		this.spawnableCreatureList.clear();
		this.spawnableWaterCreatureList.clear();
		this.spawnableMonsterList.clear();
		
	}
	
}
