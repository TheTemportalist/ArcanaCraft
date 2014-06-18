package com.countrygamer.arcanacraft.common.quom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuomRegistry {
	
	public static final Map<String, Integer> quomKeys = new HashMap<String, Integer>();
	public static final List<Quom> quomRegistry = new ArrayList<Quom>();
	
	/** Is the smallest column used to display a achievement on the GUI. */
	public static int minDisplayColumn;
	/** Is the smallest row used to display a achievement on the GUI. */
	public static int minDisplayRow;
	/** Is the biggest column used to display a achievement on the GUI. */
	public static int maxDisplayColumn;
	/** Is the biggest row used to display a achievement on the GUI. */
	public static int maxDisplayRow;
	
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
	public static Quom growth;
	public static Quom tempest;
	// public static Quom quell;
	
	public static Quom filter;
	
	public static Quom manus_Creative;
	
	public static void registerQuoms() {
		
		QuomRegistry.dig = new QuomDig("Dig", 0, 0);
		
		QuomRegistry.luminize = new QuomLuminize("Luminize", null, 0, -2);
		
		QuomRegistry.extract = new QuomExtract("Rescindo", 2, -2);
		
		QuomRegistry.lightFire = new QuomFire("Light Fire", null, 2, 0);
		
		QuomRegistry.fireBall = new QuomFireBall("Fire Ball", null, 2, 2);
		
		QuomRegistry.furnace = new QuomSmelt("Furnace", null, 0, 2);
		
		QuomRegistry.bind = new QuomBind("Bind", null, -2, 2);
		
		QuomRegistry.data = new QuomData("Data", null, -2, 0);
		
		QuomRegistry.teleport = new QuomTeleport("Teleport", null, -2, -2);
		
		QuomRegistry.fastTravel = new QuomEvaporate("Evaporate", null, -2, -4);
		
		// QuomRegistry.quell = new QuomQuell("Quell", null);
		
		QuomRegistry.connector = new QuomConnector("Connector", null, 0, -4);
		
		QuomRegistry.manus_Creative = new QuomCreateManus("Manus", 2, -4);
		
		QuomRegistry.growth = new QuomGrowth("Growth", null, 4, -4);
		
		QuomRegistry.tempest = new QuomTempest("Tempest", null, 4, -2);
		
		QuomRegistry.filter = new QuomFilter("Filter", null, 4, 0);
		
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
