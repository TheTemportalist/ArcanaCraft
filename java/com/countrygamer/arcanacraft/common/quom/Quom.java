package com.countrygamer.arcanacraft.common.quom;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import com.countrygamer.arcanacraft.common.ArcanaCraft;
import com.countrygamer.arcanacraft.common.extended.ExtendedArcanePlayer;

public abstract class Quom {
	
	private String name;
	private String parentName;
	private int id;
	private ResourceLocation iconSource;
	
	public Quom() {
	}
	
	public Quom(String name) {
		this(name, null);
	}
	
	public Quom(String name, String parentKey) {
		this.name = name;
		this.parentName = parentKey == null ? "" : parentKey;
		this.iconSource = new ResourceLocation(ArcanaCraft.pluginID,
				"textures/quoms/" + this.name + ".png");
		
		this.id = QuomRegistry.quomRegistry.size();
		QuomRegistry.registerQuom(this);
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getID() {
		return this.id;
	}
	
	public void draw(Minecraft mc, Gui g, int x, int y) {
		mc.getTextureManager().bindTexture(this.iconSource);
		g.drawTexturedModalRect(x, y, 0, 0, 16, 16);
	}
	
	public void saveToNBT(NBTTagCompound tagCom) {
		tagCom.setString("name", this.name);
		tagCom.setString("parentName", this.parentName);
		tagCom.setInteger("id", this.id);
		tagCom.setString("iconSource_dir", this.iconSource.getResourceDomain());
		tagCom.setString("iconSource_path", this.iconSource.getResourcePath());
		
	}
	
	public void loadFromNBT(NBTTagCompound tagCom) {
		this.name = tagCom.getString("name");
		this.parentName = tagCom.getString("parentName");
		this.id = tagCom.getInteger("id");
		this.iconSource = new ResourceLocation(tagCom.getString("iconSource_dir"),
				tagCom.getString("iconSource_path"));
		
	}
	
	public Quom copy() {
		// NBTTagCompound tagCom = new NBTTagCompound();
		// this.saveToNBT(tagCom);
		// return Quom.getFromNBT(tagCom);
		return this;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Quom) {
			Quom that = (Quom) obj;
			return this.name.equals(that.name) && this.id == that.id
					&& this.parentName.equals(that.parentName);
		}
		return false;
	}
	
	public Quom getParent() {
		return QuomRegistry.getQuom(this.parentName);
	}
	
	public double getReachLength() {
		return 5.0D;
	}
	
	public void onUse_do(ExtendedArcanePlayer arcanePlayer, World world, int x, int y,
			int z, int side, Tiers.Cast castTier, Tiers.MANUS manusTier) {
		// TODO Decrement Manus
		this.onUse(arcanePlayer, world, x, y, z, side, castTier, manusTier);
	}
	
	public abstract void onUse(ExtendedArcanePlayer arcanePlayer, World world, int x, int y,
			int z, int side, Tiers.Cast castTier, Tiers.MANUS manusTier);
	
}
