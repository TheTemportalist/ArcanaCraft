package com.countrygamer.arcanacraft.common.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.countrygamer.arcanacraft.common.lib.ACUtil;
import com.countrygamer.arcanacraft.common.tile.TileEntityAugmentedTank;
import com.countrygamer.countrygamercore.Base.common.block.BlockContainerBase;

public class BlockAugmentedTank extends BlockContainerBase {
	
	public BlockAugmentedTank(Material mat, String modid, String name,
			Class<? extends TileEntity> tileEntityClass) {
		super(mat, modid, name, tileEntityClass);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player,
			int side, float x1, float y1, float z1) {
		if (!player.isSneaking()) {
			TileEntityAugmentedTank tankTE = (TileEntityAugmentedTank) world.getTileEntity(x, y, z);
			ACUtil.itemTankIteraction(player, tankTE);
			return true;
		}
		return false;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return -1;
	}
	
	public boolean hasTileEntityDrops() {
		return true;
	}
	
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity,
			ItemStack itemStack) {
		super.onBlockPlacedBy(world, x, y, z, entity, itemStack);
		
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (tileEntity != null && tileEntity instanceof TileEntityAugmentedTank
				&& itemStack.hasTagCompound()) {
			TileEntityAugmentedTank tankTE = (TileEntityAugmentedTank) tileEntity;
			tankTE.loadTankFromNBT(itemStack.getTagCompound().getCompoundTag("tank"));
		}
	}
	
}
