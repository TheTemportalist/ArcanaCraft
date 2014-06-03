package com.countrygamer.arcanacraft.common.tile;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;

import com.countrygamer.countrygamercore.Base.common.tileentity.TileEntityBase;

public class TileEntityFalseAir extends TileEntityBase {
	
	private Block containedBlock;
	private int containedMetadata;
	
	private int ticksTillDeath;
	
	public TileEntityFalseAir() {
		super();
		
		this.containedBlock = null;
		this.containedMetadata = 0;
		
		this.ticksTillDeath = 0;
		
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tagCom) {
		super.writeToNBT(tagCom);
		
		tagCom.setInteger("blockID", Block.getIdFromBlock(this.containedBlock));
		tagCom.setInteger("blockMeta", this.containedMetadata);
		
		tagCom.setInteger("ticks", this.ticksTillDeath);
		
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCom) {
		super.readFromNBT(tagCom);
		
		this.containedBlock = Block.getBlockById(tagCom.getInteger("blockID"));
		this.containedMetadata = tagCom.getInteger("blockMeta");
		
		this.ticksTillDeath = tagCom.getInteger("ticks");
		
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		this.ticksTillDeath--;
		
		//System.out.println(this.ticksTillDeath);
		
		if (this.ticksTillDeath <= 0) {
			this.eraseFalseAir();
		}
		
	}
	
	public void eraseFalseAir() {
		//System.out.println("decay");
		if (this.containedBlock != null) {
			this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, this.containedBlock,
					this.containedMetadata, 3);
		}
		else {
			this.worldObj.setBlockToAir(this.xCoord, this.yCoord, this.zCoord);
		}
	}
	
	public void setTicksTillDeath(int ticks) {
		//System.out.println("New ticks " + ticks);
		this.ticksTillDeath = ticks;
	}
	
	public void setBlock(Block block, int metadata) {
		this.containedBlock = block;
		this.containedMetadata = metadata;
	}
	
}
