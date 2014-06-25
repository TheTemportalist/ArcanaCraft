package com.countrygamer.arcanacraft.common.item;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

import com.countrygamer.countrygamercore.common.item.ItemBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCharmBase extends ItemBase {
	
	public ItemCharmBase(String modid, String name) {
		super(modid, name);
	}
	
	public Charm[] getCharm(ItemStack itemStack) {
		NBTTagCompound tagCom = itemStack.hasTagCompound() ? itemStack.getTagCompound()
				: new NBTTagCompound();
		NBTTagList charms = tagCom.getTagList("charms", 10);
		
		Charm[] ret = new Charm[charms.tagCount()];
		for (int i = 0; i < charms.tagCount(); i++) {
			ret[i] = (Charm) CharmRegister.INSTANCE.getObject(charms.getCompoundTagAt(i).getString(
					"charmName"));
		}
		
		return null;
	}
	
	@Override
	public void onUpdate(ItemStack itemStack, World world, Entity entity, int par4,
			boolean isCurrentItem) {
		if (itemStack != null && !itemStack.hasTagCompound()) {
			itemStack.setTagCompound(new NBTTagCompound());
		}
	}
	
	@SuppressWarnings({
			"rawtypes", "unchecked"
	})
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4) {
		if (itemStack.hasTagCompound()) {
			NBTTagCompound tagCom = itemStack.getTagCompound();
			NBTTagList charmsList = tagCom.getTagList("charms", 10);
			for (int i = 0; i < charmsList.tagCount(); i++) {
				NBTTagCompound charmTag = charmsList.getCompoundTagAt(i);
				list.add(charmTag.getString("charmName"));
			}
		}
	}
	
	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y,
			int z, int side, float x1, float y1, float z1) {
		boolean didSomething = false;
		if (itemStack.hasTagCompound()) {
			NBTTagCompound tagCom = itemStack.getTagCompound();
			NBTTagList charmsList = tagCom.getTagList("charms", 10);
			for (int i = 0; i < charmsList.tagCount(); i++) {
				NBTTagCompound charmTag = charmsList.getCompoundTagAt(i);
				didSomething = ((Charm) CharmRegister.INSTANCE.getObject(charmTag
						.getString("charmName"))).onUse(itemStack, player, world, x, y, z, side,
						x1, y1, z1);
			}
		}
		return didSomething;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		if (itemStack.hasTagCompound()) {
			NBTTagCompound tagCom = itemStack.getTagCompound();
			NBTTagList charmsList = tagCom.getTagList("charms", 10);
			for (int i = 0; i < charmsList.tagCount(); i++) {
				NBTTagCompound charmTag = charmsList.getCompoundTagAt(i);
				((Charm) CharmRegister.INSTANCE.getObject(charmTag.getString("charmName")))
						.onClick(itemStack, player, world);
			}
		}
		return itemStack;
	}
	
	@Override
	public boolean itemInteractionForEntity(ItemStack itemStack, EntityPlayer player,
			EntityLivingBase entity) {
		boolean didSomething = false;
		if (itemStack.hasTagCompound()) {
			NBTTagCompound tagCom = itemStack.getTagCompound();
			NBTTagList charmsList = tagCom.getTagList("charms", 10);
			for (int i = 0; i < charmsList.tagCount(); i++) {
				NBTTagCompound charmTag = charmsList.getCompoundTagAt(i);
				didSomething = ((Charm) CharmRegister.INSTANCE.getObject(charmTag
						.getString("charmName"))).entityInteraction(itemStack, player, entity);
			}
		}
		return didSomething;
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack itemStack, EntityPlayer player, Entity entity) {
		boolean didSomething = false;
		if (itemStack.hasTagCompound()) {
			NBTTagCompound tagCom = itemStack.getTagCompound();
			NBTTagList charmsList = tagCom.getTagList("charms", 10);
			for (int i = 0; i < charmsList.tagCount(); i++) {
				NBTTagCompound charmTag = charmsList.getCompoundTagAt(i);
				didSomething = ((Charm) CharmRegister.INSTANCE.getObject(charmTag
						.getString("charmName"))).entityAttack(itemStack, player, entity);
			}
		}
		return didSomething;
	}
	
}
