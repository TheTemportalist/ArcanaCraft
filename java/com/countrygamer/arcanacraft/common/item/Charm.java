package com.countrygamer.arcanacraft.common.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.countrygamer.arcanacraft.common.ArcanaCraft;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Charm {
	
	private final int id;
	private final String name;
	@SideOnly(Side.CLIENT)
	private IIcon icon;
	
	public Charm(String name) {
		this.name = name;
		this.id = CharmRegister.INSTANCE.getRegister().getKeys().size();
		
		CharmRegister.INSTANCE.registerObject(this.id, this.name, this);
		
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getID() {
		return this.id;
	}
	
	@SideOnly(Side.CLIENT)
	public void registerIcon(IIconRegister iconReg) {
		this.icon = iconReg.registerIcon(ArcanaCraft.pluginID + ":charms/" + this.getName());
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon() {
		return this.icon;
	}
	
	public boolean onUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y,
			int z, int side, float x1, float y1, float z1) {
		return false;
	}
	
	public void onClick(ItemStack itemStack, EntityPlayer player, World world) {
		
	}
	
	public boolean entityInteraction(ItemStack itemStack, EntityPlayer player,
			EntityLivingBase entity) {
		return false;
	}
	
	public boolean entityAttack(ItemStack itemStack, EntityPlayer player,
			Entity entity) {
		return false;
	}
	
	
}
