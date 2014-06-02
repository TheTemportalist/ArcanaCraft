package com.countrygamer.arcanacraft.common.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import com.countrygamer.arcanacraft.common.lib.ACUtil;
import com.countrygamer.arcanacraft.common.tile.EnumBinderType;
import com.countrygamer.arcanacraft.common.tile.TileEntityBinder;

public class BlockBinderFluid extends BlockBinderBase {
	
	public BlockBinderFluid(Material mat, String modid, String name) {
		super(mat, modid, name);
	}
	
	@Override
	protected EnumBinderType getBinderType() {
		return EnumBinderType.FLUID;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player,
			int side, float x1, float y1, float z1) {
		TileEntityBinder binderTE = (TileEntityBinder) world.getTileEntity(x, y, z);
		if (binderTE.canModifyTank()) {
			if (!ACUtil.itemTankIteraction(player, binderTE)) {
				return super.onBlockActivated(world, x, y, z, player, side, x1, y1, z1);
			}
			world.markBlockForUpdate(x, y, z);
			return true;
		}
		else {
			return false;
		}
	}
	
}
