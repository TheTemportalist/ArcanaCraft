package com.countrygamer.arcanacraft.common.item;

import com.countrygamer.arcanacraft.client.render.models.armor.ModelArcaicArmor;
import com.countrygamer.countrygamercore.base.client.render.models.ModelBipedBase;
import com.countrygamer.countrygamercore.common.item.ItemArmorModelBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemArcaicArmor extends ItemArmorModelBase {
	
	public ItemArcaicArmor(String modid, String name, ArmorMaterial mat, int renderIndex,
			String type) {
		super(modid, name, mat, renderIndex, type);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	protected Class<? extends ModelBipedBase> getArmorModelClass() {
		return ModelArcaicArmor.class;
	}
	
}
