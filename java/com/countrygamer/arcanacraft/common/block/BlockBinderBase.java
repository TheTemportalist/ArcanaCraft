package com.countrygamer.arcanacraft.common.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.countrygamer.arcanacraft.common.ACOptions;
import com.countrygamer.arcanacraft.common.ArcanaCraft;
import com.countrygamer.arcanacraft.common.tile.EnumBinderType;
import com.countrygamer.arcanacraft.common.tile.TileEntityBinder;
import com.countrygamer.countrygamercore.Base.common.block.BlockContainerBase;

public abstract class BlockBinderBase extends BlockContainerBase {
	
	public BlockBinderBase(Material mat, String modid, String name) {
		super(mat, modid, name, TileEntityBinder.class);
	}
	
	@Override
	public int getRenderType() {
		return -1;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		TileEntityBinder binderTE = (TileEntityBinder) super.createNewTileEntity(var1, var2);
		binderTE.setBinderType(this.getBinderType());
		return binderTE;
	}
	
	protected abstract EnumBinderType getBinderType();
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player,
			int side, float x1, float y1, float z1) {
		if (!player.isSneaking()) {
			player.openGui(ArcanaCraft.instance, ACOptions.binderGui, world, x, y, z);
			return true;
		}
		return false;
	}
	
}
