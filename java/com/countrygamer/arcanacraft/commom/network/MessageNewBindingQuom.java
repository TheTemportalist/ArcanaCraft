package com.countrygamer.arcanacraft.commom.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

import com.countrygamer.arcanacraft.common.quom.Quom;
import com.countrygamer.arcanacraft.common.quom.QuomRegistry;
import com.countrygamer.arcanacraft.common.tile.EnumBinderType;
import com.countrygamer.arcanacraft.common.tile.TileEntityBinder;
import com.countrygamer.countrygamercore.Base.common.network.AbstractMessage;

public class MessageNewBindingQuom extends AbstractMessage {
	
	private int x, y, z;
	private Quom quom;
	
	public MessageNewBindingQuom() {
	}
	
	public MessageNewBindingQuom(int x, int y, int z, Quom quom) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.quom = quom;
	}
	
	@Override
	public void writeTo(ByteBuf buffer) {
		buffer.writeInt(this.x);
		buffer.writeInt(this.y);
		buffer.writeInt(this.z);
		
		buffer.writeBoolean(this.quom != null);
		if (this.quom != null) {
			buffer.writeInt(this.quom.getID());
		}
	}
	
	@Override
	public void readFrom(ByteBuf buffer) {
		this.x = buffer.readInt();
		this.y = buffer.readInt();
		this.z = buffer.readInt();
		
		if (buffer.readBoolean()) {
			this.quom = QuomRegistry.quomRegistry.get(buffer.readInt());
		}
		else {
			this.quom = null;
		}
	}
	
	@Override
	public void handleOnClient(EntityPlayer player) {
		this.notifyBinder(player);
	}
	
	@Override
	public void handleOnServer(EntityPlayer player) {
		this.notifyBinder(player);
	}
	
	private void notifyBinder(EntityPlayer player) {
		TileEntityBinder binderTE = (TileEntityBinder) player.worldObj.getTileEntity(this.x,
				this.y, this.z);
		if (binderTE.binderType == EnumBinderType.QUOM) {
			binderTE.setBindingQuom(this.quom);
		}
	}
	
}
