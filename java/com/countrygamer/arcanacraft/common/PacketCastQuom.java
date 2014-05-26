package com.countrygamer.arcanacraft.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IExtendedEntityProperties;

import com.countrygamer.arcanacraft.common.extended.ExtendedArcanePlayer;
import com.countrygamer.arcanacraft.common.quom.Quom;
import com.countrygamer.arcanacraft.common.quom.Tiers;
import com.countrygamer.core.Base.Plugin.ExtendedEntity;
import com.countrygamer.core.Base.common.packet.AbstractPacket;

public class PacketCastQuom extends AbstractPacket {
	
	public PacketCastQuom() {
	}
	
	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
	}
	
	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
	}
	
	@Override
	public void handleClientSide(EntityPlayer player) {
	}
	
	@Override
	public void handleServerSide(EntityPlayer player) {
		IExtendedEntityProperties props = ExtendedEntity.getExtended(player,
				ExtendedArcanePlayer.class);
		// System.out.println(props.getClass().getSimpleName());
		if (props != null) {
			ExtendedArcanePlayer arcanePlayer = (ExtendedArcanePlayer) props;
			Quom quom = arcanePlayer.getCurrentQuom();
			if (quom != null) {
				// System.out.println("FoundQuom");
				// System.out.println(quom.getClass().getSimpleName());
				// System.out.println(quom.getName());
				ItemStack heldStack = player.getHeldItem();
				Tiers.Cast castTier = Tiers.Cast.getTier(heldStack);
				Tiers.MANUS manusTier = Tiers.MANUS.getTier(castTier, quom);
				if (castTier != null && manusTier != null) {
					//ArcanaCraft.logger.info(castTier.name());
					quom.onUse_do(player, arcanePlayer, player.worldObj, castTier, manusTier);
				}
			}
			else {
				// System.out.println("No Quom There");
			}
		}
	}
	
}
