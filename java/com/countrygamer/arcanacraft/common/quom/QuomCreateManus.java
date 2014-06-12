package com.countrygamer.arcanacraft.common.quom;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;

import com.countrygamer.arcanacraft.common.block.ACBlocks;
import com.countrygamer.arcanacraft.common.extended.ExtendedArcanePlayer;
import com.countrygamer.arcanacraft.common.quom.Tiers.Cast;
import com.countrygamer.countrygamercore.lib.CoreUtil;

public class QuomCreateManus extends QuomCreative {
	
	public QuomCreateManus(String name) {
		super(name);
	}
	
	@Override
	public boolean onUse(EntityPlayer player, ExtendedArcanePlayer arcanePlayer, World world,
			int x, int y, int z, int side, Cast castTier) {
		this.checkData(arcanePlayer);
		
		NBTTagCompound data = arcanePlayer.getData("Quom_" + this.getName());
		
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (tileEntity != null && tileEntity instanceof IFluidHandler) {
			((IFluidHandler) tileEntity).fill(ForgeDirection.UNKNOWN, new FluidStack(
					ACBlocks.liquidManus, data.getInteger("manusAmount")), true);
			return true;
		}
		
		/*
		if (data.getInteger("manusCreationAmount") >= 1000) {
			int[] blockCoordsBySide = UtilCursor.getNewCoordsFromSide(x, y, z, side);
			world.setBlock(blockCoordsBySide[0], blockCoordsBySide[1], blockCoordsBySide[2], , p_147465_5_, p_147465_6_)
		}
		 */
		
		return false;
	}
	
	@Override
	public void onRightClick(EntityPlayer player, ExtendedArcanePlayer arcanePlayer, World world,
			Cast castTier) {
		this.checkData(arcanePlayer);
		
		NBTTagCompound data = arcanePlayer.getData("Quom_" + this.getName());
		
		if (player.isSneaking()) {
			int manusCreationAmount = data.getInteger("manusAmount");
			
			if (manusCreationAmount >= 1000) {
				manusCreationAmount = 100;
			}
			else {
				manusCreationAmount += 100;
			}
			
			CoreUtil.sendMessageToPlayer(player, "Manus amount = " + manusCreationAmount);
			
			data.setInteger("manusAmount", manusCreationAmount);
		}
		else {
			if (arcanePlayer.getManus() < arcanePlayer.getMaxManus()) {
				arcanePlayer.setManus(arcanePlayer.getMaxManus());
			}
		}
		
		arcanePlayer.addData("Quom_" + this.getName(), data);
		
	}
	
	private void checkData(ExtendedArcanePlayer arcanePlayer) {
		NBTTagCompound data = arcanePlayer.getData("Quom_" + this.getName());
		
		if (data == null) {
			data = new NBTTagCompound();
			data.setInteger("manusAmount", 100);
			arcanePlayer.addData("Quom_" + this.getName(), data);
		}
	}
	
}
