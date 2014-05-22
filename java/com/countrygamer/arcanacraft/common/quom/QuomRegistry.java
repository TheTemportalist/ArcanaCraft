package com.countrygamer.arcanacraft.common.quom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.world.World;

import com.countrygamer.arcanacraft.common.extended.ExtendedArcanePlayer;
import com.countrygamer.arcanacraft.common.quom.Tiers.Cast;
import com.countrygamer.arcanacraft.common.quom.Tiers.MANUS;

public class QuomRegistry {
	
	public static final Map<String, Integer> quomKeys = new HashMap<String, Integer>();
	public static final List<Quom> quomRegistry = new ArrayList<Quom>();
	
	public static Quom lightFire;
	public static Quom fireBall;
	public static Quom furnace;
	public static Quom extract;
	public static Quom bindBasic;
	public static Quom teleport;
	
	public static void registerQuom(Quom quom) {
		QuomRegistry.quomKeys.put(quom.getName(), QuomRegistry.quomRegistry.size());
		QuomRegistry.quomRegistry.add(quom);
	}
	
	public static void registerQuoms() {
		
		QuomRegistry.lightFire = new QuomFire("Light Fire");
		
		QuomRegistry.fireBall = new QuomFireBall("Fire Ball", null);
		
		QuomRegistry.furnace = new QuomSmelt("Furnace", null);
		
		QuomRegistry.extract = new Quom("Rescindo") {

			@Override
			public void onUse(ExtendedArcanePlayer arcanePlayer, World world, int x,
					int y, int z, int side, Cast castTier, MANUS manusTier) {
				
			}
			
		};
		
		QuomRegistry.bindBasic = new Quom("Bind") {

			@Override
			public void onUse(ExtendedArcanePlayer arcanePlayer, World world, int x,
					int y, int z, int side, Cast castTier, MANUS manusTier) {
				
			}
			
		};
				
		QuomRegistry.teleport = new QuomTeleport("Teleport", null);
		
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
		if (!QuomRegistry.canPlayerLearn(arcanePlayer, quomKey)) return;
		// System.out.println("Unlocking " + quomKey);
		Quom quom = QuomRegistry.getQuom(quomKey);
		
		Quom[] quoms = arcanePlayer.getQuoms();
		quoms[quom.getID()] = quom;
		arcanePlayer.setQuoms(quoms);
	}
	
}
