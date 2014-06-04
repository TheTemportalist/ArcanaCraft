package com.countrygamer.arcanacraft.common.quom;

import java.util.List;

import com.countrygamer.arcanacraft.common.extended.ExtendedArcanePlayer;

public abstract class QuomCreative extends Quom {
	
	public QuomCreative(String name) {
		super(name);
	}
	
	public void addHoverInfo(ExtendedArcanePlayer player, List<String> info) {
		info.add("Creative/Command Only");
	}
	
	@Override
	public boolean canLearnNormally(ExtendedArcanePlayer arcanePlayer) {
		return false;
	}
	
}