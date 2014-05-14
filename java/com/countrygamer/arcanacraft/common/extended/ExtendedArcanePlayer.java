package com.countrygamer.arcanacraft.common.extended;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.countrygamer.core.Base.Plugin.ExtendedEntity;

public class ExtendedArcanePlayer extends ExtendedEntity {

	private int stamina;
	
	public ExtendedArcanePlayer(EntityPlayer player) {
		super(player);
		this.stamina = 50;
	}

	@Override
	public void init(Entity entity, World world) {
		//this.stamina = 50;
		//this.onPropertyChanged(this);
	}
	
	@Override
	public void saveNBTData(NBTTagCompound compound) {
		compound.setInteger("stamina", this.stamina);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		this.stamina = compound.getInteger("stamina");
	}

	public void setStamina(int value) {
		this.stamina = value;
		this.onPropertyChanged(this);
	}
	
	public int getStamina() {
		return this.stamina;
	}
	
	
}
