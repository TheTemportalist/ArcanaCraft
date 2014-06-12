package com.countrygamer.arcanacraft.common.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCharm extends ItemCharmBase {
	
	public ItemCharm(String modid, String name) {
		super(modid, name);
	}
	
	@SuppressWarnings({
			"rawtypes", "unchecked"
	})
	@SideOnly(Side.CLIENT)
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List subItems) {
		for (final Object key : CharmRegister.INSTANCE.getRegister().getKeys()) {
			Charm charm = (Charm) CharmRegister.INSTANCE.getObject((String)key);
			
			ItemStack itemStack = new ItemStack(item, 1, charm.getID());
			NBTTagCompound tagCom = new NBTTagCompound();
			NBTTagList charms = new NBTTagList();
			NBTTagCompound charmTag = new NBTTagCompound();
			charmTag.setString("charmName", charm.getName());
			charms.appendTag(charmTag);
			tagCom.setTag("charms", charms);
			itemStack.setTagCompound(tagCom);
			
			subItems.add(itemStack);
			
		}
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIconFromDamage(int meta) {
		return ((Charm)CharmRegister.INSTANCE.getObject(meta)).getIcon();
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister iconReg) {
		for (final Object key : CharmRegister.INSTANCE.getRegister().getKeys()) {
			((Charm)CharmRegister.INSTANCE.getObject((String)key)).registerIcon(iconReg);
		}
	}
	
}
