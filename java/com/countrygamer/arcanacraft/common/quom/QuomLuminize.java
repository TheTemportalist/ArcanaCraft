package com.countrygamer.arcanacraft.common.quom;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import com.countrygamer.arcanacraft.common.extended.ExtendedArcanePlayer;
import com.countrygamer.arcanacraft.common.quom.Tiers.Cast;

public class QuomLuminize extends Quom {
	
	public QuomLuminize(String name, Quom parent) {
		super(name, parent);
	}
	
	public boolean canDiscover_Craft(ExtendedArcanePlayer arcanePlayer, ItemStack itemStack) {
		return itemStack.getItem() == Item.getItemFromBlock(Blocks.torch);
	}
	
	@Override
	public void onUse(EntityPlayer player, ExtendedArcanePlayer arcanePlayer, World world, int x,
			int y, int z, int side, Cast castTier) {
		int x1 = x, y1 = y, z1 = z;
		String blockText = "";
		if (!player.isSneaking()) {
			if (side == 0) { // bottom -Y
				y1--;
				blockText = "the space below that block";
			}
			else if (side == 1) { // top +Y
				y1++;
				blockText = "the space above that block";
			}
			else if (side == 2) { // north -Z
				z1--;
				blockText = "the space north of that block";
			}
			else if (side == 3) { // south +Z
				z1++;
				blockText = "the space south of that block";
			}
			else if (side == 4) { // west -X
				x1--;
				blockText = "the space west of that block";
			}
			else if (side == 5) { // east +X
				x1++;
				blockText = "the space east of that block";
			}
		}
		else
			blockText = "that block";
		
		int lightValue = world.getBlockLightValue(x1, y1, z1);
		if (!world.isRemote)
			player.addChatMessage(new ChatComponentText("The light level of " + blockText + " is "
					+ lightValue));
	}
	
	@Override
	public void onRightClick(EntityPlayer player, ExtendedArcanePlayer arcanePlayer, World world,
			Cast castTier) {
		if (player.isSneaking()) {
			int lightValue = world.getBlockLightValue((int) Math.floor(player.posX),
					(int) Math.floor(player.posY + 1), (int) Math.floor(player.posZ));
			String blockText = "the space above the block you are standing on";
			if (!world.isRemote)
				player.addChatMessage(new ChatComponentText("The light level of " + blockText
						+ " is " + lightValue));
		}
	}
	
}
