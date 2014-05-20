package com.countrygamer.arcanacraft.common.quom;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import com.countrygamer.arcanacraft.common.ArcanaCraft;
import com.countrygamer.arcanacraft.common.extended.ExtendedArcanePlayer;

public class Quom {
	
	public static Quom getFromNBT(NBTTagCompound tagCom) {
		Quom quom = new Quom();
		quom.loadFromNBT(tagCom);
		return quom;
	}
	
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
		
		id = QuomRegistry.quomRegistry.size();
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
		return -1;
	}
	
	public void onUse(ExtendedArcanePlayer arcanePlayer, World world, int x, int y,
			int z, int side) {
		// TODO Decrement Manus
	}
	
}
