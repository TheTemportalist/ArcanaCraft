package com.countrygamer.arcanacraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.countrygamer.arcanacraft.common.ACOptions;
import com.countrygamer.arcanacraft.common.ArcanaCraft;
import com.countrygamer.arcanacraft.common.quom.Quom;
import com.countrygamer.arcanacraft.common.quom.QuomRegistry;

public class ComponentTree extends ComponentPage {
	
	Minecraft mc = Minecraft.getMinecraft();
	Gui gui = new Gui();
	final ResourceLocation background = new ResourceLocation(ArcanaCraft.pluginID,
			"textures/gui/SkillTree_Background.png");
	
	private static final int absolute_MinCol_Pixel = QuomRegistry.minDisplayColumn * 24 - 112;
	private static final int absolute_MinRow_Pixel = QuomRegistry.minDisplayRow * 24 - 112;
	private static final int absolute_MaxCol_Pixel = QuomRegistry.maxDisplayColumn * 24 - 77;
	private static final int absolute_MaxRow_Pixel = QuomRegistry.maxDisplayRow * 24 - 77;
	
	protected double field_146569_s;
	protected double field_146568_t;
	protected double field_146567_u;
	protected double field_146566_v;
	protected double field_146565_w;
	protected double field_146573_x;
	protected float field_146570_r = 1.0F;
	
	protected int mouseX, mouseY;
	
	public ComponentTree(int guiLeft, int xSize, int guiTop, int ySize, int grayColor) {
		super(guiLeft, xSize, guiTop, ySize, grayColor);
		
		// Unknown variables. What are these?
		short short1 = 141;
		short short2 = 141;
		
		// Very first quom
		Quom quom = QuomRegistry.quomRegistry.get(0);
		int displayColumn = quom.displayColumn;
		int displayRow = quom.displayRow;
		
		this.field_146569_s = this.field_146567_u = this.field_146565_w = (double) (displayColumn
				* 24 - short1 / 2 - 12);
		this.field_146568_t = this.field_146566_v = this.field_146573_x = (double) (displayRow * 24 - short2 / 2);
		
	}
	
	@Override
	public void init() {
		
	}
	
	@Override
	public void update() {
		this.field_146569_s = this.field_146567_u;
		this.field_146568_t = this.field_146566_v;
		double d0 = this.field_146565_w - this.field_146567_u;
		double d1 = this.field_146573_x - this.field_146566_v;
		
		if (d0 * d0 + d1 * d1 < 4.0D) {
			this.field_146567_u += d0;
			this.field_146566_v += d1;
		}
		else {
			this.field_146567_u += d0 * 0.85D;
			this.field_146566_v += d1 * 0.85D;
		}
	}
	
	@Override
	public void drawForeground() {
		
	}
	
	int field_146554_D;
	
	@Override
	public void drawBackground(int mouseX, int mouseY, float renderPartialTicks) {
		if (Mouse.isButtonDown(0)) {
			
			int i1 = this.guiLeft + 8;
			int j1 = this.guiTop + 17;
			
			if ((this.field_146554_D == 0 || this.field_146554_D == 1) && mouseX >= i1
					&& mouseX < i1 + 224 && mouseY >= j1 && mouseY < j1 + 155) {
				if (this.field_146554_D == 0) {
					this.field_146554_D = 1;
				}
				else {
					this.field_146567_u -= (double) ((float) (mouseX - this.mouseX) * this.field_146570_r);
					this.field_146566_v -= (double) ((float) (mouseY - this.mouseY) * this.field_146570_r);
					this.field_146565_w = this.field_146569_s = this.field_146567_u;
					this.field_146573_x = this.field_146568_t = this.field_146566_v;
				}
				
				this.mouseX = mouseX;
				this.mouseY = mouseY;
			}
			
		}
		else {
			this.field_146554_D = 0;
		}
		
		int k = Mouse.getDWheel();
		float saved_field_146570_r = this.field_146570_r;
		
		if (k < 0) {
			this.field_146570_r += 0.25F;
		}
		else if (k > 0) {
			this.field_146570_r -= 0.25F;
		}
		this.field_146570_r = MathHelper.clamp_float(this.field_146570_r, 1.0F, 2.0F);
		
		if (this.field_146570_r != saved_field_146570_r) {
			// float dif_field_146570_r = saved_field_146570_r - this.field_146570_r;
			float saved_XScale = saved_field_146570_r * (float) this.xSize;
			float saved_YScale = saved_field_146570_r * (float) this.ySize;
			float _XScale = this.field_146570_r * (float) this.xSize;
			float _YScale = this.field_146570_r * (float) this.ySize;
			this.field_146567_u -= (double) ((_XScale - saved_XScale) * 0.5F);
			this.field_146566_v -= (double) ((_YScale - saved_YScale) * 0.5F);
			this.field_146565_w = this.field_146569_s = this.field_146567_u;
			this.field_146573_x = this.field_146568_t = this.field_146566_v;
		}
		
		if (this.field_146565_w < (double) absolute_MinCol_Pixel) {
			this.field_146565_w = (double) absolute_MinCol_Pixel;
		}
		
		if (this.field_146573_x < (double) absolute_MinRow_Pixel) {
			this.field_146573_x = (double) absolute_MinRow_Pixel;
		}
		
		if (this.field_146565_w >= (double) absolute_MaxCol_Pixel) {
			this.field_146565_w = (double) (absolute_MaxCol_Pixel - 1);
		}
		
		if (this.field_146573_x >= (double) absolute_MaxRow_Pixel) {
			this.field_146573_x = (double) (absolute_MaxRow_Pixel - 1);
		}
		
		this.drawActualBackground(mouseX, mouseY, renderPartialTicks);
		
	}
	
	private void drawActualBackground(int mouseX, int mouseY, float renderPartialTicks) {
		int k = MathHelper.floor_double(this.field_146569_s
				+ (this.field_146567_u - this.field_146569_s) * (double) renderPartialTicks);
		int l = MathHelper.floor_double(this.field_146568_t
				+ (this.field_146566_v - this.field_146568_t) * (double) renderPartialTicks);
		
		if (k < absolute_MaxCol_Pixel) {
			k = absolute_MaxCol_Pixel;
		}
		
		if (l < absolute_MinRow_Pixel) {
			l = absolute_MinRow_Pixel;
		}
		
		if (k >= absolute_MaxCol_Pixel) {
			k = absolute_MaxCol_Pixel - 1;
		}
		
		if (l >= absolute_MaxRow_Pixel) {
			l = absolute_MaxRow_Pixel - 1;
		}
		
		int k1 = this.guiLeft + 16;
		int l1 = this.guiTop + 17;
		
		// stuff that we dont need
		
		/*
		GL11.glPushMatrix();
		this.mc.getTextureManager().bindTexture(this.background);
		this.gui.drawTexturedModalRect(k1, l1, 0, 0, 112, 98);
		
		GL11.glPopMatrix();
		*/
		
		GL11.glPushMatrix();
		int right = k1 + (this.absolute_MaxCol_Pixel - this.absolute_MinCol_Pixel);
		int bottom = l1 + (this.absolute_MaxRow_Pixel - this.absolute_MinRow_Pixel);
		int vx = (int) ((k - k1) / Math.abs(k1 - bottom) * 288.0F);
		int vy = (int) ((l - l1) / Math.abs(l1 - right) * 316.0F);
		GL11.glScalef(2.0F, 2.0F, 1.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().renderEngine.bindTexture(this.background);
		
		this.gui.drawTexturedModalRect(k1 / 2, l1 / 2, vx / 2, vy / 2, 82, 82);
		GL11.glScalef(0.5F, 0.5F, 1.0F);
		GL11.glPopMatrix();
		
		int index;
		int i5;
		int j5;
		for (index = 0; index < QuomRegistry.quomRegistry.size(); index++) {
			Quom quom = QuomRegistry.quomRegistry.get(index);
			
			i5 = quom.displayColumn * 24 - k;
			j5 = quom.displayRow * 24 - l;
			
			if (i5 >= -24 && j5 >= -24 && (float) i5 <= 224.0F * this.field_146570_r
					&& (float) j5 <= 155.0F * this.field_146570_r) {
				
				this.mc.getTextureManager().bindTexture(ACOptions.icons);
				
				this.gui.drawTexturedModalRect(k1 + i5 - 2, l1 + j5 - 2, 0, 128, 26, 26);
				
			}
			
		}
		
	}
	
}
