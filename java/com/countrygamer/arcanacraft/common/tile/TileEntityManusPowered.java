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
	private final double radiusForSearch = 3.0;
	
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
		
		if (--this.ticksTillManusSearch <= 0) {
			// System.out.println("Searching...");
			resourceSearch:
			for (int y1 = this.yCoord - (int) this.radiusForSearch; y1 <= this.xCoord
					+ this.radiusForSearch; y1++) {
				for (int x1 = this.xCoord - (int) this.radiusForSearch; x1 <= this.xCoord
						+ this.radiusForSearch; x1++) {
					for (int z1 = this.zCoord - (int) this.radiusForSearch; z1 <= this.zCoord
							+ this.radiusForSearch; z1++) {
						// System.out.println("Loop");
						if (x1 * x1 + y1 * y1 + z1 * z1 > this.radiusForSearch
								* this.radiusForSearch * this.radiusForSearch) {
							// System.out.println("Too far");
							// continue;
						}
						
						if (x1 == this.xCoord && y1 == this.yCoord && z1 == this.zCoord) {
							continue;
						}
						
						TileEntity tileEntity = this.getWorldObj().getTileEntity(x1, y1, z1);
						if (tileEntity != null && tileEntity instanceof TileEntityAugmentedTank) {
							TileEntityAugmentedTank tankTE = (TileEntityAugmentedTank) tileEntity;
							
							FluidStack resource = tankTE.getFluidStack();
							if (resource != null
									&& resource.getFluid().equals(ACBlocks.liquidManus)) {
								FluidStack fillStack = new FluidStack(ACBlocks.liquidManus, 1000);
								int newAmount = this.fill(ForgeDirection.UNKNOWN, fillStack, false);
								fillStack.amount = newAmount;
								
								FluidStack stackToDrain = tankTE.drain(ForgeDirection.UNKNOWN,
										newAmount, false);
								if (stackToDrain == null) continue;
								
								if (fillStack.amount > stackToDrain.amount) {
									fillStack.amount = stackToDrain.amount;
								}
								
								tankTE.drain(ForgeDirection.UNKNOWN, fillStack.amount, true);
								this.fill(ForgeDirection.UNKNOWN, fillStack, true);
								break resourceSearch;
								
							}
						}
						
					}
				}
			}
			this.ticksTillManusSearch = this.ticksPerManusSearch;
		}
		
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tagCom) {
		super.writeToNBT(tagCom);
		tagCom.setInteger("ticksTillManusSearch", this.ticksTillManusSearch);
		
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCom) {
		super.readFromNBT(tagCom);
		this.ticksTillManusSearch = tagCom.getInteger("ticksTillManusSearch");
		
	}
	
}
