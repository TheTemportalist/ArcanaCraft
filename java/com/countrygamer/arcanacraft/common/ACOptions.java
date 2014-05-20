package com.countrygamer.arcanacraft.common;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;

import com.countrygamer.core.Base.Plugin.PluginEnchantment;
import com.countrygamer.core.Base.Plugin.PluginOptionRegistry;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ACOptions implements PluginOptionRegistry {
	
	public static final int arcanaGui = 0;
	public static final int arcanaGuiReset = 1;
	
	public static final ResourceLocation icons = new ResourceLocation(
			ArcanaCraft.pluginID, "textures/gui/GuiIcons.png");
	
	public static final ResourceLocation quomIcons = new ResourceLocation(
			ArcanaCraft.pluginID, "textures/quoms/Teleport.png");
	
	public static final Enchantment arcaneSocerery = new PluginEnchantment(
			"Arcane Socerery", 1) {
		
		@Override
		public int getMaxLevel() {
			return 1;
		}
		
		@Override
		public boolean canApply(ItemStack itemStack) {
			return itemStack.getItem() == ACItems.embroideredBook;
		}
		
		@Override
		public boolean canApplyTogether(Enchantment enchant2) {
			return true;
		}
		
	};
	
	@Override
	public boolean hasCustomConfiguration() {
		return false;
	}
	
	@Override
	public void customizeConfiguration(FMLPreInitializationEvent event) {
	}
	
	@Override
	public void registerOptions(Configuration config) {
		
	}
	
}