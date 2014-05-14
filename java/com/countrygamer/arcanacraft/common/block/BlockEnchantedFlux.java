package com.countrygamer.arcanacraft.common.block;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockEnchantedFlux extends BlockFluidClassic {
	
	@SideOnly(Side.CLIENT)
	IIcon iconStill, iconFlow;
	
	public BlockEnchantedFlux(Fluid fluid, Material mat) {
		super(fluid, mat);
		this.setBlockName("Enchanted Flux");
		
	}
	
	public IIcon getIcon(int side, int meta) {
		return (side == 0 || side == 1) ? this.iconStill : this.iconFlow;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister register) {
		this.iconStill = register.registerIcon("arcanacraft:enchantedflux");
		this.iconFlow = register.registerIcon("arcanacraft:enchantedflux");
	}
	
	@Override
	public boolean canDisplace(IBlockAccess world, int x, int y, int z) {
		if (world.getBlock(x, y, z).getMaterial().isLiquid()) return false;
		return super.canDisplace(world, x, y, z);
	}
	
	@Override
	public boolean displaceIfPossible(World world, int x, int y, int z) {
		if (world.getBlock(x, y, z).getMaterial().isLiquid()) return false;
		return super.displaceIfPossible(world, x, y, z);
	}
	
}
