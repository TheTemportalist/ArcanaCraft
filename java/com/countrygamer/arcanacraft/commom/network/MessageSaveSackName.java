package com.countrygamer.arcanacraft.commom.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.countrygamer.arcanacraft.common.item.ItemInventorySack;
import com.countrygamer.countrygamercore.Base.common.item.ItemInvBase;
import com.countrygamer.countrygamercore.Base.common.network.AbstractMessage;

public class MessageSaveSackName extends AbstractMessage {
	
	String name = "";
	int length = 0;
	
	public MessageSaveSackName() {
	}
	
	public MessageSaveSackName(String name) {
		this.name = name;
		this.length = name.length();
	}
	
	@Override
	public void writeTo(ByteBuf buffer) {
		buffer.writeInt(this.length);
		char[] chars = this.name.toCharArray();
		for (int i = 0; i < this.length; i++) {
			buffer.writeChar(chars[i]);
		}
		
	}
	
	@Override
	public void readFrom(ByteBuf buffer) {
		this.length = buffer.readInt();
		String str = "";
		for (int i = 0; i < this.length; i++) {
			str += buffer.readChar();
		}
		this.name = str;
		
	}
	
	@Override
	public void handleOnClient(EntityPlayer player) {
		
	}
	
	@Override
	public void handleOnServer(EntityPlayer player) {
		ItemStack held = player.getHeldItem();
		if (!player.worldObj.isRemote) {
			if (held != null && held.getItem() instanceof ItemInventorySack) {
				NBTTagCompound tagCom = null;
				if (held.hasTagCompound())
					tagCom = held.getTagCompound();
				else
					tagCom = new NBTTagCompound();
				tagCom.getCompoundTag(ItemInvBase.basicDataKey).setString(
						ItemInventorySack.sackName, this.name);
				player.getHeldItem().setTagCompound(tagCom);
			}
		}
	}
	
}
