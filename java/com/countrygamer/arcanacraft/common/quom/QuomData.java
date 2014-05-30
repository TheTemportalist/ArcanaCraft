package com.countrygamer.arcanacraft.common.quom;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

import com.countrygamer.arcanacraft.common.block.ACBlocks;
import com.countrygamer.arcanacraft.common.extended.ExtendedArcanePlayer;
import com.countrygamer.arcanacraft.common.quom.Tiers.Cast;
import com.countrygamer.arcanacraft.common.tile.TileEntityAugmentedTank;
import com.countrygamer.core.Base.common.block.BlockBase;
import com.countrygamer.countrygamercore.lib.CoreUtil;

public class QuomData extends Quom {
	
	public QuomData(String name, Quom parent) {
		super(name, parent);
	}
	
	@Override
	public boolean onUse(EntityPlayer player, ExtendedArcanePlayer arcanePlayer, World world, int x,
			int y, int z, int side, Cast castTier) {
		Block block = world.getBlock(x, y, z);
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		
		if (player.isSneaking() && block instanceof BlockBase) {
			world.setBlockToAir(x, y, z);
			return true;
		}
		
		if (block == ACBlocks.augmentedTank && tileEntity != null
				&& tileEntity instanceof TileEntityAugmentedTank) {
			TileEntityAugmentedTank tankTE = (TileEntityAugmentedTank) tileEntity;
			
			FluidStack storedFluid = tankTE.getFluidStored();
			String message = "";
			if (storedFluid != null) {
				message = "That tank is holding " + storedFluid.amount + " mB of "
						+ storedFluid.getFluid().getName();
			}
			else {
				message = "That tank contains no fluid";
			}
			
			CoreUtil.sendMessageToPlayer(player, message);
			return true;
		}
		
		return false;
	}

	
	@Override
	public void onRightClick(EntityPlayer player, ExtendedArcanePlayer arcanePlayer, World world,
			Cast castTier) {
	}
	
}
