package com.countrygamer.arcanacraft.common.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

import com.countrygamer.arcanacraft.common.tile.TileEntityActuator;
import com.countrygamer.countrygamercore.Base.common.network.MessageTileEntityBase;

public class MessageUpdateClick extends MessageTileEntityBase {
	
	int mouseButton;
	
	public MessageUpdateClick() {
	}
	
	public MessageUpdateClick(int x, int y, int z, int mouseButton) {
		super(x, y, z);
		this.mouseButton = mouseButton;
		
	}
	
	@Override
	public void writeTo(ByteBuf buffer) {
		super.writeTo(buffer);
		buffer.writeInt(this.mouseButton);
		
	}
	
	@Override
	public void readFrom(ByteBuf buffer) {
		super.readFrom(buffer);
		this.mouseButton = buffer.readInt();
		
	}
	
	@Override
	protected void handleSync(EntityPlayer player, TileEntity tileEntity) {
		if (tileEntity != null && tileEntity instanceof TileEntityActuator) {
			((TileEntityActuator)tileEntity).setClickType(this.mouseButton);
		}
	}
	
}
