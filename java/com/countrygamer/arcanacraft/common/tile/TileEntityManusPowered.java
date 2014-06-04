package com.countrygamer.arcanacraft.common.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import com.countrygamer.arcanacraft.common.block.ACBlocks;
import com.countrygamer.countrygamercore.Base.common.tileentity.TileEntityInventoryBase;

public class TileEntityManusPowered extends TileEntityInventoryBase {
	
	private final int ticksPerManusSearch = 20 * 5;
	private int ticksTillManusSearch = 0;
	private int[] sourceTankCoords = null;
	
	public TileEntityManusPowered(String name, int inventorySize, int maxStackSize) {
		super(name, inventorySize, maxStackSize);
		this.setTank(new FluidTank(FluidContainerRegistry.BUCKET_VOLUME * 10));
		this.ticksTillManusSearch = this.ticksPerManusSearch;
		
	}
	
	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if (resource.getFluid().equals(ACBlocks.liquidManus))
			return super.fill(from, resource, doFill);
		else
			return 0;
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if (this.canHoldMoreFluid() && this.sourceTankCoords != null) {
			if (--this.ticksTillManusSearch <= 0) {
				// System.out.println("Searching...");
				
				TileEntity tileEntity = this.getWorldObj().getTileEntity(this.sourceTankCoords[0],
						this.sourceTankCoords[1], this.sourceTankCoords[2]);
				if (tileEntity != null && tileEntity instanceof TileEntityAugmentedTank) {
					TileEntityAugmentedTank sourceTankTE = (TileEntityAugmentedTank) tileEntity;
					
					FluidStack resource = sourceTankTE.getFluidStack();
					if (resource != null && resource.getFluid().equals(ACBlocks.liquidManus)) {
						FluidStack fillStack = new FluidStack(ACBlocks.liquidManus, 1000);
						int newAmount = this.fill(ForgeDirection.UNKNOWN, fillStack, false);
						fillStack.amount = newAmount;
						
						FluidStack stackToDrain = sourceTankTE.drain(ForgeDirection.UNKNOWN,
								newAmount, false);
						if (stackToDrain != null) {
							if (fillStack.amount > stackToDrain.amount) {
								fillStack.amount = stackToDrain.amount;
							}
							
							// System.out.println("Draining");
							sourceTankTE.drain(ForgeDirection.UNKNOWN, fillStack.amount, true);
							this.fill(ForgeDirection.UNKNOWN, fillStack, true);
						}
					}
				}
				
				this.ticksTillManusSearch = this.ticksPerManusSearch;
			}
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tagCom) {
		super.writeToNBT(tagCom);
		tagCom.setInteger("ticksTillManusSearch", this.ticksTillManusSearch);
		
		tagCom.setBoolean("hasTank", this.sourceTankCoords != null);
		if (this.sourceTankCoords != null) {
			tagCom.setInteger("sourceTankX", this.sourceTankCoords[0]);
			tagCom.setInteger("sourceTankY", this.sourceTankCoords[1]);
			tagCom.setInteger("sourceTankZ", this.sourceTankCoords[2]);
			
		}
		
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCom) {
		super.readFromNBT(tagCom);
		this.ticksTillManusSearch = tagCom.getInteger("ticksTillManusSearch");
		
		if (tagCom.getBoolean("hasTank")) {
			this.sourceTankCoords = new int[] {
					tagCom.getInteger("sourceTankX"), tagCom.getInteger("sourceTankY"),
					tagCom.getInteger("sourceTankZ")
			};
			
		}
		else if (this.sourceTankCoords != null) {
			this.sourceTankCoords = null;
		}
		
	}
	
	public void setSourceTankCoords(int[] coords) {
		this.sourceTankCoords = coords;
	}
	
	public void clearSourceTank() {
		this.sourceTankCoords = null;
	}
	
}
