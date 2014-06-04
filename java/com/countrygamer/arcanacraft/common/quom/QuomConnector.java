package com.countrygamer.arcanacraft.common.quom;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.countrygamer.arcanacraft.common.extended.ExtendedArcanePlayer;
import com.countrygamer.arcanacraft.common.quom.Tiers.Cast;
import com.countrygamer.arcanacraft.common.tile.TileEntityAugmentedTank;
import com.countrygamer.arcanacraft.common.tile.TileEntityManusPowered;
import com.countrygamer.countrygamercore.lib.CoreUtil;

public class QuomConnector extends Quom {
	
	public QuomConnector(String name, Quom parent) {
		super(name, parent);
	}
	
	@Override
	public boolean onUse(EntityPlayer player, ExtendedArcanePlayer arcanePlayer, World world,
			int x, int y, int z, int side, Cast castTier) {
		
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		
		if (tileEntity != null) {
			NBTTagCompound data = arcanePlayer.getQuomData(this.getName());
			if (data == null) data = new NBTTagCompound();
			
			int[] sourceTileCoords = data.getIntArray("sourceTileEntity");
			int[] targetTileCoords = data.getIntArray("targetTankTileEntity");
			
			if (sourceTileCoords.length == 0 && tileEntity instanceof TileEntityAugmentedTank) {
				// set source
				sourceTileCoords = new int[] {
						x, y, z
				};
				data.setIntArray("sourceTileEntity", sourceTileCoords);
				
				CoreUtil.sendMessageToPlayer(player, "Set source tank's coordinates");
				arcanePlayer.addQuomData(this.getName(), data);
				return true;
			}
			else if (targetTileCoords.length == 0 && tileEntity instanceof TileEntityManusPowered) {
				// set target
				targetTileCoords = new int[] {
						x, y, z
				};
				data.setIntArray("targetTankTileEntity", targetTileCoords);
				
				CoreUtil.sendMessageToPlayer(player, "Set target's coordinates");
				arcanePlayer.addQuomData(this.getName(), data);
				return true;
			}
			
		}
		
		return false;
	}
	
	@Override
	public void onRightClick(EntityPlayer player, ExtendedArcanePlayer arcanePlayer, World world,
			Cast castTier) {
		NBTTagCompound data = arcanePlayer.getQuomData(this.getName());
		if (data != null) {
			
			if (player.isSneaking()) {
				arcanePlayer.addQuomData(this.getName(), new NBTTagCompound());
				return;
			}
			
			int[] sourceTileCoords = data.getIntArray("sourceTileEntity");
			int[] targetTileCoords = data.getIntArray("targetTankTileEntity");
			
			if (sourceTileCoords.length != 0) {
				CoreUtil.sendMessageToPlayer(player, "Source tank is at: " + sourceTileCoords[0]
						+ ", " + sourceTileCoords[1] + ", " + sourceTileCoords[2]);
			}
			if (targetTileCoords.length != 0) {
				CoreUtil.sendMessageToPlayer(player, "Target block is at: " + targetTileCoords[0]
						+ ", " + targetTileCoords[1] + ", " + targetTileCoords[2]);
			}
			
			if (sourceTileCoords.length != 0 && targetTileCoords.length != 0) {
				// connect the two
				TileEntityManusPowered poweredTE = (TileEntityManusPowered) world.getTileEntity(
						targetTileCoords[0], targetTileCoords[1], targetTileCoords[2]);
				
				if (poweredTE != null) {
					poweredTE.setSourceTankCoords(sourceTileCoords);
					
					CoreUtil.sendMessageToPlayer(player, "Notified target of source tank");
					
					arcanePlayer.removeQuomData(this.getName());
				}
			}
			
		}
		else
			arcanePlayer.addQuomData(this.getName(), new NBTTagCompound());
	}
	
}