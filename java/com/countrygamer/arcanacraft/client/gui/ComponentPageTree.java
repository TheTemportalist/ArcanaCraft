package com.countrygamer.arcanacraft.client.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.countrygamer.arcanacraft.common.ArcanaCraft;
import com.countrygamer.arcanacraft.common.quom.QuomRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ComponentPageTree extends ComponentPage {
	
	final Minecraft mc = Minecraft.getMinecraft();
	final ResourceLocation background = new ResourceLocation(ArcanaCraft.pluginID,
			"textures/gui/SkillTree_Background.png");
	final Gui gui = new Gui();
	
	int boxX, boxY, boxW, boxH, iconSide = 16, cols = 15, rows = 15;
	boolean isMoving = false;
	int originalMousePosX = -1, originalMousePosY = -1;
	int boxOffsetX = 0, boxOffsetY = 0;
	int shownBoxX, shownBoxY;
	
	List<GuiButtonInquiry> inquires = new ArrayList<GuiButtonInquiry>();
	
	public ComponentPageTree(int guiLeft, int xSize, int guiTop, int ySize, int grayColor) {
		super(guiLeft, xSize, guiTop, ySize, grayColor);
	}
	
	@Override
	public void init() {
		this.boxX = this.guiLeft + 10;
		this.boxY = this.guiTop + 20;
		this.boxW = 426;
		this.boxH = 426;
		this.shownBoxX = 88;
		this.shownBoxY = 88;
		
		this.inquires.clear();
		int baseX = this.boxX + 10;
		int baseY = this.boxY + 10;
		int x = baseX - 30;
		int y = baseY;
		for (int i = 0; i < QuomRegistry.quomRegistry.size(); i++) {
			x += 30;
			if (x > this.boxX + this.boxW + 50) {
				y += 30;
				x = baseX - 30;
			}
			this.inquires.add(new GuiButtonInquiry(0, x, y, 1, QuomRegistry.quomRegistry.get(i)));
		}
		
	}
	
	@Override
	public void drawForeground() {
		String title = "Quom Skill Tree";
		this.mc.fontRenderer
				.drawString(
						title,
						this.guiLeft + (this.xSize / 2)
								- (this.mc.fontRenderer.getStringWidth(title) / 2),
						this.guiTop + 5, this.grayColor);
	}
	
	@Override
	public void drawBackground(int mouseX, int mouseY, float renderPartialTicks) {
		
		if (Mouse.isButtonDown(0)) {
			if (this.isMoving) {
				
				if (this.originalMousePosX >= 0) {
					this.boxOffsetX += this.originalMousePosX - mouseX;
					if (this.boxOffsetX < 0) this.boxOffsetX = 0;
					if (this.boxOffsetX + this.shownBoxX > this.boxW) {
						this.boxOffsetX = this.boxW - this.shownBoxX;
					}
					this.originalMousePosX = mouseX;
				}
				if (this.originalMousePosY >= 0) {
					this.boxOffsetY += this.originalMousePosY - mouseY;
					if (this.boxOffsetY < 0) this.boxOffsetY = 0;
					if (this.boxOffsetY + this.shownBoxY > this.boxH) {
						this.boxOffsetY = this.boxH - this.shownBoxY;
					}
					this.originalMousePosY = mouseY;
				}
			}
		}
		else {
			this.isMoving = false;
			this.originalMousePosX = -1;
			this.originalMousePosY = -1;
		}
		
		GL11.glPushMatrix();
		GL11.glScalef(2.0F, 2.0F, 1.0F);
		
		int offsetX = (int) (this.boxOffsetX);
		int offsetY = (int) (this.boxOffsetY);
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		
		this.mc.getTextureManager().bindTexture(this.background);
		this.gui.drawTexturedModalRect(this.boxX / 2, this.boxY / 2, offsetX / 2, offsetY / 2,
				this.shownBoxX, this.shownBoxY);
		
		GL11.glScalef(0.5F, 0.5F, 1.0F);
		GL11.glPopMatrix();
		
		for (int i = 0; i < this.inquires.size(); ++i) {
			GuiButtonInquiry inqu = ((GuiButtonInquiry) this.inquires.get(i));
			
			boolean min = (inqu.xPosition + 26 > this.boxX + offsetX)
					&& (inqu.yPosition + 26 > this.boxY + offsetY);
			boolean max = (inqu.xPosition + 26 < this.boxX + offsetX + (this.shownBoxX * 2))
					&& (inqu.yPosition + 26 < this.boxY + offsetY + (this.shownBoxY * 2));
			if (min && max) {
				// System.out.println("Should draw");
				
				int uOffset = 0;
				int vOffset = 0;
				int wOffset = 0;
				int hOffset = 0;
				inqu.drawButtonWithOffsets(mc, mouseX, mouseY, -offsetX, -offsetY, uOffset,
						vOffset, wOffset, hOffset);
				
			}
			
		}
		
	}
	
	boolean isInBox(int mouseX, int mouseY) {
		return mouseX >= boxX && mouseX <= boxX + boxW && mouseY >= boxY && mouseY <= boxY + boxH;
	}
	
	@Override
	public void onMouseClick(int x, int y, int mouseButton) {
		//boolean buttonUsed = false;
		for (int i = 0; i < this.inquires.size(); i++) {
			
		}
		if (mouseButton == 0 && this.isInBox(x, y)) {
			this.isMoving = true;
			this.originalMousePosX = x;
			this.originalMousePosY = y;
		}
	}
	
}
