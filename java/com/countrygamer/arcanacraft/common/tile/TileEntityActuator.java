package com.countrygamer.arcanacraft.common.tile;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import com.countrygamer.arcanacraft.common.block.ACBlocks;
import com.countrygamer.countrygamercore.Base.common.tile.TileEntityInventoryBase;
import com.countrygamer.countrygamercore.lib.UtilDrops;

import cpw.mods.fml.common.eventhandler.Event;

public class TileEntityActuator extends TileEntityInventoryBase {
	
	ActuatorFakePlayer player;
	
	public TileEntityActuator() {
		super(ACBlocks.actuator.getUnlocalizedName(), 1, 64);
		
	}
	
	@Override
	public void updateEntity() {
		this.player = new ActuatorFakePlayer(this);
		this.player.onUpdate();
		
	}
	
	private int getSideHit() {
		return ForgeDirection.getOrientation(this.getBlockMetadata()).getOpposite().ordinal();
	}
	
	private int[] getCoordsOfHit(int side) {
		int x = this.xCoord;
		int y = this.yCoord;
		int z = this.zCoord;
		
		if (side == ForgeDirection.DOWN.ordinal()) {
			y += 1;
		}
		else if (side == ForgeDirection.UP.ordinal()) {
			y -= 1;
		}
		else if (side == ForgeDirection.NORTH.ordinal()) {
			z += 1;
		}
		else if (side == ForgeDirection.SOUTH.ordinal()) {
			z -= 1;
		}
		else if (side == ForgeDirection.EAST.ordinal()) {
			x -= 1;
		}
		else if (side == ForgeDirection.WEST.ordinal()) {
			x += 1;
		}
		
		return new int[] {
				x, y, z
		};
	}
	
	private float[] getHitVectors(int side) {
		float hitX = 0.5F;
		float hitY = 0.5F;
		float hitZ = 0.5F;
		
		if (side == ForgeDirection.DOWN.ordinal()) {
			hitY = 0.0F;
		}
		else if (side == ForgeDirection.UP.ordinal()) {
			hitY = 1.0F;
		}
		else if (side == ForgeDirection.NORTH.ordinal()) {
			hitZ = 1.0F;
		}
		else if (side == ForgeDirection.SOUTH.ordinal()) {
			hitZ = 0.0F;
		}
		else if (side == ForgeDirection.EAST.ordinal()) {
			hitX = 0.0F;
		}
		else if (side == ForgeDirection.WEST.ordinal()) {
			hitX = 1.0F;
		}
		
		return new float[] {
				hitX, hitY, hitZ
		};
	}
	
	public void rightClick() {
		ItemStack usageStack = this.getStackInSlot(0);
		
		if (usageStack != null) {
			ItemStack itemStack = usageStack.copy();
			
			int side = this.getSideHit();
			
			int[] hitCoords = this.getCoordsOfHit(side);
			int x = hitCoords[0];
			int y = hitCoords[1];
			int z = hitCoords[2];
			
			if (!this.placeBlock(itemStack, x, y, z)) {
				if (!this.useItem(itemStack, x, y, z, side)) {
					ItemStack customStackResult = this.handleRightClick(itemStack, x, y, z, side);
					if (ItemStack.areItemStacksEqual(itemStack, customStackResult)) {
						ItemStack newItemStack = itemStack.useItemRightClick(this.getWorldObj(),
								this.player);
						itemStack = newItemStack.copy();
					}
					else {
						itemStack = customStackResult.copy();
					}
				}
				this.setInventorySlotContents(0, itemStack);
			}
			
		}
		
	}
	
	private boolean placeBlock(ItemStack itemStack, int x, int y, int z) {
		if (Block.getBlockFromItem(itemStack.getItem()) != Blocks.air) {
			Block block = Block.getBlockFromItem(itemStack.getItem());
			int meta = itemStack.getItemDamage();
			
			Block blockAtCoord = this.getWorldObj().getBlock(x, y, z);
			if (blockAtCoord == Blocks.air || blockAtCoord.getMaterial().isLiquid()) {
				this.getWorldObj().setBlock(x, y, z, block, meta, 3);
				this.decrStackSize(0, 1);
				return true;
			}
		}
		return false;
	}
	
	private boolean useItem(ItemStack itemStack, int x, int y, int z, int side) {
		float[] hitVecs = this.getHitVectors(side);
		if (itemStack.getItem().onItemUseFirst(itemStack, this.player, this.getWorldObj(), x, y, z,
				side, hitVecs[0], hitVecs[1], hitVecs[2])) {
			return true;
		}
		return this
				.getWorldObj()
				.getBlock(x, y, z)
				.onBlockActivated(this.getWorldObj(), x, y, z, this.player, side, hitVecs[0],
						hitVecs[1], hitVecs[2]);
	}
	
	private ItemStack handleRightClick(ItemStack itemStack, int x, int y, int z, int side) {
		if (FluidContainerRegistry.isContainer(itemStack)) {
			Block block = this.getWorldObj().getBlock(x, y, z);
			
			if (FluidContainerRegistry.isFilledContainer(itemStack)) {
				if (block == Blocks.air) {
					FluidStack fluidStack = FluidContainerRegistry.getFluidForFilledItem(itemStack);
					Block fluidBlock = null;
					if (fluidStack.getFluid() == FluidRegistry.WATER) {
						fluidBlock = Blocks.flowing_water;
					}
					else if (fluidStack.getFluid() == FluidRegistry.LAVA) {
						fluidBlock = Blocks.flowing_lava;
					}
					if (fluidBlock != null) {
						this.getWorldObj().setBlock(x, y, z, fluidBlock, 0, 3);
						
						return new ItemStack(Items.bucket);
					}
				}
			}
			else {
				MovingObjectPosition mop = this.getMOP(x, y, z, side);
				
				FillBucketEvent event = new FillBucketEvent(this.player, itemStack,
						this.getWorldObj(), mop);
				if (MinecraftForge.EVENT_BUS.post(event)) {
					return itemStack;
				}
				
				if (event.getResult() == Event.Result.ALLOW) {
					if (--itemStack.stackSize <= 0) {
						return event.result;
					}
					
					if (this.canInsertItem(0, event.result, -1)) {
						this.addToStack(0, itemStack);
					}
					else {
						UtilDrops.spawnItemStack(this.getWorldObj(), this.xCoord, this.yCoord,
								this.zCoord, event.result.copy(), new Random());
					}
					return itemStack;
				}
				
				int blockMeta = this.getWorldObj().getBlockMetadata(x, y, z);
				
				if (blockMeta == 0) {
					Material blockMat = block.getMaterial();
					if (blockMat == Material.water || blockMat == Material.lava) {
						this.getWorldObj().setBlockToAir(x, y, z);
						
						ItemStack retStack = new ItemStack(
								blockMat == Material.water ? Items.water_bucket : Items.lava_bucket);
						
						if (--itemStack.stackSize <= 0) {
							return retStack.copy();
						}
						else {
							UtilDrops.spawnItemStack(this.getWorldObj(), this.xCoord, this.yCoord,
									this.zCoord, event.result.copy(), new Random());
							return itemStack;
						}
						
					}
				}
				
			}
		}
		return itemStack;
	}
	
	private MovingObjectPosition getMOP(int x, int y, int z, int side) {
		float[] hitVecs = this.getHitVectors(side);
		return new MovingObjectPosition(x, y, z, side, this.getWorldObj().getWorldVec3Pool()
				.getVecFromPool(hitVecs[0], hitVecs[1], hitVecs[2]));
	}
	
}
