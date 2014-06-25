package com.countrygamer.arcanacraft.common.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

import com.countrygamer.arcanacraft.common.ACOptions;
import com.countrygamer.arcanacraft.common.ArcanaCraft;
import com.countrygamer.arcanacraft.common.block.ACBlocks;
import com.countrygamer.arcanacraft.common.extended.ExtendedArcanePlayer;
import com.countrygamer.countrygamercore.base.common.tile.TileEntityTankBase;
import com.countrygamer.countrygamercore.base.extended.ExtendedEntity;
import com.countrygamer.countrygamercore.common.item.ItemBase;

public class ItemArcana extends ItemBase {
	
	boolean debug = false;
	
	public ItemArcana(String modid, String name) {
		super(modid, name);
		this.setMaxStackSize(1);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		ExtendedArcanePlayer arcanePlayer = (ExtendedArcanePlayer) ExtendedEntity.getExtended(
				player, ExtendedArcanePlayer.class);
		if (arcanePlayer == null) return itemStack;
		
		if (!arcanePlayer.isPlayerArcaic()) arcanePlayer.setArcaic(true);
		
		if (debug) {
			
		}
		else {
			int id = player.isSneaking() ? ACOptions.arcanaGuiReset : ACOptions.arcanaGui;
			player.openGui(ArcanaCraft.pluginID, id, world, (int) player.posX, (int) player.posY,
					(int) player.posZ);
		}
		
		return itemStack;
	}
	
	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y,
			int z, int side, float par8, float par9, float par10) {
		if (debug) {
			TileEntity tileEntity = world.getTileEntity(x, y, z);
			if (tileEntity != null && tileEntity instanceof TileEntityTankBase) {
				((TileEntityTankBase) tileEntity).fill(ForgeDirection.UNKNOWN, new FluidStack(
						ACBlocks.liquidManus, 1000), true);
			}
			return true;
		}
		return false;
	}
	
}
