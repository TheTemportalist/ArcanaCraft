package com.countrygamer.arcanacraft.client.render;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import com.countrygamer.arcanacraft.client.render.models.ModelGauntlet;
import com.countrygamer.arcanacraft.common.ArcanaCraft;
import com.countrygamer.countrygamercore.base.client.render.models.ModelBase;
import com.countrygamer.countrygamercore.common.lib.util.UtilRender;

public class ItemGauntletRenderer implements IItemRenderer {
	
	final ResourceLocation gauntletTexture = UtilRender.getResource(ArcanaCraft.pluginID, "model/item/",
			"gauntlet");
	ModelGauntlet model;
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}
	
	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		return true;
	}
	
	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		if (type == IItemRenderer.ItemRenderType.ENTITY) GL11.glTranslatef(-0.5F, 0.0F, -0.5F);
		
		GL11.glRotatef(0.0F, 0.0F, 0.0F, 0.0F);
		
		this.model = new ModelGauntlet();
		
		//if (type != IItemRenderer.ItemRenderType.FIRST_PERSON_MAP) return;
		
		GL11.glPushMatrix();
		
		UtilRender.bindResource(this.gauntletTexture);

		GL11.glTranslatef(1.0F, 0.0F, 1.0F);
		
		if (type == ItemRenderType.EQUIPPED) {
			//GL11.glRotatef(45.0F, 0.0F, 0.0F, 1.0F);
		}
		else if (type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
			GL11.glTranslatef(-0.5F, 0.0F, 0.25F);
			GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-60.0F, 0.0F, 0.0F, 1.0F);
		}
		
		this.model.renderModel(ModelBase.f5);
		
		GL11.glPopMatrix();
		
	}
	
}
