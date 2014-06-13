package com.countrygamer.arcanacraft.common.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import com.countrygamer.arcanacraft.common.ACOptions;
import com.countrygamer.arcanacraft.common.ArcanaCraft;
import com.countrygamer.arcanacraft.common.inventory.InventorySack;
import com.countrygamer.countrygamercore.Base.common.inventory.ContainerBase;
import com.countrygamer.countrygamercore.Base.common.item.ItemInvBase;
import com.countrygamer.countrygamercore.lib.CoreUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemInventorySack extends ItemInvBase {
	
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
		super.onUpdate(itemstack, world, entity, par4, isCurrentItem);
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
					
					this.swapInventory(itemStack, invSack, (TileEntityChest) tileEntity, 9, 0);
					this.spawnSwapParticles(player.worldObj, x + 0.5, y - 0.75, z + 0.5, 1, 1);
					
				}
				else {
					this.removeLink(player, itemStack);
				}
			}
			else {
				// Swap with player
				InventoryPlayer invPlayer = player.inventory;
				
				this.swapInventory(itemStack, invSack, invPlayer, 0, 4);
				if (player.worldObj.isRemote)
					this.spawnSwapParticles(player.worldObj, player.posX, player.posY - 2,
							player.posZ, player.width, player.height);
				
			}
		}
		
		return itemStack;
	}
	
	private void swapInventory(ItemStack itemStack, InventorySack invSack,
			IInventory thatInventory, int primaryOffset, int thatExtraSlots) {
		for (int i = 0; i < thatInventory.getSizeInventory() - thatExtraSlots; i++) {
			if ((i + primaryOffset) < invSack.getSizeInventory()) {
				ItemStack thatStack = thatInventory.getStackInSlot(i);
				if (thatStack == null || invSack.isItemValidForSlot((i + primaryOffset), thatStack)) {
					
					thatInventory.setInventorySlotContents(i,
							invSack.getStackInSlot((i + primaryOffset)));
					invSack.setInventorySlotContents((i + primaryOffset), thatStack);
				}
			}
		}
		invSack.writeToNBT(itemStack.getTagCompound().getCompoundTag(ItemInvBase.inventoryDataKey));
		
	}
	
	private void spawnSwapParticles(World world, double x, double y, double z, double w, double h) {
		Random random = new Random();
		
		for (int i = 0; i < 128; i++) {
			float velX = (random.nextFloat() - 0.5F) * 0.2F;
			float velY = (random.nextFloat() - 0.5F) * 0.2F;
			float velZ = (random.nextFloat() - 0.5F) * 0.2F;
			double x1 = x + (random.nextDouble() - 0.5D) * w * 2.0D;
			double y1 = y + random.nextDouble() * h;
			double z1 = z + (random.nextDouble() - 0.5D) * w * 2.0D;
			
			world.spawnParticle("portal", x1, y1, z1, velX, velY, velZ);
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
				
				NBTTagCompound chestTag = genericData.getCompoundTag("chest");
				
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
					if (x == chestTag.getInteger("x") && y == chestTag.getInteger("y")
							&& z == chestTag.getInteger("z")) {
						this.removeLink(player, itemStack);
					}
					else {
						CoreUtil.sendMessageToPlayer(player,
								"Cannot link until the old link is removed.");
					}
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
	
	@SuppressWarnings({
			"rawtypes", "unchecked"
	})
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4) {
		super.addInformation(itemStack, player, list, par4);
		if (itemStack.hasTagCompound())
			list.add(1, itemStack.getTagCompound().getCompoundTag(ItemInvBase.basicDataKey)
					.getString(ItemInventorySack.sackName));
		
	}
	
	@SuppressWarnings({
			"rawtypes", "unchecked"
	})
	@SideOnly(Side.CLIENT)
	@Override
	public List addInformationWithShift(ItemStack itemStack, EntityPlayer player, List list,
			boolean par4) {
		if (itemStack.hasTagCompound()) {
			NBTTagList stacks = itemStack.getTagCompound()
					.getCompoundTag(ItemInvBase.inventoryDataKey).getTagList("Stacks", 10);
			List<ItemStack> inventory = new ArrayList<ItemStack>();
			for (int index = 0; index < stacks.tagCount(); index++) {
				NBTTagCompound stackTag = stacks.getCompoundTagAt(index);
				int slot = stackTag.getInteger("Slot");
				if (slot >= inventory.size()) {
					inventory.add(ItemStack.loadItemStackFromNBT(stackTag));
				}
				else {
					inventory.add(slot, ItemStack.loadItemStackFromNBT(stackTag));
				}
			}
			for (final ItemStack stack : inventory) {
				list.add(EnumChatFormatting.DARK_GREEN + "   " + stack.getDisplayName());
			}
		}
		return list;
	}
}
