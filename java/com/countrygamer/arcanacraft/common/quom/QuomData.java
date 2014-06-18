package com.countrygamer.arcanacraft.common.quom;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

import com.countrygamer.arcanacraft.common.block.ACBlocks;
import com.countrygamer.arcanacraft.common.extended.ExtendedArcanePlayer;
import com.countrygamer.arcanacraft.common.quom.Tiers.Cast;
import com.countrygamer.arcanacraft.common.tile.TileEntityActuator;
import com.countrygamer.countrygamercore.Base.common.block.BlockBase;
import com.countrygamer.countrygamercore.Base.common.tile.TileEntityTankBase;
import com.countrygamer.countrygamercore.lib.CoreUtil;

public class QuomData extends Quom {
	
	public QuomData(String name, Quom parent, int col, int row) {
		super(name, parent, col, row);
	}
	
	@Override
	public boolean onUse(EntityPlayer player, ExtendedArcanePlayer arcanePlayer, World world,
			int x, int y, int z, int side, Cast castTier) {
		Block block = world.getBlock(x, y, z);
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		
		if (player.isSneaking() && block instanceof BlockBase) {
			if (tileEntity instanceof TileEntityActuator) {
				((TileEntityActuator) tileEntity).shouldDropStats = true;
			}
			world.setBlockToAir(x, y, z);
			return true;
		}
		
		if (block == ACBlocks.deriver) {
			System.out.println("Has TE: " + (tileEntity != null));
		}
		
		if (tileEntity != null) {
			if (tileEntity instanceof TileEntityTankBase) {
				this.tankClick(player, (TileEntityTankBase) tileEntity);
				return true;
			}
		}
		
		return false;
	}
	
	private void tankClick(EntityPlayer player, TileEntityTankBase tileEntity) {
		if (tileEntity.canModifyTank()) {
			FluidStack storedFluid = tileEntity.getFluidStack();
			
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
	}
	
	@Override
	public void onRightClick(EntityPlayer player, ExtendedArcanePlayer arcanePlayer, World world,
			Cast castTier) {
	}
	
}
