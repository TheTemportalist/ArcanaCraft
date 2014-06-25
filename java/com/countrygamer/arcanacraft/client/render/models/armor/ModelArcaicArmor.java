package com.countrygamer.arcanacraft.client.render.models.armor;

import com.countrygamer.countrygamercore.base.client.render.models.ModelBipedBase;
import com.countrygamer.countrygamercore.base.client.render.models.ModelHelper;

public class ModelArcaicArmor extends ModelBipedBase {
	
	public ModelArcaicArmor(Float scale) {
		super(scale);
		
		this.addHead();
		
		this.addBody();
		
		this.addLegs();
		
		this.addBoots();
		
	}
	
	// @formatter:off
	
	private void addHead() {
		ModelHelper.createArmorModel(this, "head",
				-4.0F, -8.0F, -5.0F,
				8, 4, 1,
				0, 0);
		
		ModelHelper.createArmorModel(this, "head",
				-5.0F, -9.0F, -5.0F,
				10, 1, 10,
				0, 0);
		
		ModelHelper.createArmorModel(this, "head",
				-5.0F, -8.0F, -5.0F,
				1, 8, 10,
				0, 0);
		
		ModelHelper.createArmorModel(this, "head",
				4.0F, -8.0F, -5.0F,
				1, 8, 10,
				0, 0);
		
		ModelHelper.createArmorModel(this, "head",
				-4.0F, -8.0F, 4.0F,
				9, 8, 1,
				0, 0);
	}
	
	private void addBody() {
		ModelHelper.createArmorModel(this, "chest",
				-5.0F, 0.0F, 2.0F,
				10, 12, 1,
				0, 0);
		ModelHelper.createArmorModel(this, "chest",
				-5.0F, 0.0F, -3.0F,
				10, 12, 1,
				0, 0);
		
	}
	
	private void addLegs() {
		
	}
	
	private void addBoots() {
		
	}
	
	// @formatter:on
	
}
