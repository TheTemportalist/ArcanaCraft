package com.countrygamer.arcanacraft.common.tile;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import com.countrygamer.arcanacraft.common.block.ACBlocks;
import com.countrygamer.countrygamercore.base.common.tile.ICamouflage;
import com.countrygamer.countrygamercore.base.common.tile.TileEntityInventoryBase;
import com.countrygamer.countrygamercore.common.lib.Activity;
import com.countrygamer.countrygamercore.common.lib.ItemMeta;
import com.countrygamer.countrygamercore.common.lib.util.UtilDrops;

import cpw.mods.fml.common.eventhandler.Event;

public class TileEntityActuator extends TileEntityInventoryBase implements ICamouflage {
	
	ActuatorFakePlayer player;
	int mouseButton;
	public boolean shouldDropStats = false;
	
	public TileEntityActuator() {
		super(ACBlocks.actuator.getUnlocalizedName(), 2, 64);
		this.mouseButton = 0;
		
	}
	
	@Override
	public void updateEntity() {
		if (!this.getWorldObj().isRemote
				&& this.getWorldObj().getTotalWorldTime() % (16L * 3) == 0L) {
			this.updatePlayer();
		}
		else if (this.player != null) {
			if (this.canRun() && this.getActivity() == Activity.WHILE) {
				this.doAction();
			}
		}
		
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tagCom) {
		super.writeToNBT(tagCom);
		
		this.writeStatsToNBT(tagCom);
		
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCom) {
		super.readFromNBT(tagCom);
		
		this.readStatsFromNBT(tagCom);
		
	}
	
	public void writeStatsToNBT(NBTTagCompound tagCom) {
		tagCom.setInteger("click_type", this.mouseButton);
		
	}
	
	public void readStatsFromNBT(NBTTagCompound tagCom) {
		this.mouseButton = tagCom.getInteger("click_type");
		
	}
	
	@Override
	public void onPowerChanged() {
		if (this.canRun() && this.getActivity() == Activity.PULSE) {
			this.doAction();
		}
	}
	
	private void updatePlayer() {
		if (this.player == null) {
			this.player = new ActuatorFakePlayer(this);
		}
		
		this.player.inventory.mainInventory = new ItemStack[36];
		for (int i = 0; i < this.getSizeInventory(); i++) {
			this.player.inventory.mainInventory[i] = this.getStackInSlot(i);
		}
		
		double x = this.xCoord + 0.5;
		double y = this.yCoord - 1.1D;
		double z = this.zCoord + 0.5;
		// TODO yaw & pitch
		float yaw = 0;
		float pitch = 0;
		this.player.setPositionAndRotation(x, y, z, yaw, pitch);
		
		this.player.isSneaking = false;
		this.player.yOffset = -1.1F;
		this.player.setCurrentSlot(0);
		
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
	
	public void doAction() {
		switch (this.mouseButton) {
			case 0:
				this.leftClick();
				break;
			case 1:
				this.rightClick();
				break;
			case 2:
				
				break;
			default:
				break;
		}
	}
	
	public void rightClick() {
		ItemStack itemStack = this.getStackInSlot(0);
		
		int side = this.getSideHit();
		
		int[] hitCoords = this.getCoordsOfHit(side);
		int x = hitCoords[0];
		int y = hitCoords[1];
		int z = hitCoords[2];
		
		if (!this.placeBlock(itemStack, x, y, z)) {
			if (!this.useItem(itemStack, x, y, z, side)) {
				if (itemStack != null) {
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
		if (itemStack == null) return false;
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
		if (itemStack != null
				&& itemStack.getItem().onItemUseFirst(itemStack, this.player, this.getWorldObj(),
						x, y, z, side, hitVecs[0], hitVecs[1], hitVecs[2])) {
			return true;
		}
		
		boolean ret = this
				.getWorldObj()
				.getBlock(x, y, z)
				.onBlockActivated(this.getWorldObj(), x, y, z, this.player, side, hitVecs[0],
						hitVecs[1], hitVecs[2]);
		this.setInventorySlotContents(0, this.player.getHeldItem());
		return ret;
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
	
	public void leftClick() {
		
	}
	
	private MovingObjectPosition getMOP(int x, int y, int z, int side) {
		float[] hitVecs = this.getHitVectors(side);
		return new MovingObjectPosition(x, y, z, side, Vec3.createVectorHelper(hitVecs[0],
				hitVecs[1], hitVecs[2]));
	}
	
	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int side) {
		if (side == ForgeDirection.getOrientation(this.getBlockMetadata()).ordinal()) return false;
		return i != 1 ? super.canInsertItem(i, itemStack, side) : false;
	}
	
	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int side) {
		if (side == ForgeDirection.getOrientation(this.getBlockMetadata()).ordinal()) return false;
		return i != 1;
	}
	
	@Override
	public void getTileEntityDrops(ArrayList<ItemStack> drops) {
		drops.clear();
		System.out.println("Drops");
		Block block = this.getWorldObj().getBlock(this.xCoord, this.yCoord, this.zCoord);
		ItemStack dropStack = new ItemStack(block, 1, 0);
		
		NBTTagCompound tagCom = new NBTTagCompound();
		tagCom.setBoolean("hasStats", this.shouldDropStats);
		System.out.println(this.shouldDropStats);
		if (this.shouldDropStats) {
			NBTTagCompound stats = new NBTTagCompound();
			
			this.writeInventoryToNBT(stats);
			this.writeStatsToNBT(stats);
			
			tagCom.setTag("stats", stats);
		}
		dropStack.setTagCompound(tagCom);
		
		drops.add(dropStack.copy());
		
		for (int i = 0; i < this.getSizeInventory(); i++) {
			if (i == 1) continue;
			ItemStack stack = this.getStackInSlot(i);
			if (stack != null) {
				drops.add(stack.copy());
			}
		}
	}
	
	@Override
	public boolean isCamouflaged() {
		return this.getStackInSlot(1) != null;
	}
	
	@Override
	public ItemMeta getCamouflage() {
		if (this.isCamouflaged()) return ItemMeta.getFromStack(this.getStackInSlot(1));
		return null;
	}
	
	@Override
	public void setCamouflage(ItemMeta itemMeta) {
		this.setInventorySlotContents(1, itemMeta != null ? itemMeta.getItemStack(1).copy() : null);
	}
	
	public void setClickType(int mouseButton) {
		this.mouseButton = mouseButton;
	}
	
	public int getClickType() {
		return this.mouseButton;
	}
	
}
