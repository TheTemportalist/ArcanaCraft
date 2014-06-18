package com.countrygamer.arcanacraft.common.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IExtendedEntityProperties;

import com.countrygamer.arcanacraft.common.extended.ExtendedArcanePlayer;
import com.countrygamer.arcanacraft.common.quom.Quom;
import com.countrygamer.arcanacraft.common.quom.Tiers;
import com.countrygamer.countrygamercore.Base.Plugin.extended.ExtendedEntity;
import com.countrygamer.countrygamercore.Base.common.network.AbstractMessage;

public class MessageCastQuom extends AbstractMessage {
	
	public MessageCastQuom() {
	}
	
	@Override
	public void writeTo(ByteBuf buffer) {
	}
	
	@Override
	public void readFrom(ByteBuf buffer) {
	}
	
	@Override
	public void handleOnClient(EntityPlayer player) {
		this.handle(player);
	}
	
	@Override
	public void handleOnServer(EntityPlayer player) {
		this.handle(player);
	}
	
	public void handle(EntityPlayer player) {
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
					// ArcanaCraft.logger.info(castTier.name());
					quom.onUse_do(player, arcanePlayer, player.worldObj, castTier, manusTier);
				}
			}
			else {
				// System.out.println("No Quom There");
			}
		}
	}
	
}
