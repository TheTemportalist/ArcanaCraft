package com.countrygamer.arcanacraft.common.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

import com.mojang.authlib.GameProfile;

public class ActuatorFakePlayer extends EntityPlayer {
	
	TileEntityActuator tileEntity;
	
	public ActuatorFakePlayer(TileEntityActuator actuator) {
		super(actuator.getWorldObj(), new GameProfile("", "[Actuator]"));
		this.tileEntity = actuator;
	}
	
	@Override
	public void addChatMessage(IChatComponent s) {
	}
	
	public void addChatMessage(String s) {
	}
	
	@Override
	public boolean canCommandSenderUseCommand(int i, String s) {
		return false;
	}
	
	@Override
	public void openGui(Object mod, int modGuiId, World world, int x, int y, int z) {
	}
	
	@Override
	public ChunkCoordinates getPlayerCoordinates() {
		return new ChunkCoordinates(this.tileEntity.xCoord, this.tileEntity.yCoord,
				this.tileEntity.zCoord);
	}
	
}
