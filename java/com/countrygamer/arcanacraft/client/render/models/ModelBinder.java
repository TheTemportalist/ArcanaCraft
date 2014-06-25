package com.countrygamer.arcanacraft.client.render.models;

import com.countrygamer.countrygamercore.base.client.render.models.ModelBase;
import com.countrygamer.countrygamercore.base.client.render.models.ModelHelper;

public class ModelBinder extends ModelBase {
	
	public ModelBinder() {
		super(64, 64);
		
		// @formatter:off
		
		// BASE (2 rectangles)
		ModelHelper.createModel(this, this,
				+0.0F, +16.0F, +0.0F,
				-8.0F, +0.0F, -8.0F,
				16, 1, 16,
				+0.0F, +0.0F, +0.0F,
				0, 0);
		ModelHelper.createModel(this, this,
				+0.0F, +15.0F, +0.0F,
				-7.0F, +0.0F, -7.0F,
				14, 1, 14,
				+0.0F, +0.0F, +0.0F,
				0, 17);
		
		float prongY = +15.0F;
		int pylonHeight = 5;
		// ++ Prongs
		ModelHelper.createModel(this, this,
				+6.0F, prongY, +6.0F,
				-1.5F, +0.0F, -1.5F,
				3, 6, 3,
				+0.3F, +0.8F, +0.0F,
				0, 32);
		ModelHelper.createModel(this, this,
				+7.0F, prongY - 5, +7.0F,
				-1.5F, +0.0F, -1.5F,
				3, 6, 3,
				-0.3F, +0.8F, +0.0F,
				12, 32);
		ModelHelper.createModel(this, this,
				+6.0F, prongY - 10, +6.0F,
				-1.0F, +0.0F, -1.0F,
				2, 5, 2,
				-0.7F, +0.8F, +0.0F,
				24, 32);
		
		ModelHelper.createModel(this, this,
				+2.0F, prongY - 1, +2.0F,
				-0.5F, +0.0F, -0.5F,
				1, pylonHeight, 1,
				+0.0F, +0.0F, +0.0F,
				32, 32);
		
		// -+ Prongs
		ModelHelper.createModel(this, this,
				-6.0F, prongY, +6.0F,
				-1.5F, +0.0F, -1.5F,
				3, 6, 3,
				+0.3F, -0.8F, +0.0F,
				0, 32);
		ModelHelper.createModel(this, this,
				-7.0F, prongY - 5, +7.0F,
				-1.5F, +0.0F, -1.5F,
				3, 6, 3,
				-0.3F, -0.8F, +0.0F,
				12, 32);
		ModelHelper.createModel(this, this,
				-6.0F, prongY - 10, +6.0F,
				-1.0F, +0.0F, -1.0F,
				2, 5, 2,
				-0.7F, -0.8F, +0.0F,
				24, 32);
		
		ModelHelper.createModel(this, this,
				-2.0F, prongY - 1, +2.0F,
				-0.5F, +0.0F, -0.5F,
				1, pylonHeight, 1,
				+0.0F, +0.0F, +0.0F,
				32, 32);
		
		// -- Prongs
		ModelHelper.createModel(this, this,
				-6.0F, prongY, -6.0F,
				-1.5F, +0.0F, -1.5F,
				3, 6, 3,
				-0.3F, +0.8F, +0.0F,
				0, 32);
		ModelHelper.createModel(this, this,
				-7.0F, prongY - 5, -7.0F,
				-1.5F, +0.0F, -1.5F,
				3, 6, 3,
				+0.3F, +0.8F, +0.0F,
				12, 32);
		ModelHelper.createModel(this, this,
				-6.0F, prongY - 10, -6.0F,
				-1.0F, +0.0F, -1.0F,
				2, 5, 2,
				+0.7F, +0.8F, +0.0F,
				24, 32);
		
		ModelHelper.createModel(this, this,
				-2.0F, prongY - 1, -2.0F,
				-0.5F, +0.0F, -0.5F,
				1, pylonHeight, 1,
				+0.0F, +0.0F, +0.0F,
				32, 32);
		
		// +- Prongs
		ModelHelper.createModel(this, this,
				+6.0F, prongY, -6.0F,
				-1.5F, +0.0F, -1.5F,
				3, 6, 3,
				-0.3F, -0.8F, +0.0F,
				0, 32);
		ModelHelper.createModel(this, this,
				+7.0F, prongY - 5, -7.0F,
				-1.5F, +0.0F, -1.5F,
				3, 6, 3,
				+0.3F, -0.8F, +0.0F,
				12, 32);
		ModelHelper.createModel(this, this,
				+6.0F, prongY - 10, -6.0F,
				-1.0F, +0.0F, -1.0F,
				2, 5, 2,
				+0.7F, -0.8F, +0.0F,
				24, 32);
		
		ModelHelper.createModel(this, this,
				+2.0F, prongY - 1, -2.0F,
				-0.5F, +0.0F, -0.5F,
				1, pylonHeight, 1,
				+0.0F, +0.0F, +0.0F,
				32, 32);
		
		// @formatter:on
		
	}
	
}
