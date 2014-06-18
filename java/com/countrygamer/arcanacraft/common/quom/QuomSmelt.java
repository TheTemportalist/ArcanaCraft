package com.countrygamer.arcanacraft.common.quom;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.countrygamer.arcanacraft.common.extended.ExtendedArcanePlayer;
import com.countrygamer.arcanacraft.common.quom.Tiers.Cast;

public class QuomSmelt extends Quom {
	
	public QuomSmelt(String name, Quom parent, int col, int row) {
		super(name, parent, col, row);
	}
	
	@Override
	public boolean onUse(EntityPlayer player, ExtendedArcanePlayer arcanePlayer, World world, int x,
			int y, int z, int side, Tiers.Cast castTier) {		
		FurnaceRecipes recipes = FurnaceRecipes.smelting();
		
		float xp = 0.0F;
		int stackSize = 0;
		
		if (castTier != Tiers.Cast.HAND) {
			@SuppressWarnings("rawtypes")
			List list = world.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB
					.getBoundingBox(x - 0, y + 1, z - 0, x + 1, y + 2, z + 1));
			
			for (Object obj : list) {
				if (obj instanceof EntityItem) {
					EntityItem itemEnt = (EntityItem) obj;
					ItemStack itemStack = itemEnt.getEntityItem();
					
					ItemStack result = recipes.getSmeltingResult(itemStack);
					if (result != null) {
						xp = recipes.func_151398_b(result);
						
						if (castTier == Tiers.Cast.BASIC) {
							stackSize = result.stackSize = 1;
							
							world.spawnEntityInWorld(new EntityItem(world,
									itemEnt.posX, itemEnt.posY, itemEnt.posZ, result));
							
							itemStack.stackSize -= 1;
							if (itemStack.stackSize > 0) {
								itemEnt.setEntityItemStack(itemStack);
							}
							else {
								itemEnt.setDead();
							}
							
						}
						else if (castTier == Tiers.Cast.ADVANCED) {
							stackSize = result.stackSize = itemStack.stackSize;
							
							world.spawnEntityInWorld(new EntityItem(world,
									itemEnt.posX, itemEnt.posY, itemEnt.posZ, result));
							
							itemEnt.setDead();
						}
						
						return true;
					}
				}
			}
		}
		
		Block block = world.getBlock(x, y, z);
		int blockMeta = world.getBlockMetadata(x, y, z);
		// System.out.println(block.getUnlocalizedName());
		
		ItemStack blockStack = new ItemStack(block, 1, blockMeta);
		
		ItemStack result = recipes.getSmeltingResult(blockStack);
		
		if (result != null && Block.getBlockFromItem(result.getItem()) != null
				&& result.stackSize == blockStack.stackSize) {
			xp = recipes.func_151398_b(result);
			stackSize = result.stackSize;
			world.setBlock(x, y, z, Block.getBlockFromItem(result.getItem()),
					result.getItemDamage(), 3);
			
			this.spawnOrbs(arcanePlayer.player, stackSize, xp);
			// TODO, dormat flux
			
			return true;
		}
		
		return false;
	}
	
	private void spawnOrbs(EntityPlayer player, int i, float xp) {
		if (!player.worldObj.isRemote) {
			float f = xp;
			int j;
			
			if (f == 0.0F) {
				i = 0;
			}
			else if (f < 1.0F) {
				j = MathHelper.floor_float((float) i * f);
				
				if (j < MathHelper.ceiling_float_int((float) i * f)
						&& (float) Math.random() < (float) i * f - (float) j) {
					++j;
				}
				
				i = j;
			}
			
			while (i > 0) {
				j = EntityXPOrb.getXPSplit(i);
				i -= j;
				player.worldObj.spawnEntityInWorld(new EntityXPOrb(player.worldObj,
						player.posX, player.posY + 0.5D, player.posZ + 0.5D, j));
			}
		}
		
	}
	
	@Override
	public void onRightClick(EntityPlayer player, ExtendedArcanePlayer arcanePlayer, World world,
			Cast castTier) {
	}
	
}
