package com.countrygamer.arcanacraft.common.tile;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

import com.countrygamer.arcanacraft.common.block.ACBlocks;
import com.countrygamer.core.Base.common.tileentity.TileEntityBase;

public class TileEntityAugmentedTank extends TileEntityBase {
	
	// private FluidTank tank;
	
	public TileEntityAugmentedTank() {
		super(FluidContainerRegistry.BUCKET_VOLUME * 10);
		// this.tank = new FluidTank(FluidContainerRegistry.BUCKET_VOLUME * 10);
	}
	
	/*
	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		int fluidAmount = this.tank.fill(resource, doFill);
		
		if (fluidAmount > 0 && doFill) {
			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		}
		
		return fluidAmount;
	}
	
	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return null;
	}
	
	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		FluidStack fluidStack = tank.drain(maxDrain, doDrain);
		
		if (fluidStack != null && doDrain) {
			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		}
		
		return null;
	}
	
	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return this.tank.getFluidAmount() < this.tank.getCapacity();
	}
	
	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return this.tank.getFluidAmount() > 0;
	}
	
	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		FluidStack containedFluid = null;
		if (this.tank.getFluid() != null) containedFluid = tank.getFluid().copy();
		return new FluidTankInfo[] {
			new FluidTankInfo(containedFluid, tank.getCapacity())
		};
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tagCom) {
		super.writeToNBT(tagCom);
		FluidStack containedFluid = this.tank.getFluid();
		tagCom.setBoolean("hasFluid", containedFluid != null);
		if (containedFluid != null) {
			tagCom.setInteger("fluidID", containedFluid.fluidID);
			tagCom.setInteger("fluidAmount", containedFluid.amount);
		}
		
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCom) {
		super.readFromNBT(tagCom);
		if (tagCom.getBoolean("hasFluid")) {
			this.tank.setFluid(new FluidStack(tagCom.getInteger("fluidID"), tagCom
					.getInteger("fluidAmount")));
		}
		else {
			this.tank.setFluid(null);
		}
		
	}
	
	public FluidStack getFluidStored() {
		return this.tank.getFluid();
	}
	
	public Fluid getFluidStored_Type() {
		return this.getFluidStored() != null ? this.getFluidStored().getFluid() : null;
	}
	
	public int getFluidStored_Amount() {
		return this.getFluidStored() != null ? this.getFluidStored().amount : 0;
	}
	 */
	public double getFluidRatio() {
		FluidStack fluidStack = this.getFluidStack();
		if (fluidStack != null) {
			return (double) fluidStack.amount / (double) this.getTankCapacity();
		}
		return 0;
	}
	
	@Override
	public void getTileEntityDrops(ArrayList<ItemStack> drops) {
		ItemStack dropStack = new ItemStack(ACBlocks.augmentedTank, 1, 0);
		if (this.getFluidStack() != null && this.getFluidStack().amount > 0) {
			NBTTagCompound tankTag = new NBTTagCompound();
			this.writeTankToNBT(tankTag);
			// ArcanaCraft.logger.info("Wrote");
			if (!dropStack.hasTagCompound()) dropStack.setTagCompound(new NBTTagCompound());
			dropStack.getTagCompound().setTag("tank", tankTag);
		}
		drops.clear();
		drops.add(dropStack);
	}
	
}
