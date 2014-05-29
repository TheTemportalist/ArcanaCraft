package com.countrygamer.arcanacraft.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;

import com.countrygamer.arcanacraft.common.extended.ExtendedArcanePlayer;
import com.countrygamer.core.Base.Plugin.extended.ExtendedEntity;
import com.countrygamer.core.Base.common.packet.AbstractPacket;

public class PacketSelectQuom extends AbstractPacket {
	
	boolean nextVsLast;
	
	public PacketSelectQuom() {
	}
	
	public PacketSelectQuom(boolean next) {
		this.nextVsLast = next;
	}
	
	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		buffer.writeBoolean(this.nextVsLast);
		
	}
	
	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		this.nextVsLast = buffer.readBoolean();
		
	}
	
	@Override
	public void handleClientSide(EntityPlayer player) {
		
	}
	
	@Override
	public void handleServerSide(EntityPlayer player) {
		ExtendedArcanePlayer arcanePlayer = (ExtendedArcanePlayer) ExtendedEntity
				.getExtended(player, ExtendedArcanePlayer.class);
		if (this.nextVsLast)
			arcanePlayer.nextQuom();
		else
			arcanePlayer.lastQuom();
		
	}
	
}
