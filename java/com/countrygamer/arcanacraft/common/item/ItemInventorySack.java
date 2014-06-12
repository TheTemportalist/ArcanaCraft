package com.countrygamer.arcanacraft.common.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;

import com.countrygamer.arcanacraft.common.ACOptions;
import com.countrygamer.arcanacraft.common.ArcanaCraft;
import com.countrygamer.arcanacraft.common.inventory.InventorySack;
import com.countrygamer.countrygamercore.Base.common.inventory.ContainerBase;
import com.countrygamer.countrygamercore.Base.common.item.ItemBase;
import com.countrygamer.countrygamercore.Base.common.item.ItemInvBase;
import com.countrygamer.countrygamercore.lib.CoreUtil;

public class ItemInventorySack extends ItemBase {
	
	public static String sackName = "sackName";
	
	public ItemInventorySack(String modid, String name) {
		super(modid, name);
		this.setMaxStackSize(1);
		
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack itemstack) {
		return 1;
	}
	
	@Override
	public void onUpdate(ItemStack itemstack, World world, Entity entity, int par4,
			boolean isCurrentItem) {
		if (!world.isRemote && entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			if (player.openContainer != null && player.openContainer instanceof ContainerBase
					&& ((ContainerBase) player.openContainer).needsUpdate) {
				((ContainerBase) player.openContainer).writeToNBT();
				((ContainerBase) player.openContainer).needsUpdate = false;
			}
		}
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		// Open inventory without sneaking
		// if Sneaking
		/*
			Try to swap with player inv
			Try to swap with connected chest
			
		 */
		
		if (!player.isSneaking()) {
			player.openGui(ArcanaCraft.instance, ACOptions.sackGui, world, (int) player.posX,
					(int) player.posY, (int) player.posZ);
		}
		else if (itemStack.getItem() instanceof ItemInventorySack) {
			InventorySack invSack = new InventorySack(itemStack);
			
			NBTTagCompound tagCom = itemStack.hasTagCompound() ? itemStack.getTagCompound()
					: new NBTTagCompound();
			NBTTagCompound genericData = tagCom.getCompoundTag(ItemInvBase.basicDataKey);
			NBTTagCompound chestTag = genericData.getCompoundTag("chest");
			
			if (chestTag.getBoolean("hasLink")) {
				// Swap with chest
				
				int x = chestTag.getInteger("x");
				int y = chestTag.getInteger("y");
				int z = chestTag.getInteger("z");
				
				TileEntity tileEntity = world.getTileEntity(x, y, z);
				if (tileEntity != null && tileEntity instanceof TileEntityChest) {
					
					this.swapInventory(itemStack, invSack, (TileEntityChest) tileEntity, 0);
					
				}
				else {
					this.removeLink(player, itemStack);
				}
			}
			else {
				// Swap with player
				InventoryPlayer invPlayer = player.inventory;
				
				this.swapInventory(itemStack, invSack, invPlayer, 4);
				
			}
		}
		
		return itemStack;
	}
	
	private void swapInventory(ItemStack itemStack, InventorySack inv, IInventory thatInventory,
			int thatExtraSlots) {
		int idOffset = inv.getSizeInventory() - (thatInventory.getSizeInventory() - thatExtraSlots);
		for (int i = 0; i < thatInventory.getSizeInventory() - thatExtraSlots; i++) {
			// System.out.println();
			ItemStack thatStack = thatInventory.getStackInSlot(i);
			boolean valid = thatStack == null ? true : inv.isItemValidForSlot(i + idOffset,
					thatStack);
			if (valid) {
				thatInventory.setInventorySlotContents(i, inv.getStackInSlot(i + idOffset));
				inv.setInventorySlotContents(i + idOffset, thatStack);
				// System.out.println("Writing for loop slot " + i);
				inv.writeToNBT(itemStack.getTagCompound().getCompoundTag(
						ItemInvBase.inventoryDataKey));
			}
		}
	}
	
	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y,
			int z, int side, float x1, float y1, float z1) {
		if (player.isSneaking()) {
			TileEntity tileEntity = world.getTileEntity(x, y, z);
			if (tileEntity != null && tileEntity instanceof TileEntityChest) {
				NBTTagCompound tagCom = itemStack.hasTagCompound() ? itemStack.getTagCompound()
						: new NBTTagCompound();
				NBTTagCompound genericData = tagCom.getCompoundTag(ItemInvBase.basicDataKey);
				
				NBTTagCompound chestTag = new NBTTagCompound();
				
				if (!chestTag.getBoolean("hasLink")) {
					chestTag.setBoolean("hasLink", true);
					chestTag.setInteger("x", x);
					chestTag.setInteger("y", y);
					chestTag.setInteger("z", z);
					
					genericData.setTag("chest", chestTag);
					tagCom.setTag(ItemInvBase.basicDataKey, genericData);
					itemStack.setTagCompound(tagCom);
					
					CoreUtil.sendMessageToPlayer(player, "Linked to Chest at " + x + " " + y + " "
							+ z);
				}
				else {
					this.removeLink(player, itemStack);
				}
				return true;
			}
		}
		return false;
	}
	
	private void removeLink(EntityPlayer player, ItemStack itemStack) {
		itemStack.getTagCompound().getCompoundTag(ItemInvBase.basicDataKey).removeTag("chest");
		CoreUtil.sendMessageToPlayer(player, "Removed link to chest");
	}
	
	
}
