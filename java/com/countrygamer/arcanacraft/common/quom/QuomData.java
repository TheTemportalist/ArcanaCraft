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
import com.countrygamer.countrygamercore.lib.CoreUtil;

public class QuomData extends Quom {
	
	public QuomData(String name, String parentKey) {
		super(name, parentKey);
	}
	
	@Override
	public void onUse(EntityPlayer player, ExtendedArcanePlayer arcanePlayer, World world, int x,
			int y, int z, int side, Cast castTier) {
		Block block = world.getBlock(x, y, z);
		
		if (block == ACBlocks.augmentedTank) {
			TileEntity tileEntity = world.getTileEntity(x, y, z);
			if (tileEntity != null && tileEntity instanceof TileEntityAugmentedTank) {
				TileEntityAugmentedTank tankTE = (TileEntityAugmentedTank) tileEntity;
				
				if (!player.isSneaking()) {
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
				}
				else {
					//block.dismantle();
				}
			}
		}
		
	}
	
}
