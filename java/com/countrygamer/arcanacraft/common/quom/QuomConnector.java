package com.countrygamer.arcanacraft.common.quom;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.countrygamer.arcanacraft.common.extended.ExtendedArcanePlayer;
import com.countrygamer.arcanacraft.common.quom.Tiers.Cast;
import com.countrygamer.arcanacraft.common.tile.TileEntityAugmentedTank;
import com.countrygamer.arcanacraft.common.tile.TileEntityManusPowered;
import com.countrygamer.countrygamercore.common.lib.util.Player;

public class QuomConnector extends Quom {
	
	public QuomConnector(String name, Quom parent, int col, int row) {
		super(name, parent, col, row);
	}
	
	@Override
	public boolean onUse(EntityPlayer player, ExtendedArcanePlayer arcanePlayer, World world,
			int x, int y, int z, int side, Cast castTier) {
		
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		
		if (tileEntity != null) {
			NBTTagCompound data = arcanePlayer.getData("Quom_" + this.getName());
			if (data == null) data = new NBTTagCompound();
			
			int[] sourceTileCoords = data.getIntArray("sourceTileEntity");
			int[] targetTileCoords = data.getIntArray("targetTankTileEntity");
			
			if (sourceTileCoords.length == 0 && tileEntity instanceof TileEntityAugmentedTank) {
				// set source
				sourceTileCoords = new int[] {
						x, y, z
				};
				data.setIntArray("sourceTileEntity", sourceTileCoords);
				
				Player.sendMessageToPlayer(player, "Set source tank's coordinates");
				arcanePlayer.addData(this.getName(), data);
				this.checkForConnection(world, player, arcanePlayer, sourceTileCoords,
						targetTileCoords);
				return true;
			}
			else if (targetTileCoords.length == 0 && tileEntity instanceof TileEntityManusPowered) {
				// set target
				targetTileCoords = new int[] {
						x, y, z
				};
				data.setIntArray("targetTankTileEntity", targetTileCoords);
				
				Player.sendMessageToPlayer(player, "Set target's coordinates");
				arcanePlayer.addData(this.getName(), data);
				this.checkForConnection(world, player, arcanePlayer, sourceTileCoords,
						targetTileCoords);
				return true;
			}
			
		}
		
		return false;
	}
	
	@Override
	public void onRightClick(EntityPlayer player, ExtendedArcanePlayer arcanePlayer, World world,
			Cast castTier) {
		NBTTagCompound data = arcanePlayer.getData("Quom_" + this.getName());
		if (data != null) {
			
			if (player.isSneaking()) {
				arcanePlayer.addData("Quom_" + this.getName(), new NBTTagCompound());
				return;
			}
			
			int[] sourceTileCoords = data.getIntArray("sourceTileEntity");
			int[] targetTileCoords = data.getIntArray("targetTankTileEntity");
			
			if (sourceTileCoords.length != 0) {
				Player.sendMessageToPlayer(player, "Source tank is at: " + sourceTileCoords[0]
						+ ", " + sourceTileCoords[1] + ", " + sourceTileCoords[2]);
			}
			if (targetTileCoords.length != 0) {
				Player.sendMessageToPlayer(player, "Target block is at: " + targetTileCoords[0]
						+ ", " + targetTileCoords[1] + ", " + targetTileCoords[2]);
			}
			
		}
		else
			arcanePlayer.addData("Quom_" + this.getName(), new NBTTagCompound());
	}
	
	private void checkForConnection(World world, EntityPlayer player,
			ExtendedArcanePlayer arcanePlayer, int[] sourceTileCoords, int[] targetTileCoords) {
		if (sourceTileCoords.length != 0 && targetTileCoords.length != 0) {
			// connect the two
			TileEntityManusPowered poweredTE = (TileEntityManusPowered) world.getTileEntity(
					targetTileCoords[0], targetTileCoords[1], targetTileCoords[2]);
			
			if (poweredTE != null) {
				poweredTE.setSourceTankCoords(sourceTileCoords);
				
				Player.sendMessageToPlayer(player, "Notified target of source tank");
				
				arcanePlayer.removeData("Quom_" + this.getName());
			}
		}
		
	}
	
}
