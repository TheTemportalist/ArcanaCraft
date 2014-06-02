package com.countrygamer.arcanacraft.common.lib;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class ACUtil {
	
	public static boolean itemTankIteraction(EntityPlayer player, IFluidHandler tankTileEntity) {
		return ACUtil.itemTankIteraction(player, tankTileEntity, player.getHeldItem());
	}
	
	public static boolean itemTankIteraction(EntityPlayer player, IFluidHandler tankTileEntity,
			ItemStack heldStack) {
		if (heldStack != null) {
			heldStack = heldStack.copy();
			FluidStack fluidInItem = FluidContainerRegistry.getFluidForFilledItem(heldStack);
			
			if (fluidInItem != null) {
				// Falsely fill the tank, checking for if it can fill fully
				int amount = tankTileEntity.fill(ForgeDirection.UNKNOWN, fluidInItem, false);
				if (amount == fluidInItem.amount) {
					// Can fill with the entire item, so fill
					tankTileEntity.fill(ForgeDirection.UNKNOWN, fluidInItem, true);
					
					if (!player.capabilities.isCreativeMode) {
						player.inventory.setInventorySlotContents(player.inventory.currentItem,
								decrementStack(heldStack));
						return true;
					}
					
					return true;
				}
				else {
					return false;
				}
			}
			else if (FluidContainerRegistry.isBucket(heldStack)) {
				FluidTankInfo[] tanks = tankTileEntity.getTankInfo(ForgeDirection.UNKNOWN);
				FluidStack fluidToFill = tanks[0].fluid;
				ItemStack fillingStack = FluidContainerRegistry.fillFluidContainer(fluidToFill,
						heldStack);
				if (fillingStack != null) {
					tankTileEntity
							.drain(ForgeDirection.UNKNOWN,
									FluidContainerRegistry.getFluidForFilledItem(fillingStack).amount,
									true);
					if (!player.capabilities.isCreativeMode) {
						if (heldStack.stackSize == 1) {
							player.inventory.setInventorySlotContents(player.inventory.currentItem,
									fillingStack);
							return true;
						}
						else {
							player.inventory.setInventorySlotContents(player.inventory.currentItem,
									decrementStack(heldStack));
							if (!player.inventory.addItemStackToInventory(fillingStack)) {
								player.dropPlayerItemWithRandomChoice(fillingStack, false);
							}
							return true;
						}
					}
					return true;
				}
				else {
					return false;
				}
			}
		}
		return false;
	}
	
	private static ItemStack decrementStack(ItemStack itemStack) {
		if (itemStack.stackSize == 1) {
			if (itemStack.getItem().getContainerItem() != null)
				return itemStack.getItem().getContainerItem(itemStack);
			else
				return null;
		}
		else {
			itemStack.splitStack(1);
			return itemStack;
		}
	}
	
	public static ItemStack addFluidWithGui(EntityPlayer player, IFluidHandler tankTileEntity,
			ItemStack heldStack) {
		if (heldStack != null) {
			heldStack = heldStack.copy();
			FluidStack fluidInItem = FluidContainerRegistry.getFluidForFilledItem(heldStack);
			
			if (fluidInItem != null) {
				// Falsely fill the tank, checking for if it can fill fully
				int amount = tankTileEntity.fill(ForgeDirection.UNKNOWN, fluidInItem, false);
				if (amount == fluidInItem.amount) {
					// Can fill with the entire item, so fill
					tankTileEntity.fill(ForgeDirection.UNKNOWN, fluidInItem, true);
					
					return decrementStack(heldStack);
				}
				else {
					return heldStack;
				}
			}
			else if (FluidContainerRegistry.isBucket(heldStack)) {
				FluidTankInfo[] tanks = tankTileEntity.getTankInfo(ForgeDirection.UNKNOWN);
				FluidStack fluidToFill = tanks[0].fluid;
				ItemStack fillingStack = FluidContainerRegistry.fillFluidContainer(fluidToFill,
						heldStack);
				if (fillingStack != null) {
					tankTileEntity
							.drain(ForgeDirection.UNKNOWN,
									FluidContainerRegistry.getFluidForFilledItem(fillingStack).amount,
									true);
					if (heldStack.stackSize == 1) {
						return fillingStack;
					}
					else {
						if (!player.inventory.addItemStackToInventory(fillingStack)) {
							player.dropPlayerItemWithRandomChoice(fillingStack, false);
						}
						return decrementStack(heldStack);
					}
				}
				else {
					return heldStack;
				}
			}
		}
		return heldStack;
	}
	
}
