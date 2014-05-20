package com.countrygamer.arcanacraft.client.particle;

public enum EnumSmokeColor {
	
	BLUE(0, 0, 200), RED(200, 0, 0), GREEN(0, 200, 0),
	PINK(255, 0, 160), PURPLE(90, 0, 190), YELLOW(255, 255, 0),
	ORANGE(220, 170, 0);
	
	public final int r, g, b;
	
	private EnumSmokeColor(int r, int g, int b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
}
