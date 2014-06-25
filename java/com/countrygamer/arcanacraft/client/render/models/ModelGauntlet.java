package com.countrygamer.arcanacraft.client.render.models;

import com.countrygamer.countrygamercore.base.client.render.models.ModelBase;
import com.countrygamer.countrygamercore.base.client.render.models.ModelHelper;

public class ModelGauntlet extends ModelBase {
	
	public ModelGauntlet() {
		super(64, 64);
		
		// @formatter:off
		
		
		ModelHelper.createModel(this, this,
				0.0F, 0.0F, 0.0F,
				-5.0F, 0.0F, -5.0F,
				10, 20, 10,
				0.0F, 0.0F, 0.0F,
				0, 0);
		
		
		ModelHelper.createModel(this, this,
				-5.0F, -15.0F, 0.0F,
				-1.0F, -2.5F, -2.5F,
				2, 5, 5,
				0.0F, 0.0F, 0.0F,
				0, 0);
		
		// @formatter:on
		
	}
	
}
