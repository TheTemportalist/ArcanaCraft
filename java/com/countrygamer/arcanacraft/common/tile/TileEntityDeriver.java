package com.countrygamer.arcanacraft.common.tile;

import com.countrygamer.arcanacraft.common.block.BlockDeriver;

public class TileEntityDeriver extends TileEntityManusPowered {
	
	public TileEntityDeriver() {
		super("Deriver", 1, 64);
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		BlockDeriver.checkBlockStructure(this.getWorldObj(), this.xCoord, this.yCoord, this.zCoord);
		
	}
	
}
