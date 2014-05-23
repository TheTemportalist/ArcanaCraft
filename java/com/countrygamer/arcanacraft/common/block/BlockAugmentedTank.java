package com.countrygamer.arcanacraft.common.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

import com.countrygamer.arcanacraft.common.tile.TileEntityAugmentedTank;
import com.countrygamer.core.Base.common.block.BlockContainerBase;

public class BlockAugmentedTank extends BlockContainerBase {
	
	public BlockAugmentedTank(Material mat, String modid, String name,
			Class<? extends TileEntity> tileEntityClass) {
		super(mat, modid, name, tileEntityClass);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player,
			int side, float x1, float y1, float z1) {
		ItemStack heldStack = player.getHeldItem();
		if (heldStack != null) {
			heldStack = heldStack.copy();
			TileEntityAugmentedTank tankTE = (TileEntityAugmentedTank) world.getTileEntity(x, y, z);
			
			FluidStack fluidInItem = FluidContainerRegistry.getFluidForFilledItem(heldStack);
			
			if (fluidInItem != null) {
				// Falsely fill the tank, checking for if it can fill fully
				int amount = tankTE.fill(ForgeDirection.UNKNOWN, fluidInItem, false);
				if (amount == fluidInItem.amount) {
					// Can fill with the entire item, so fill
					tankTE.fill(ForgeDirection.UNKNOWN, fluidInItem, true);
					
					if (!player.capabilities.isCreativeMode) {
						player.inventory.setInventorySlotContents(player.inventory.currentItem,
								decrementStack(heldStack));
					}
					
					return true;
				}
				else {
					return true;
				}
			}
			else if (FluidContainerRegistry.isBucket(heldStack)) {
				FluidTankInfo[] tanks = tankTE.getTankInfo(ForgeDirection.UNKNOWN);
				FluidStack fluidToFill = tanks[0].fluid;
				ItemStack fillingStack = FluidContainerRegistry.fillFluidContainer(fluidToFill,
						heldStack);
				if (fillingStack != null) {
					tankTE.drain(ForgeDirection.UNKNOWN,
							FluidContainerRegistry.getFluidForFilledItem(fillingStack).amount, true);
					if (!player.capabilities.isCreativeMode) {
						if (heldStack.stackSize == 1) {
							player.inventory.setInventorySlotContents(player.inventory.currentItem,
									fillingStack);
						}
						else {
							player.inventory.setInventorySlotContents(0, decrementStack(heldStack));
							if (!player.inventory.addItemStackToInventory(fillingStack)) {
								player.dropPlayerItemWithRandomChoice(fillingStack, false);
							}
						}
					}
					return true;
				}
				else {
					return true;
				}
			}
		}
		
		return false;
	}
	
	private ItemStack decrementStack(ItemStack itemStack) {
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
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return -1;
	}
	
}
