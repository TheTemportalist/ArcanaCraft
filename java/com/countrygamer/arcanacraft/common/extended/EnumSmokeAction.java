package com.countrygamer.arcanacraft.common.extended;


public enum EnumSmokeAction {
	
	NONE(0), TELEPORT(1), SMOKE(2);
	
	private final int id;
	
	private EnumSmokeAction(int id) {
		this.id = id;
	}
	
	public int getID() {
		return this.id;
	}
	
	public static EnumSmokeAction getEnumFromID(int id) {
		EnumSmokeAction[] vals = EnumSmokeAction.values();
		for (int i = 0; i < vals.length; i++) {
			EnumSmokeAction action = vals[i];
			if (id == action.id) {
				return action;
			}
		}
		return null;
	}
	
}
