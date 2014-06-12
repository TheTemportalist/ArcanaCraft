package com.countrygamer.arcanacraft.common.extended;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.countrygamer.arcanacraft.common.quom.Quom;
import com.countrygamer.arcanacraft.common.quom.QuomRegistry;

public class Caste {

	public static Caste pyromancy;
	
	public static void registerCastes() {
		
		Caste.pyromancy = new Caste("Pyromancy");
		Caste.pyromancy.addAvailibleQuom(QuomRegistry.lightFire);
		
		
	}
	
	private static final Map<String, Caste> CASTES = new HashMap<String, Caste>();
	
	public static void registerCaste(Caste caste) {
		if (!Caste.CASTES.containsKey(caste.getName())) {
			Caste.CASTES.put(caste.getName(), caste);
		}
	}
	
	public static Caste getCaste(String name) {
		return Caste.CASTES.get(name);
	}
	
	private final String name;
	private final List<String> availibleQuoms = new ArrayList<String>();
	
	public Caste(String name) {
		this.name = name;
		
		Caste.registerCaste(this);
	}
	
	public String getName() {
		return this.name;
	}
	
	public void addAvailibleQuom(Quom quom) {
		this.availibleQuoms.add(quom.getName());
	}
	
	public boolean isQuomAvailible(String name) {
		return this.availibleQuoms.contains(name);
	}
	
	public Quom getAvailibleQuom(String name) {
		if (this.isQuomAvailible(name)) {
			return QuomRegistry.getQuom(name);
		}
		return null;
	}
	
}
