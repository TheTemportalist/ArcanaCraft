package com.countrygamer.arcanacraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.IExtendedEntityProperties;

import org.lwjgl.opengl.GL11;

import com.countrygamer.arcanacraft.common.ArcanaCraft;
import com.countrygamer.arcanacraft.common.extended.ExtendedArcanePlayer;
import com.countrygamer.arcanacraft.common.quom.Quom;
import com.countrygamer.core.Base.Plugin.ExtendedEntity;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ArcaneOverlay extends Gui {
	
	final Minecraft mc;
	
	public ArcaneOverlay(Minecraft mc) {
		super();
		this.mc = mc;
	}
	
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void renderGameOverlay(RenderGameOverlayEvent.Post event) {
		if (event.isCanceled()) return;
		
		int width = event.resolution.getScaledWidth();
		int height = event.resolution.getScaledHeight();
		/*
		int width = Minecraft.getMinecraft().displayWidth;
		int height = Minecraft.getMinecraft().displayHeight;
		ScaledResolution get = new ScaledResolution(
				Minecraft.getMinecraft().gameSettings, width, height);
		int scale = get.getScaleFactor();
		if (scale == 1 || scale == 2 || scale == 3) {
			width /= scale;
			height /= scale;
		}
		 */
		
		IExtendedEntityProperties props = ExtendedEntity.getExtended(
				this.mc.thePlayer, ExtendedArcanePlayer.class);
		if (props != null) {
			ExtendedArcanePlayer arcanePlayer = (ExtendedArcanePlayer) props;
			
			if (!arcanePlayer.isPlayerArcaic()) return;
			
			if (event.type == ElementType.EXPERIENCE) {
				
				int w = 80;
				int h = 10;
				float frac = ((float) arcanePlayer.getManus())
						/ ((float) arcanePlayer.getMaxManus());
				float wFrac = frac * ((float) w);
				w = (int) wFrac;
				
				// System.out.println("\n" + arcanePlayer.getStamina());
				// System.out.println(frac + "\n" + wFrac + "\n" + w);
				
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				GL11.glDisable(GL11.GL_LIGHTING);
				
				int quomX = 202;
				int quomY = 195;
				this.mc.getTextureManager().bindTexture(
						new ResourceLocation(ArcanaCraft.pluginID,
								"textures/gui/GuiIcons.png"));
				this.drawTexturedModalRect(quomX, quomY, 0, 20, 18, 18);
				Quom quom = arcanePlayer.getCurrentQuom();
				if (quom != null) quom.draw(this.mc, this, quomX + 1, quomY + 1);
				
				this.mc.getTextureManager().bindTexture(
						new ResourceLocation(ArcanaCraft.pluginID,
								"textures/gui/GuiIcons.png"));
				this.drawTexturedModalRect(223, 190, 0, 10, 80, h);
				this.drawTexturedModalRect(223, 190, 0, 0, w, h);
				
			}
			if (event.type == ElementType.TEXT) {
				String s = arcanePlayer.getManus() + " / "
						+ arcanePlayer.getMaxManus();
				this.mc.fontRenderer.drawString(s, 226, 191, 0xFFFFFF);
			}
		}
	}
	
}
