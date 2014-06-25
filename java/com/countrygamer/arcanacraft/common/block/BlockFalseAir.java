package com.countrygamer.arcanacraft.common.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.countrygamer.countrygamercore.base.common.block.BlockContainerBase;

public class BlockFalseAir extends BlockContainerBase {
	
	public BlockFalseAir(Material mat, String modid, String name,
			Class<? extends TileEntity> tileEntityClass) {
		super(mat, modid, name, tileEntityClass);
		this.setBlockBounds(0, 0, 0, 0, 0, 0);
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4,
			AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity) {
		
	}
	
	public int quantityDropped(Random par1Random) {
		return 0;
	}
	
	@Override
	public boolean isReplaceable(IBlockAccess world, int x, int y, int z) {
		return true;
	}
	
	@Override
	public boolean isAir(IBlockAccess world, int x, int y, int z) {
		return true;
	}
	
}
