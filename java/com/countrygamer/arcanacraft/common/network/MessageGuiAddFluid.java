package com.countrygamer.arcanacraft.common.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import com.countrygamer.arcanacraft.common.lib.ACUtil;
import com.countrygamer.countrygamercore.Base.common.network.AbstractMessage;
import com.countrygamer.countrygamercore.Base.common.tile.TileEntityTankBase;

public class MessageGuiAddFluid extends AbstractMessage {
	
	int x, y, z;
	
	public MessageGuiAddFluid() {
	}
	
	public MessageGuiAddFluid(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
		
	}
	
	@Override
	public void writeTo(ByteBuf buffer) {
		buffer.writeInt(this.x);
		buffer.writeInt(this.y);
		buffer.writeInt(this.z);
		
	}
	
	@Override
	public void readFrom(ByteBuf buffer) {
		this.x = buffer.readInt();
		this.y = buffer.readInt();
		this.z = buffer.readInt();
		
	}
	
	@Override
	public void handleOnClient(EntityPlayer player) {
		this.addFluid(player);
	}
	
	@Override
	public void handleOnServer(EntityPlayer player) {
		this.addFluid(player);
	}
	
	private void addFluid(EntityPlayer player) {
		TileEntity tileEntity = player.worldObj.getTileEntity(x, y, z);
		if (tileEntity != null) {
			ItemStack retStack = ACUtil.addFluidWithGui(player, (TileEntityTankBase) tileEntity,
					player.inventory.getItemStack());
			if (!ItemStack.areItemStacksEqual(player.inventory.getItemStack(), retStack)) {
				player.inventory.setItemStack(retStack);
				player.inventory.markDirty();
				tileEntity.markDirty();
				player.worldObj.markBlockForUpdate(x, y, z);
			}
		}
	}
	
}
