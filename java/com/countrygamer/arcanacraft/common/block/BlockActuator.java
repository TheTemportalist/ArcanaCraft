package com.countrygamer.arcanacraft.common.block;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.countrygamer.arcanacraft.common.ACOptions;
import com.countrygamer.arcanacraft.common.ArcanaCraft;
import com.countrygamer.countrygamercore.Base.common.block.BlockContainerBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockActuator extends BlockContainerBase {
	
	@SideOnly(Side.CLIENT)
	private IIcon faceIcon;
	
	public BlockActuator(Material mat, String modid, String name,
			Class<? extends TileEntity> tileEntityClass) {
		super(mat, modid, name, tileEntityClass);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconReg) {
		super.registerBlockIcons(iconReg);
		this.faceIcon = iconReg.registerIcon(ArcanaCraft.pluginID + ":" + this.getName() + "_face");
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int side, int meta) {
		if (side == ForgeDirection.getOrientation(meta).ordinal()) {
			return this.faceIcon;
		}
		return this.blockIcon;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player,
			int side, float x1, float y1, float z1) {
		/*
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (tileEntity != null && tileEntity instanceof TileEntityActuator) {
			TileEntityActuator actuatorTE = (TileEntityActuator) tileEntity;
			if (actuatorTE.getStackInSlot(0) == null) {
				if (player.getHeldItem() != null) {
					actuatorTE.setInventorySlotContents(0,
							player.inventory.decrStackSize(player.inventory.currentItem, 1).copy());
					return true;
				}
			}
			else {
				if (player.isSneaking()) {
					UtilDrops.spawnItemStack(world, player.posX, player.posY, player.posZ,
							actuatorTE.decrStackSize(0, 1), new Random());
				}
				else {
					actuatorTE.rightClick();
				}
				return true;
			}
		}
		 */
		if (!player.isSneaking()) {
			player.openGui(ArcanaCraft.instance, ACOptions.actuatorGui, world, x, y, z);
			return true;
		}
		return false;
	}
	
	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY,
			float hitZ, int meta) {
		return ForgeDirection.getOrientation(side).ordinal();
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity,
			ItemStack itemStack) {
		if (entity != null && entity instanceof EntityPlayer) {
			if (((EntityPlayer) entity).isSneaking()) {
				world.setBlockMetadataWithNotify(x, y, z,
						ForgeDirection.getOrientation(world.getBlockMetadata(x, y, z))
								.getOpposite().ordinal(), 3);
			}
		}
	}
	
}
