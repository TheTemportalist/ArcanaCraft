package com.countrygamer.arcanacraft.common.lib;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

import com.countrygamer.arcanacraft.common.biome.ACBiomes;
import com.countrygamer.arcanacraft.common.block.ACBlocks;

public class TempTreeGen {
	
	public static void generateArcanaTrees(World world, int x, int y, int z) {
		BiomeGenBase biome = world.getBiomeGenForCoords(x, y);
		if (biome == ACBiomes.enchantedGrove) {
			
		}
		else if (biome == ACBiomes.petrifiedGrove) {
			// TempTreeGen.generatePetrifiedTree(world, x, y, z, 16, 4);
		}
		
	}
	
	public static void generatePetrifiedTree(World world, int x, int y, int z, int treeHeight) {
		TempTreeGen.generateTree(world, x, y, z, treeHeight, ACBlocks.petrified_log,
				ACBlocks.petrified_leaves);
	}
	
	public static void generateTree(World world, int x, int y, int z, int logSize, Block log,
			Block leaf) {
		int flag = 3;
		
		int y1;
		for (y1 = y; y1 <= y + logSize; y1++) {
			int meta = logSize - (y1 - y);
			// System.out.println(y1 + ":" + meta);
			world.setBlock(x + 0, y1, z + 0, log, meta, flag);
		}
		
		// y1 is now at the maximum height of the tree
		int totalLeafRows = 3;
		int usedLeafRows = (int) ((float) totalLeafRows / 2.0F);
		int maxLeafY = y1 + usedLeafRows;
		int minLeafY = y1 - (totalLeafRows - usedLeafRows);
		
		for (int y2 = minLeafY; y2 <= maxLeafY; y2++) {
			// System.out.println(y2);
			int leafRadius = (maxLeafY - y2);
			for (int r = 0; r <= leafRadius; r++) {
				for (int x1 = x - r; x1 <= x + r; x1++) {
					for (int z1 = z - r; z1 <= z + r; z1++) {
						if (world.getBlock(x1, y2, z1) == Blocks.air) {
							double distance = Math.sqrt(Math.pow(x - x1, 2) + Math.pow(z - z1, 2));
							int meta = (int)distance - 1;
							System.out.println(meta);
							world.setBlock(x1, y2, z1, leaf, meta, flag);
						}
					}
				}
			}
		}
		
	}
	
}
