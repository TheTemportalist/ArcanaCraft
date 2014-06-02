package com.countrygamer.arcanacraft.client.model;

import com.countrygamer.countrygamercore.Base.client.ModelBase;

public class ModelBinder extends ModelBase {
	
	public ModelBinder() {
		super(64, 64);
		
		// @formatter:off
		
		// BASE (2 rectangles)
		this.createModel(this, this.modelList,
				+0.0F, +16.0F, +0.0F,
				-8.0F, +0.0F, -8.0F,
				16, 1, 16,
				+0.0F, +0.0F, +0.0F,
				this.textureWidth, this.textureHeight, 0, 0);
		this.createModel(this, this.modelList,
				+0.0F, +15.0F, +0.0F,
				-7.0F, +0.0F, -7.0F,
				14, 1, 14,
				+0.0F, +0.0F, +0.0F,
				this.textureWidth, this.textureHeight, 0, 17);
		
		float prongY = +15.0F;
		int pylonHeight = 5;
		// ++ Prongs
		this.createModel(this, this.modelList,
				+6.0F, prongY, +6.0F,
				-1.5F, +0.0F, -1.5F,
				3, 6, 3,
				+0.3F, +0.8F, +0.0F,
				this.textureWidth, this.textureHeight, 0, 32);
		this.createModel(this, this.modelList,
				+7.0F, prongY - 5, +7.0F,
				-1.5F, +0.0F, -1.5F,
				3, 6, 3,
				-0.3F, +0.8F, +0.0F,
				this.textureWidth, this.textureHeight, 12, 32);
		this.createModel(this, this.modelList,
				+6.0F, prongY - 10, +6.0F,
				-1.0F, +0.0F, -1.0F,
				2, 5, 2,
				-0.7F, +0.8F, +0.0F,
				this.textureWidth, this.textureHeight, 24, 32);
		
		this.createModel(this, this.modelList,
				+2.0F, prongY - 1, +2.0F,
				-0.5F, +0.0F, -0.5F,
				1, pylonHeight, 1,
				+0.0F, +0.0F, +0.0F,
				this.textureWidth, this.textureHeight, 32, 32);
		
		// -+ Prongs
		this.createModel(this, this.modelList,
				-6.0F, prongY, +6.0F,
				-1.5F, +0.0F, -1.5F,
				3, 6, 3,
				+0.3F, -0.8F, +0.0F,
				this.textureWidth, this.textureHeight, 0, 32);
		this.createModel(this, this.modelList,
				-7.0F, prongY - 5, +7.0F,
				-1.5F, +0.0F, -1.5F,
				3, 6, 3,
				-0.3F, -0.8F, +0.0F,
				this.textureWidth, this.textureHeight, 12, 32);
		this.createModel(this, this.modelList,
				-6.0F, prongY - 10, +6.0F,
				-1.0F, +0.0F, -1.0F,
				2, 5, 2,
				-0.7F, -0.8F, +0.0F,
				this.textureWidth, this.textureHeight, 24, 32);
		
		this.createModel(this, this.modelList,
				-2.0F, prongY - 1, +2.0F,
				-0.5F, +0.0F, -0.5F,
				1, pylonHeight, 1,
				+0.0F, +0.0F, +0.0F,
				this.textureWidth, this.textureHeight, 32, 32);
		
		// -- Prongs
		this.createModel(this, this.modelList,
				-6.0F, prongY, -6.0F,
				-1.5F, +0.0F, -1.5F,
				3, 6, 3,
				-0.3F, +0.8F, +0.0F,
				this.textureWidth, this.textureHeight, 0, 32);
		this.createModel(this, this.modelList,
				-7.0F, prongY - 5, -7.0F,
				-1.5F, +0.0F, -1.5F,
				3, 6, 3,
				+0.3F, +0.8F, +0.0F,
				this.textureWidth, this.textureHeight, 12, 32);
		this.createModel(this, this.modelList,
				-6.0F, prongY - 10, -6.0F,
				-1.0F, +0.0F, -1.0F,
				2, 5, 2,
				+0.7F, +0.8F, +0.0F,
				this.textureWidth, this.textureHeight, 24, 32);
		
		this.createModel(this, this.modelList,
				-2.0F, prongY - 1, -2.0F,
				-0.5F, +0.0F, -0.5F,
				1, pylonHeight, 1,
				+0.0F, +0.0F, +0.0F,
				this.textureWidth, this.textureHeight, 32, 32);
		
		// +- Prongs
		this.createModel(this, this.modelList,
				+6.0F, prongY, -6.0F,
				-1.5F, +0.0F, -1.5F,
				3, 6, 3,
				-0.3F, -0.8F, +0.0F,
				this.textureWidth, this.textureHeight, 0, 32);
		this.createModel(this, this.modelList,
				+7.0F, prongY - 5, -7.0F,
				-1.5F, +0.0F, -1.5F,
				3, 6, 3,
				+0.3F, -0.8F, +0.0F,
				this.textureWidth, this.textureHeight, 12, 32);
		this.createModel(this, this.modelList,
				+6.0F, prongY - 10, -6.0F,
				-1.0F, +0.0F, -1.0F,
				2, 5, 2,
				+0.7F, -0.8F, +0.0F,
				this.textureWidth, this.textureHeight, 24, 32);
		
		this.createModel(this, this.modelList,
				+2.0F, prongY - 1, -2.0F,
				-0.5F, +0.0F, -0.5F,
				1, pylonHeight, 1,
				+0.0F, +0.0F, +0.0F,
				this.textureWidth, this.textureHeight, 32, 32);
		
		// @formatter:on
		
	}
	
}
