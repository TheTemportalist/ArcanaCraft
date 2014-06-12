package com.countrygamer.arcanacraft.common.tile;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

import com.countrygamer.arcanacraft.common.quom.Quom;
import com.countrygamer.arcanacraft.common.quom.QuomRegistry;
import com.countrygamer.arcanacraft.common.recipes.BinderRecipes;
import com.countrygamer.arcanacraft.common.recipes.EnumBinderType;
import com.countrygamer.countrygamercore.lib.UtilDrops;

public class TileEntityBinder extends TileEntityManusPowered {
	
	public EnumBinderType binderType;
	
	private Quom quom;
	
	public TileEntityBinder() {
		super("Binder", 3, 64);
	}
	
	public void setBinderType(EnumBinderType type) {
		this.binderType = type;
		this.setName(this.binderType.toString() + " Binder");
		
	}
	
	public boolean canModifyBindingTank() {
		return this.binderType == EnumBinderType.FLUID;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tagCom) {
		super.writeToNBT(tagCom);
		
		tagCom.setInteger("binderTypeID", this.binderType.getID());
		
		tagCom.setBoolean("hasQuom", this.quom != null);
		if (this.quom != null) {
			tagCom.setInteger("quomID", this.quom.getID());
		}
		
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCom) {
		super.readFromNBT(tagCom);
		
		this.binderType = EnumBinderType.getTypeFromID(tagCom.getInteger("binderTypeID"));
		
		if (tagCom.getBoolean("hasQuom")) {
			this.quom = QuomRegistry.quomRegistry.get(tagCom.getInteger("quomID")).copy();
		}
		
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if (this.binderType == null) {
			System.out.println("Error");
			return;
		}
		
		if (this.getStackInSlot(0) != null) {
			ItemStack input = this.getStackInSlot(0).copy();
			Object binding = null;
			
			switch (this.binderType) {
				case QUOM:
					binding = this.quom != null ? this.quom.copy() : null;
					break;
				case ITEM:
					if (this.getStackInSlot(1) != null) {
						binding = this.getStackInSlot(1).copy();
					}
					break;
				case FLUID:
					TileEntity tileEntity = this.getWorldObj().getTileEntity(this.xCoord,
							this.yCoord - 1, this.zCoord);
					if (tileEntity != null && tileEntity instanceof TileEntityAugmentedTank) {
						binding = ((TileEntityAugmentedTank) tileEntity).getFluidStack();
					}
					break;
				default:
					binding = null;
					break;
			
			}
			
			// if (binding != null) {
			Object[] recipeOutputInfo = BinderRecipes.getRecipes().getRecipeOutput(this.binderType,
					input, binding);
			if (recipeOutputInfo != null) {
				ItemStack output = (ItemStack) recipeOutputInfo[0];
				// System.out.println("Valid output: " + (output != null));
				int requiredFluidAmount = (Integer) recipeOutputInfo[1];
				int bindingAmountRequired = (Integer) recipeOutputInfo[2];
				int manusCost = (Integer) recipeOutputInfo[3];
				int inputStackSizeRequired = (Integer) recipeOutputInfo[4];
				
				if (output != null) {
					boolean canBind = true;
					
					FluidStack manusStack = this.getFluidStack();
					if (manusCost > 0 && (manusStack == null || manusStack.amount < manusCost)) {
						System.out.println("Not Enough Manus");
						canBind = false;
					}
					else if (input.stackSize < inputStackSizeRequired) {
						canBind = false;
					}
					else if (this.binderType == EnumBinderType.ITEM) {
						ItemStack bindingStack = this.getStackInSlot(1);
						if (bindingStack == null || bindingStack.stackSize > bindingAmountRequired) {
							System.out.println("Invalid binding Stack");
							canBind = false;
						}
					}
					else if (this.binderType == EnumBinderType.FLUID) {
						FluidStack bindingFluid = (FluidStack) binding;
						if (bindingFluid == null || bindingFluid.amount < requiredFluidAmount) {
							canBind = false;
						}
					}
					if (canBind) {
						if (this.getStackInSlot(2) != null) {
							ItemStack prevOutput = this.getStackInSlot(2).copy();
							if (output.getItem() == prevOutput.getItem()
									&& output.getItemDamage() == prevOutput.getItemDamage()) {
								if (ItemStack.areItemStackTagsEqual(output, prevOutput)) {
									if (prevOutput.stackSize + output.stackSize <= output
											.getMaxStackSize()) {
										this.setInventorySlotContents(2,
												new ItemStack(output.getItem(),
														prevOutput.stackSize + output.stackSize,
														output.getItemDamage()));
										this.hasBinded(manusCost, requiredFluidAmount,
												bindingAmountRequired, inputStackSizeRequired);
									}
								}
							}
						}
						else {
							this.setInventorySlotContents(2, output.copy());
							this.hasBinded(manusCost, requiredFluidAmount, bindingAmountRequired,
									inputStackSizeRequired);
						}
					}
				}
			}
			// }
		}
	}
	
	private void hasBinded(int manusToDrain, int amountToDrain, int requiredBindingStackSize,
			int requiredInputStackSize) {
		ItemStack input = this.getStackInSlot(0).copy();
		ItemStack input2 = this.getStackInSlot(1);
		if (input2 != null) input2 = input2.copy();
		
		if (FluidContainerRegistry.isContainer(input)
				&& !FluidContainerRegistry.isEmptyContainer(input)) {
			ItemStack emptyContainerStack = input.getItem().getContainerItem(input);
			emptyContainerStack.stackSize -= requiredInputStackSize;
			if (emptyContainerStack != null) {
				if (input.stackSize > 1) {
					UtilDrops.spawnItemStack(this.getWorldObj(), this.xCoord + 0.5,
							this.yCoord + 1, this.zCoord + 0.5, emptyContainerStack, new Random());
				}
				else {
					this.setInventorySlotContents(0, emptyContainerStack.copy());
				}
			}
		}
		else {
			input = new ItemStack(input.getItem(), input.stackSize - requiredInputStackSize,
					input.getItemDamage());
			this.setInventorySlotContents(0, input.stackSize > 0 ? input.copy() : null);
		}
		
		if (input2 != null) {
			input2 = new ItemStack(input2.getItem(), input2.stackSize - requiredBindingStackSize,
					input2.getItemDamage());
			this.setInventorySlotContents(1, input2.stackSize > 0 ? input2.copy() : null);
		}
		
		this.drain(ForgeDirection.UNKNOWN, manusToDrain, true);
		
		if (this.binderType == EnumBinderType.FLUID) {
			TileEntity tileEntity = this.getWorldObj().getTileEntity(this.xCoord, this.yCoord - 1,
					this.zCoord);
			((TileEntityAugmentedTank) tileEntity).drain(ForgeDirection.UNKNOWN, amountToDrain,
					true);
		}
		
	}
	
	public Quom getBindingQuom() {
		return this.quom;
	}
	
	public void setBindingQuom(Quom quom) {
		this.quom = quom;
		this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
	}
	
	@Override
	public void getTileEntityDrops(ArrayList<ItemStack> drops) {
		drops.clear();
		
		drops.add(new ItemStack(this.getWorldObj().getBlock(this.xCoord, this.yCoord, this.zCoord),
				1, this.getWorldObj().getBlockMetadata(this.xCoord, this.yCoord, this.zCoord)));
		
		for (int i = 0; i < this.getSizeInventory(); i++) {
			if (this.getStackInSlot(i) != null) drops.add(this.getStackInSlot(i).copy());
		}
		
	}
	
}
