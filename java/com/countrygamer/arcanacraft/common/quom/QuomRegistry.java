package com.countrygamer.arcanacraft.common.quom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuomRegistry {
	
	public static final Map<String, Integer> quomKeys = new HashMap<String, Integer>();
	public static final List<Quom> quomRegistry = new ArrayList<Quom>();
	
	public static Quom dig;
	
	public static Quom lightFire;
	public static Quom fireBall;
	public static Quom furnace;
	public static Quom extract;
	public static Quom bind;
	public static Quom teleport;
	public static Quom fastTravel;
	public static Quom flash;
	public static Quom barrier;
	public static Quom icePath;
	public static Quom luminize;
	public static Quom illuminate;
	public static Quom data;
	public static Quom connector;
	// public static Quom quell;
	
	public static Quom manus_Creative;
	
	public static void registerQuoms() {
		
		QuomRegistry.dig = new QuomDig("Dig");
		
		QuomRegistry.luminize = new QuomLuminize("Luminize", null);
		
		QuomRegistry.extract = new QuomExtract("Rescindo");
		
		QuomRegistry.lightFire = new QuomFire("Light Fire", null);
		
		QuomRegistry.fireBall = new QuomFireBall("Fire Ball", null);
		
		QuomRegistry.furnace = new QuomSmelt("Furnace", null);
		
		QuomRegistry.bind = new QuomBind("Bind", null);
		
		QuomRegistry.data = new QuomData("Data", null);
		
		QuomRegistry.teleport = new QuomTeleport("Teleport", null);
		
		QuomRegistry.fastTravel = new QuomEvaporate("Evaporate", null);
		
		// QuomRegistry.quell = new QuomQuell("Quell", null);
		
		QuomRegistry.connector = new QuomConnector("Connector", null);
		
		QuomRegistry.manus_Creative = new QuomCreateManus("Manus");
		
		
		
	}
	
	public static void registerQuom(Quom quom) {
		QuomRegistry.quomKeys.put(quom.getName(), QuomRegistry.quomRegistry.size());
		QuomRegistry.quomRegistry.add(quom);
	}
	
	public static Quom getQuom(String key) {
		if (!QuomRegistry.quomKeys.containsKey(key)) return null;
		int id = QuomRegistry.quomKeys.get(key);
		return QuomRegistry.quomRegistry.get(id);
	}
	
}
