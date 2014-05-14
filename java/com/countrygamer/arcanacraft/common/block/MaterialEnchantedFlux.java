package com.countrygamer.arcanacraft.common.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class MaterialEnchantedFlux extends Material {
	
	private int mobilityFlag;
	
	public MaterialEnchantedFlux(MapColor par1MapColor) {
		super(par1MapColor);
		this.setNoPushMobility();
		this.setRequiresTool();
		this.setReplaceable();
	}
	
	
	@Override
	public boolean isLiquid() {
		return true;
	}
	
	@Override
	public boolean isSolid() {
		return false;
	}
	
	@Override
	public boolean isReplaceable() {
		return true;
	}
	
	@Override
	public boolean getCanBlockGrass() {
		return false;
	}
	
	@Override
	public boolean blocksMovement() {
		return false;
	}
	
	@Override
	public Material setRequiresTool() {
		return this;
	}
	
	@Override
	public Material setNoPushMobility() {
		this.mobilityFlag = 1;
		return this;
	}
	
	@Override
	public int getMaterialMobility() {
		return this.mobilityFlag;
	}
	
}
