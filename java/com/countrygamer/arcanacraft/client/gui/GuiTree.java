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
	
	private static final int field_146572_y = QuomRegistry.minDisplayColumn * 24 - 112;
	private static final int field_146571_z = QuomRegistry.minDisplayRow * 24 - 112;
	private static final int field_146559_A = QuomRegistry.maxDisplayColumn * 24 - 77;
	private static final int field_146560_B = QuomRegistry.maxDisplayRow * 24 - 77;
	private static final ResourceLocation field_146561_C = new ResourceLocation(
			"textures/gui/achievement/achievement_background.png");
	
	protected int field_146555_f = 256;
	protected int field_146557_g = 202;
	protected int field_146563_h;
	protected int field_146564_i;
	protected float field_146570_r = 1.0F;
	protected double field_146569_s;
	protected double field_146568_t;
	protected double field_146567_u;
	protected double field_146566_v;
	protected double field_146565_w;
	protected double field_146573_x;
	private int field_146554_D;
	
	public GuiTree() {
		short short1 = 141;
		short short2 = 141;
		Quom quom = QuomRegistry.quomRegistry.get(0);
		this.field_146569_s = this.field_146567_u = this.field_146565_w = (double) (quom.displayColumn
				* 24 - short1 / 2 - 12);
		this.field_146568_t = this.field_146566_v = this.field_146573_x = (double) (quom.displayRow * 24 - short2 / 2);
		
	}
	
	@Override
	public void drawScreen(int par1, int par2, float par3) {
		int k;
		
		if (Mouse.isButtonDown(0)) {
			k = (this.width - this.field_146555_f) / 2;
			int l = (this.height - this.field_146557_g) / 2;
			int i1 = k + 8;
			int j1 = l + 17;
			
			if ((this.field_146554_D == 0 || this.field_146554_D == 1) && par1 >= i1
					&& par1 < i1 + 224 && par2 >= j1 && par2 < j1 + 155) {
				if (this.field_146554_D == 0) {
					this.field_146554_D = 1;
				}
				else {
					this.field_146567_u -= (double) ((float) (par1 - this.field_146563_h) * this.field_146570_r);
					this.field_146566_v -= (double) ((float) (par2 - this.field_146564_i) * this.field_146570_r);
					this.field_146565_w = this.field_146569_s = this.field_146567_u;
					this.field_146573_x = this.field_146568_t = this.field_146566_v;
				}
				
				this.field_146563_h = par1;
				this.field_146564_i = par2;
			}
		}
		else {
			this.field_146554_D = 0;
		}
		
		k = Mouse.getDWheel();
		float f4 = this.field_146570_r;
		
		if (k < 0) {
			this.field_146570_r += 0.25F;
		}
		else if (k > 0) {
			this.field_146570_r -= 0.25F;
		}
		
		this.field_146570_r = MathHelper.clamp_float(this.field_146570_r, 1.0F, 2.0F);
		
		if (this.field_146570_r != f4) {
			float f5 = f4 * (float) this.field_146555_f;
			float f1 = f4 * (float) this.field_146557_g;
			float f2 = this.field_146570_r * (float) this.field_146555_f;
			float f3 = this.field_146570_r * (float) this.field_146557_g;
			this.field_146567_u -= (double) ((f2 - f5) * 0.5F);
			this.field_146566_v -= (double) ((f3 - f1) * 0.5F);
			this.field_146565_w = this.field_146569_s = this.field_146567_u;
			this.field_146573_x = this.field_146568_t = this.field_146566_v;
		}
		
		if (this.field_146565_w < (double) field_146572_y) {
			this.field_146565_w = (double) field_146572_y;
		}
		
		if (this.field_146573_x < (double) field_146571_z) {
			this.field_146573_x = (double) field_146571_z;
		}
		
		if (this.field_146565_w >= (double) field_146559_A) {
			this.field_146565_w = (double) (field_146559_A - 1);
		}
		
		if (this.field_146573_x >= (double) field_146560_B) {
			this.field_146573_x = (double) (field_146560_B - 1);
		}
		
		this.func_146552_b(par1, par2, par3);
	}
	
	@Override
	public void updateScreen() {
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
	
	protected void func_146552_b(int p_146552_1_, int p_146552_2_, float p_146552_3_) {
		int k = MathHelper.floor_double(this.field_146569_s
				+ (this.field_146567_u - this.field_146569_s) * (double) p_146552_3_);
		int l = MathHelper.floor_double(this.field_146568_t
				+ (this.field_146566_v - this.field_146568_t) * (double) p_146552_3_);
		
		if (k < field_146572_y) {
			k = field_146572_y;
		}
		
		if (l < field_146571_z) {
			l = field_146571_z;
		}
		
		if (k >= field_146559_A) {
			k = field_146559_A - 1;
		}
		
		if (l >= field_146560_B) {
			l = field_146560_B - 1;
		}
		
		int i1 = (this.width - this.field_146555_f) / 2;
		int j1 = (this.height - this.field_146557_g) / 2;
		int k1 = i1 + 16;
		int l1 = j1 + 17;
		
		this.zLevel = 0.0F;
		GL11.glDepthFunc(GL11.GL_GEQUAL);
		GL11.glPushMatrix();
		GL11.glTranslatef((float) k1, (float) l1, -200.0F);
		GL11.glScalef(1.0F / this.field_146570_r, 1.0F / this.field_146570_r, 0.0F);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		
		// draw background
		
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		this.mc.getTextureManager().bindTexture(field_146561_C);
		int l4;
		
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		int i5;
		int j5;
		
		for (l4 = 0; l4 < QuomRegistry.quomRegistry.size(); ++l4) {
			Quom quom = QuomRegistry.quomRegistry.get(l4);
			i5 = quom.displayColumn * 24 - k;
			j5 = quom.displayRow * 24 - l;
			
			if (i5 >= -24 && j5 >= -24 && (float) i5 <= 224.0F && (float) j5 <= 155.0F) {
				this.mc.getTextureManager().bindTexture(field_146561_C);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				this.drawTexturedModalRect(i5 - 2, j5 - 2, 0, 202, 26, 26);
				
				quom.draw(this.mc, this, i5 - 2 + 5, j5 - 2 + 5);
			}
		}
		
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glPopMatrix();
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		
		// frame
		this.mc.getTextureManager().bindTexture(field_146561_C);
		this.drawTexturedModalRect(i1, j1, 0, 0, this.field_146555_f, this.field_146557_g);
		this.zLevel = 0.0F;
		
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		GL11.glEnable(GL11.GL_LIGHTING);
	}
	
}
