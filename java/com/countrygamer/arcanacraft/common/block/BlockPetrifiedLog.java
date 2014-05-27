package com.countrygamer.arcanacraft.common.block;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.countrygamer.core.Base.common.block.BlockBase;
import com.countrygamer.countrygamercore.lib.UtilHex;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockPetrifiedLog extends BlockBase {
	
	@SideOnly(Side.CLIENT)
	private IIcon	side, topBottom;
	
	public BlockPetrifiedLog(Material mat, String modid, String name) {
		super(mat, modid, name);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconReg) {
		String blockName = this.getUnlocalizedName().substring(5);
		this.side = iconReg.registerIcon(this.modid + ":" + blockName + "");
		this.topBottom = iconReg.registerIcon(this.modid + ":" + blockName + "_top");
		
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int side, int meta) {
		if (side == 0 || side == 1)
			return this.topBottom;
		else
			return this.side;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
		// 0xffffff = 16777215
		
		// 0x000000 = 0
		int meta = world.getBlockMetadata(x, y, z);
		
		int largestMeta = meta;
		int y1 = y - 1;
		while (world.getBlock(x, y1, z) == ACBlocks.petrified_log) {
			int meta2 = world.getBlockMetadata(x, y1, z);
			if (meta2 > largestMeta) largestMeta = meta2;
			y1--;
		}
		
		int minVal = 100;
		int maxVal = 255 - minVal;
		int val = maxVal - (int) (((float) meta * (float) maxVal) / (float) largestMeta) + minVal;
		
		//System.out.println(meta + "\t" + largestMeta);
		//System.out.println("\t" + val);
		String hexString = UtilHex.convertRGBtoHexString(val, val, val);
		//System.out.println("\t" + hexString);
		int color = Integer.parseInt(hexString, 16);
		//System.out.println("\t" + color);
		
		return color;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity,
			ItemStack itemStack) {
		super.onBlockPlacedBy(world, x, y, z, entity, itemStack);
		
		int meta = 0;
		int y1 = y - 1;
		while (world.getBlock(x, y1, z) == ACBlocks.petrified_log) {
			world.setBlockMetadataWithNotify(x, y1, z, ++meta, 3);
			// System.out.println(y1 + ":" + meta);
			y1--;
		}
		
	}
	
}