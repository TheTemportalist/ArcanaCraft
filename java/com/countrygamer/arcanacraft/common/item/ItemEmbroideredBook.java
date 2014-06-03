package com.countrygamer.arcanacraft.common.item;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

import com.countrygamer.arcanacraft.common.ACOptions;
import com.countrygamer.countrygamercore.Base.common.item.ItemBase;

public class ItemEmbroideredBook extends ItemBase {
	
	public ItemEmbroideredBook(String pluginID, String name) {
		super(pluginID, name);
	}
	
	public int getItemEnchantability() {
		return 50;
	}
	
	public boolean isItemTool(ItemStack itemStack) {
		return true;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		if (itemStack != null && itemStack.getItem() instanceof ItemEmbroideredBook) {
			if (ItemEmbroideredBook.hasEnchantment(itemStack, ACOptions.arcaneSocerery, 1)) {
				return new ItemStack(ACItems.arcana);
			}
			else {
				float maxHealth = player.getMaxHealth();
				float currentHealth = player.getHealth();
				float requiredHealth = maxHealth - 1;
				float newHealth = currentHealth - requiredHealth;
				if (newHealth >= 0) {
					player.setHealth(newHealth);
					return new ItemStack(ACItems.arcana);
				}
				else {
					player.setHealth(0);
					return itemStack;
				}
			}
		}
		return itemStack;
	}
	
	@Override
	public boolean getIsRepairable(ItemStack stack, ItemStack stack2) {
		if (stack != null && stack2 != null) {
			if (stack.getItem() instanceof ItemEmbroideredBook
					&& stack2.getItem() instanceof ItemEmbroideredBook) {
				return ItemEmbroideredBook.hasEnchantment(stack, ACOptions.arcaneSocerery, 0)
						&& ItemEmbroideredBook.hasEnchantment(stack2, ACOptions.arcaneSocerery, 0);
			}
		}
		return true;
	}
	
	public static boolean hasEnchantment(ItemStack itemStack, Enchantment enchant, int level) {
		if (itemStack != null && itemStack.isItemEnchanted()) {
			for (int i = 0; i < itemStack.getEnchantmentTagList().tagCount(); i++) {
				NBTTagCompound enchants = ((NBTTagList) itemStack.getEnchantmentTagList())
						.getCompoundTagAt(i);
				if (enchants.getShort("id") == enchant.effectId) {
					if (level <= 0 || enchants.getShort("lvl") == level) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
}
