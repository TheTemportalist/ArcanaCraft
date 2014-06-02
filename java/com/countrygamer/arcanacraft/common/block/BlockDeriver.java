package com.countrygamer.arcanacraft.common.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.countrygamer.arcanacraft.common.ACOptions;
import com.countrygamer.arcanacraft.common.ArcanaCraft;
import com.countrygamer.arcanacraft.common.tile.TileEntityDeriver;
import com.countrygamer.core.Base.common.block.BlockContainerBase;

public class BlockDeriver extends BlockContainerBase {
	
	public BlockDeriver(Material mat, String modid, String name,
			Class<? extends TileEntity> tileEntityClass) {
		super(mat, modid, name, tileEntityClass);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return null;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player,
			int side, float x1, float y1, float z1) {
		BlockDeriver.checkBlockStructure(world, x, y, z);
		
		if (!player.isSneaking()) {
			TileEntity tileEntity = world.getTileEntity(x, y, z);
			if (tileEntity != null) {
				player.openGui(ArcanaCraft.instance, ACOptions.deriverGui, world, x, y, z);
				return true;
			}
		}
		return false;
	}
	
	public static void checkBlockStructure(World world, int deriverX, int deriverY, int deriverZ) {
		boolean isValidStructure = true;
		
		isValidStructure = isValidStructure && world.getBlock(deriverX + 0, deriverY + 1, deriverZ + 0) == Blocks.cobblestone;
		
		if (isValidStructure) {
			world.setTileEntity(deriverX, deriverY, deriverZ, new TileEntityDeriver());
		}
		else {
			world.removeTileEntity(deriverX, deriverY, deriverZ);
		}
		
	}
	
}
