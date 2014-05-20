package com.countrygamer.arcanacraft.common.quom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.countrygamer.arcanacraft.common.extended.ExtendedArcanePlayer;

public class QuomRegistry {
	
	public static final Map<String, Integer> quomKeys = new HashMap<String, Integer>();
	public static final List<Quom> quomRegistry = new ArrayList<Quom>();
	
	public static Quom lightFire;
	public static Quom fireBall;
	public static Quom fireBall2;
	public static Quom furnace;
	public static Quom extract;
	public static Quom bindBasic;
	public static Quom bindAdvanced;
	//public static Quom teleport;
	
	public static void registerQuom(Quom quom) {
		QuomRegistry.quomKeys.put(quom.getName(), QuomRegistry.quomRegistry.size());
		QuomRegistry.quomRegistry.add(quom);
	}
	
	public static void registerQuoms() {
		QuomRegistry.lightFire = new Quom("Light Fire") {
			
			@Override
			public double getReachLength() {
				return 5.0D;
			}
			
			@Override
			public void onUse(ExtendedArcanePlayer arcanePlayer, World world, int x,
					int y, int z, int side) {
				super.onUse(arcanePlayer, world, x, y, z, side);
				EntityPlayer player = arcanePlayer.player;
				
				if (!player.canPlayerEdit(x, y, z, side, new ItemStack(
						Items.flint_and_steel))) {
					return;
				}
				else {
					if (world.isAirBlock(x, y, z)) {
						world.playSoundEffect((double) x + 0.5D, (double) y + 0.5D,
								(double) z + 0.5D, "fire.ignite", 1.0F,
								(new Random()).nextFloat() * 0.4F + 0.8F);
						world.setBlock(x, y, z, Blocks.fire);
					}
				}
				
			}
		};
		
		QuomRegistry.fireBall = new Quom("Fire Ball") {
			
			@Override
			public void onUse(ExtendedArcanePlayer arcanePlayer, World world, int x,
					int y, int z, int side) {
				super.onUse(arcanePlayer, world, x, y, z, side);
				
				// TODO fire a fire ball
				
			}
			
		};
		
		QuomRegistry.fireBall2 = new Quom("Lava Ball");
		
		QuomRegistry.furnace = new Quom("Furnace");
		
		QuomRegistry.extract = new Quom("Rescindo");
		
		QuomRegistry.bindBasic = new Quom("Bind (Basic)");
		
		QuomRegistry.bindAdvanced = new Quom("Bind (Advanced)");
		
		
		
		// QuomRegistry.teleport = new QuomTeleport("Teleport");
		
	}
	
	public static Quom getQuom(String key) {
		if (!QuomRegistry.quomKeys.containsKey(key)) return null;
		int id = QuomRegistry.quomKeys.get(key);
		return QuomRegistry.quomRegistry.get(id);
	}
	
	public static boolean canPlayerLearn(ExtendedArcanePlayer arcanePlayer,
			String quomKey) {
		Quom quom = QuomRegistry.getQuom(quomKey);
		
		Quom parentQuom = quom.getParent();
		boolean parent_is_trained = parentQuom == null;
		if (parentQuom != null) {
			Quom[] activeQuoms = arcanePlayer.getQuoms();
			for (int i = 0; i < activeQuoms.length; i++) {
				if (parentQuom.equals(activeQuoms[i])) {
					parent_is_trained = true;
					break;
				}
			}
		}
		
		return parent_is_trained
				&& !QuomRegistry.doesPlayerHaveQuom(arcanePlayer, quomKey);
	}
	
	public static boolean doesPlayerHaveQuom(ExtendedArcanePlayer arcanePlayer,
			String quomKey) {
		Quom quom = QuomRegistry.getQuom(quomKey);
		
		Quom[] quoms = arcanePlayer.getQuoms();
		for (int i = 0; i < quoms.length; i++) {
			if (quom.equals(quoms[i])) {
				return true;
			}
		}
		return false;
	}
	
	public static void unlockQuom(ExtendedArcanePlayer arcanePlayer, String quomKey) {
		if (!QuomRegistry.canPlayerLearn(arcanePlayer, quomKey))
			return;
		
		Quom quom = QuomRegistry.getQuom(quomKey);
		
		Quom[] quoms = arcanePlayer.getQuoms();
		quoms[quom.getID()] = quom;
		arcanePlayer.setQuoms(quoms);
	}
	
}
