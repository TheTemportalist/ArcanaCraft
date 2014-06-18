package com.countrygamer.arcanacraft.common.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

import com.countrygamer.arcanacraft.common.extended.ExtendedArcanePlayer;
import com.countrygamer.countrygamercore.Base.Plugin.extended.ExtendedEntity;
import com.countrygamer.countrygamercore.Base.common.network.AbstractMessage;

public class MessageSelectQuom extends AbstractMessage {
	
	boolean nextVsLast;
	
	public MessageSelectQuom() {
	}
	
	public MessageSelectQuom(boolean next) {
		this.nextVsLast = next;
	}
	
	@Override
	public void writeTo(ByteBuf buffer) {
		buffer.writeBoolean(this.nextVsLast);
		
	}
	
	@Override
	public void readFrom(ByteBuf buffer) {
		this.nextVsLast = buffer.readBoolean();
		
	}
	
	@Override
	public void handleOnClient(EntityPlayer player) {
	}
	
	@Override
	public void handleOnServer(EntityPlayer player) {
		ExtendedArcanePlayer arcanePlayer = (ExtendedArcanePlayer) ExtendedEntity.getExtended(
				player, ExtendedArcanePlayer.class);
		if (this.nextVsLast)
			arcanePlayer.nextQuom();
		else
			arcanePlayer.lastQuom();
	}
	
}
