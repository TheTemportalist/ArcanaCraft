package com.countrygamer.arcanacraft.common.quom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuomRegistry {
	
	public static final Map<String, Integer> quomKeys = new HashMap<String, Integer>();
	public static final List<Quom> quomRegistry = new ArrayList<Quom>();
	
	/** Is the smallest column used to display a achievement on the GUI. */
	public static int minDisplayColumn = 0;
	/** Is the smallest row used to display a achievement on the GUI. */
	public static int minDisplayRow = 0;
	/** Is the biggest column used to display a achievement on the GUI. */
	public static int maxDisplayColumn = 0;
	/** Is the biggest row used to display a achievement on the GUI. */
	public static int maxDisplayRow = 0;
	
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
		
		int maxWidth = 4;
		
		int[] xy = null;
		int x = 0;
		int y = 0;
		
		QuomRegistry.dig = new QuomDig("Dig", x, y);
		xy = next(x, y, maxWidth);
		x = xy[0];
		y = xy[1];
		QuomRegistry.luminize = new QuomLuminize("Luminize", null, x, y);
		xy = next(x, y, maxWidth);
		x = xy[0];
		y = xy[1];
		QuomRegistry.extract = new QuomExtract("Rescindo", x, y);
		xy = next(x, y, maxWidth);
		x = xy[0];
		y = xy[1];
		QuomRegistry.lightFire = new QuomFire("Light Fire", null, x, y);
		xy = next(x, y, maxWidth);
		x = xy[0];
		y = xy[1];
		QuomRegistry.fireBall = new QuomFireBall("Fire Ball", null, x, y);
		xy = next(x, y, maxWidth);
		x = xy[0];
		y = xy[1];
		QuomRegistry.furnace = new QuomSmelt("Furnace", null, x, y);
		xy = next(x, y, maxWidth);
		x = xy[0];
		y = xy[1];
		QuomRegistry.bind = new QuomBind("Bind", null, x, y);
		xy = next(x, y, maxWidth);
		x = xy[0];
		y = xy[1];
		QuomRegistry.data = new QuomData("Data", null, x, y);
		xy = next(x, y, maxWidth);
		x = xy[0];
		y = xy[1];
		QuomRegistry.teleport = new QuomTeleport("Teleport", null, x, y);
		xy = next(x, y, maxWidth);
		x = xy[0];
		y = xy[1];
		QuomRegistry.fastTravel = new QuomEvaporate("Evaporate", null, x, y);
		xy = next(x, y, maxWidth);
		x = xy[0];
		y = xy[1];
		/*
		QuomRegistry.quell = new QuomQuell("Quell", null);
		xy = next(x, y, maxWidth);
		x = xy[0];
		y = xy[1];
		 */
		QuomRegistry.connector = new QuomConnector("Connector", null, x, y);
		xy = next(x, y, maxWidth);
		x = xy[0];
		y = xy[1];
		QuomRegistry.manus_Creative = new QuomCreateManus("Manus", x, y);
		xy = next(x, y, maxWidth);
		x = xy[0];
		y = xy[1];
		QuomRegistry.growth = new QuomGrowth("Growth", null, x, y);
		xy = next(x, y, maxWidth);
		x = xy[0];
		y = xy[1];
		QuomRegistry.tempest = new QuomTempest("Tempest", null, x, y);
		xy = next(x, y, maxWidth);
		x = xy[0];
		y = xy[1];
		QuomRegistry.filter = new QuomFilter("Filter", null, x, y);
		
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
	
	private static int[] next(int x, int y, int maxW) {
		x += 2;
		if (x > maxW * 2) {
			x = 0;
			y += 2;
		}
		return new int[] {
				x, y
		};
	}
	
}
