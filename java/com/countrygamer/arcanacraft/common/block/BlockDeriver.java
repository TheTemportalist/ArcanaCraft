package com.countrygamer.arcanacraft.common.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.countrygamer.arcanacraft.common.tile.TileEntityDeriver;
import com.countrygamer.countrygamercore.Base.common.block.BlockContainerBase;

public class BlockDeriver extends BlockContainerBase {
	
	public BlockDeriver(Material mat, String modid, String name,
			Class<? extends TileEntity> tileEntityClass) {
		super(mat, modid, name, tileEntityClass);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player,
			int side, float x1, float y1, float z1) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (tileEntity != null && tileEntity instanceof TileEntityDeriver) {
			//TileEntityDeriver deriverTE = (TileEntityDeriver) tileEntity;
			
			if (!player.isSneaking()) {
				//System.out.println("Open gui");
				// player.openGui(ArcanaCraft.instance, ACOptions.deriverGui, world, x, y, z);
				return true;
			}
		}
		return false;
	}
	
}
