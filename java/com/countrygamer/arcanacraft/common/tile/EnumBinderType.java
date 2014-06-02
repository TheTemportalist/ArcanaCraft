package com.countrygamer.arcanacraft.common.tile;

public enum EnumBinderType {
	BIND_QUOM(0, ""), QUOM(1, "Quom"), ITEM(2, "Item"), FLUID(3, "Fluid");
	
	final String name;
	final int id;
	
	EnumBinderType(int id, String name) {
		this.name = name;
		this.id = id;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
	public static EnumBinderType getTypeFromID(int id) {
		EnumBinderType[] vals = EnumBinderType.values();
		for (int i = 0; i < vals.length; i++) {
			if (vals[i].id == id) {
				return vals[i];
			}
		}
		return null;
	}
	
}
