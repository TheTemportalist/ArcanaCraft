package com.countrygamer.arcanacraft.common.item;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class ItemCharmMulti extends ItemCharmBase {
	
	public ItemCharmMulti(String modid, String name) {
		super(modid, name);
	}
	
	public static void addCharm(ItemStack multiCharmStack, Charm charm) {
		NBTTagCompound tagCom = multiCharmStack.hasTagCompound() ? multiCharmStack.getTagCompound()
				: new NBTTagCompound();
		NBTTagList charmList = tagCom.getTagList("", 10);
		NBTTagCompound charmTag = new NBTTagCompound();
		charmTag.setString("charmName", charm.getName());
		charmList.appendTag(charmTag);
		tagCom.setTag("charms", charmList);
		multiCharmStack.setTagCompound(tagCom);
	}
	
}
