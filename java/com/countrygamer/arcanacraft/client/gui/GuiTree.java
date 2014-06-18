package com.countrygamer.arcanacraft.client.gui;

import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.countrygamer.arcanacraft.common.quom.Quom;
import com.countrygamer.arcanacraft.common.quom.QuomRegistry;
import com.countrygamer.countrygamercore.Base.client.gui.GuiScreenBase;

public class GuiTree extends GuiScreenBase {
	
	private static final int innerBoxLeft = QuomRegistry.minDisplayColumn * 24 - 112;
	private static final int innerBoxTop = QuomRegistry.minDisplayRow * 24 - 112;
	private static final int innerBoxRight = QuomRegistry.maxDisplayColumn * 24 - 77;
	private static final int innerBoxBottom = QuomRegistry.maxDisplayRow * 24 - 77;
	private static final ResourceLocation frameBackground = new ResourceLocation(
			"textures/gui/achievement/achievement_background.png");
	
	protected int xSize = 256;
	protected int ySize = 202;
	protected int mouseX_Saved;
	protected int mouseY_Saved;
	protected float some_float_value = 1.0F;
	protected double prevMapPosX;
	protected double prevMapPosY;
	protected double mapPosX;
	protected double mapPosY;
	protected double savedMapPosX_maybe;
	protected double savedMapPosY_maybe;
	private int isMouseButtonDown;
	
	public GuiTree() {
		short short1 = 141;
		short short2 = 141;
		Quom quom = QuomRegistry.quomRegistry.get(0);
		this.prevMapPosX = this.mapPosX = this.savedMapPosX_maybe = (double) (quom.displayColumn
				* 24 - short1 / 2 - 12);
		this.prevMapPosY = this.mapPosY = this.savedMapPosY_maybe = (double) (quom.displayRow * 24 - short2 / 2);
		
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float rpt) {
		int guiLeft;
		
		if (Mouse.isButtonDown(0)) {
			guiLeft = (this.width - this.xSize) / 2;
			int guiTop = (this.height - this.ySize) / 2;
			int i1 = guiLeft + 8;
			int j1 = guiTop + 17;
			
			if ((this.isMouseButtonDown == 0 || this.isMouseButtonDown == 1) && mouseX >= i1
					&& mouseX < i1 + 224 && mouseY >= j1 && mouseY < j1 + 155) {
				if (this.isMouseButtonDown == 0) {
					this.isMouseButtonDown = 1;
				}
				else {
					this.mapPosX -= (double) ((float) (mouseX - this.mouseX_Saved) * this.some_float_value);
					this.mapPosY -= (double) ((float) (mouseY - this.mouseY_Saved) * this.some_float_value);
					this.savedMapPosX_maybe = this.prevMapPosX = this.mapPosX;
					this.savedMapPosY_maybe = this.prevMapPosY = this.mapPosY;
				}
				
				this.mouseX_Saved = mouseX;
				this.mouseY_Saved = mouseY;
			}
		}
		else {
			this.isMouseButtonDown = 0;
		}
		
		guiLeft = Mouse.getDWheel();
		float f4 = this.some_float_value;
		
		if (guiLeft < 0) {
			this.some_float_value += 0.25F;
		}
		else if (guiLeft > 0) {
			this.some_float_value -= 0.25F;
		}
		
		this.some_float_value = MathHelper.clamp_float(this.some_float_value, 1.0F, 2.0F);
		
		if (this.some_float_value != f4) {
			float f5 = f4 * (float) this.xSize;
			float f1 = f4 * (float) this.ySize;
			float f2 = this.some_float_value * (float) this.xSize;
			float f3 = this.some_float_value * (float) this.ySize;
			this.mapPosX -= (double) ((f2 - f5) * 0.5F);
			this.mapPosY -= (double) ((f3 - f1) * 0.5F);
			this.savedMapPosX_maybe = this.prevMapPosX = this.mapPosX;
			this.savedMapPosY_maybe = this.prevMapPosY = this.mapPosY;
		}
		
		if (this.savedMapPosX_maybe < (double) innerBoxLeft) {
			this.savedMapPosX_maybe = (double) innerBoxLeft;
		}
		
		if (this.savedMapPosY_maybe < (double) innerBoxTop) {
			this.savedMapPosY_maybe = (double) innerBoxTop;
		}
		
		if (this.savedMapPosX_maybe >= (double) innerBoxRight) {
			this.savedMapPosX_maybe = (double) (innerBoxRight - 1);
		}
		
		if (this.savedMapPosY_maybe >= (double) innerBoxBottom) {
			this.savedMapPosY_maybe = (double) (innerBoxBottom - 1);
		}
		
		this.drawComponents(mouseX, mouseY, rpt);
	}
	
	@Override
	public void updateScreen() {
		this.prevMapPosX = this.mapPosX;
		this.prevMapPosY = this.mapPosY;
		double d0 = this.savedMapPosX_maybe - this.mapPosX;
		double d1 = this.savedMapPosY_maybe - this.mapPosY;
		
		if (d0 * d0 + d1 * d1 < 4.0D) {
			this.mapPosX += d0;
			this.mapPosY += d1;
		}
		else {
			this.mapPosX += d0 * 0.85D;
			this.mapPosY += d1 * 0.85D;
		}
		
	}
	
	protected void drawComponents(int mouseX, int mouseY, float rpt) {
		int currentMapPosX = MathHelper.floor_double(this.prevMapPosX
				+ (this.mapPosX - this.prevMapPosX) * (double) rpt);
		int currentMapPosY = MathHelper.floor_double(this.prevMapPosY
				+ (this.mapPosY - this.prevMapPosY) * (double) rpt);
		
		if (currentMapPosX < innerBoxLeft) {
			currentMapPosX = innerBoxLeft;
		}
		
		if (currentMapPosY < innerBoxTop) {
			currentMapPosY = innerBoxTop;
		}
		
		if (currentMapPosX >= innerBoxRight) {
			currentMapPosX = innerBoxRight - 1;
		}
		
		if (currentMapPosY >= innerBoxBottom) {
			currentMapPosY = innerBoxBottom - 1;
		}
		
		int guiLeft = (this.width - this.xSize) / 2;
		int guiTop = (this.height - this.ySize) / 2;
		int mapX = guiLeft + 16;
		int mapY = guiTop + 17;
		
		this.zLevel = 0.0F;
		GL11.glDepthFunc(GL11.GL_GEQUAL);
		GL11.glPushMatrix();
		GL11.glTranslatef((float) mapX, (float) mapY, -200.0F);
		GL11.glScalef(1.0F / this.some_float_value, 1.0F / this.some_float_value, 0.0F);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		
		// draw background
		
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		this.mc.getTextureManager().bindTexture(frameBackground);
		int l4;
		
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		int i5;
		int j5;
		
		for (l4 = 0; l4 < QuomRegistry.quomRegistry.size(); ++l4) {
			Quom quom = QuomRegistry.quomRegistry.get(l4);
			i5 = quom.displayColumn * 24 - currentMapPosX;
			j5 = quom.displayRow * 24 - currentMapPosY;
			
			if (i5 >= -24 && j5 >= -24 && (float) i5 <= 224.0F && (float) j5 <= 155.0F) {
				this.mc.getTextureManager().bindTexture(frameBackground);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				this.drawTexturedModalRect(i5 - 2, j5 - 2, 0, 202, 26, 26);
				
				//quom.draw(this.mc, this, i5 - 2 + 5, j5 - 2 + 5);
			}
		}
		
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glPopMatrix();
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		
		// frame
		this.mc.getTextureManager().bindTexture(frameBackground);
		this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, this.xSize, this.ySize);
		this.zLevel = 0.0F;
		
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		GL11.glEnable(GL11.GL_LIGHTING);
	}
	
}
