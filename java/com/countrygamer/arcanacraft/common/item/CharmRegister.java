package com.countrygamer.arcanacraft.common.item;

import com.countrygamer.countrygamercore.base.common.generic.GenericRegister;


public class CharmRegister extends GenericRegister {
	
	public static GenericRegister INSTANCE = new CharmRegister();
	
	public static Charm health;
	public static Charm hunger;
	public static Charm speed;
	public static Charm jumping;
	public static Charm regeneration;
	public static Charm heat;
	public static Charm gills;
	public static Charm invisiblity;
	public static Charm nightvision;
	
	@Override
	public void registerObjects() {
		
		CharmRegister.health = new Charm("Health");
		
		CharmRegister.hunger = new Charm("Hunger");
		
		CharmRegister.speed = new Charm("Speed");
		
		CharmRegister.jumping = new Charm("Jumping");
		
		CharmRegister.regeneration = new Charm("Regeneration");
		
		CharmRegister.heat = new Charm("Heat");
		
		CharmRegister.gills = new Charm("Gills");
		
		CharmRegister.invisiblity = new Charm("Invisibility");
		
		CharmRegister.nightvision = new Charm("Night Vision");
		
	}
}
