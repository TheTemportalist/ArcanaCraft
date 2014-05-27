package com.countrygamer.arcanacraft.common.quom;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import com.countrygamer.arcanacraft.common.block.ACBlocks;
import com.countrygamer.arcanacraft.common.extended.ExtendedArcanePlayer;
import com.countrygamer.arcanacraft.common.quom.Tiers.Cast;

public class QuomBind extends Quom {
	
	public QuomBind(String name, Quom parent) {
		super(name, parent);
	}
	
	@Override
	public void onUse(EntityPlayer player, ExtendedArcanePlayer arcanePlayer, World world, int x,
			int y, int z, int side, Cast castTier) {
		if (castTier == Tiers.Cast.HAND) return;
		
		if (world.getBlock(x, y, z) == ACBlocks.bindableCore) {
			world.setBlock(x, y, z, ACBlocks.quomBinder, 0, 3);
			return;
		}
		
		// ArcanaCraft.logger.info("Quom Bind");
		
		@SuppressWarnings("rawtypes")
		List list = world.getEntitiesWithinAABB(EntityItem.class,
				AxisAlignedBB.getBoundingBox(x - 0, y + 1, z - 0, x + 1, y + 2, z + 1));
		if (list.isEmpty()) return;
		List<EntityItem> ents = new ArrayList<EntityItem>();
		for (Object obj : list) {
			if (obj instanceof EntityItem) ents.add((EntityItem) obj);
		}
		
		// ArcanaCraft.logger.info("Searching...");
		
		ItemStack output = null;
		BindRecipes recipes = BindRecipes.getRecipes();
		recipeSearch:
		for (EntityItem ent1 : ents) {
			for (EntityItem ent2 : ents) {
				if (!ent1.equals(ent2)) {
					// ArcanaCraft.logger.info("Found 2 stacks");
					ItemStack[] inputs = new ItemStack[] {
							ent1.getEntityItem(), ent2.getEntityItem()
					};
					output = recipes.getRecipeOutput(BindRecipes.Type.QUOM, inputs);
					if (output != null) {
						// ArcanaCraft.logger.info("Found Output");
						
						if (castTier == Tiers.Cast.BASIC) {
							ItemStack ent1Stack = ent1.getEntityItem().copy();
							ent1Stack.stackSize--;
							
							ItemStack ent2Stack = ent2.getEntityItem().copy();
							ent2Stack.stackSize--;
							
							this.checkStacks(ent1Stack, ent1, ent2Stack, ent2);
						}
						else if (castTier == Tiers.Cast.ADVANCED) {
							int maxOutput = getLowestSize(inputs);
							output.stackSize = maxOutput;
							
							ItemStack ent1Stack = ent1.getEntityItem().copy();
							ent1Stack.stackSize -= maxOutput;
							ItemStack ent2Stack = ent2.getEntityItem().copy();
							ent2Stack.stackSize -= maxOutput;
							
							this.checkStacks(ent1Stack, ent1, ent2Stack, ent2);
						}
						
						double avgX = (ent1.posX + ent2.posX) / 2;
						double avgY = ent1.posY;
						double avgZ = (ent1.posZ + ent2.posZ) / 2;
						if (!world.isRemote)
							world.spawnEntityInWorld(new EntityItem(world, avgX, avgY, avgZ, output));
						
						break recipeSearch;
					}
				}
			}
		}
		
	}
	
	private int getLowestSize(ItemStack[] inputs) {
		int lowest = 0;
		for (int i = 0; i < inputs.length; i++) {
			if (lowest == 0 || inputs[i].stackSize < lowest) lowest = inputs[i].stackSize;
		}
		return lowest;
	}
	
	private void checkStacks(ItemStack ent1Stack, EntityItem ent1, ItemStack ent2Stack,
			EntityItem ent2) {
		if (ent1Stack.stackSize > 0)
			ent1.setEntityItemStack(ent1Stack);
		else
			ent1.setDead();
		if (ent2Stack.stackSize > 0)
			ent2.setEntityItemStack(ent2Stack);
		else
			ent2.setDead();
	}
	
	
	@Override
	public void onRightClick(EntityPlayer player, ExtendedArcanePlayer arcanePlayer, World world,
			Cast castTier) {
	}
	
}
