package com.countrygamer.arcanacraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import com.countrygamer.arcanacraft.common.ACOptions;
import com.countrygamer.arcanacraft.common.extended.ExtendedArcanePlayer;
import com.countrygamer.arcanacraft.common.quom.Quom;
import com.countrygamer.countrygamercore.Base.client.gui.GuiScreenBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ComponentPageQuomi extends ComponentPage {
	
	Minecraft mc = Minecraft.getMinecraft();
	Gui g = new Gui();
	ExtendedArcanePlayer arcanePlayer;
	final int number_of_slots;
	
	public ComponentPageQuomi(GuiScreenBase ownerGui, int a, int b, int c, int d, int e, ExtendedArcanePlayer arcanePlayer) {
		super(ownerGui, a, b, c, d, e);
		this.arcanePlayer = arcanePlayer;
		this.number_of_slots = 9 + 1 + arcanePlayer.getLearnedQuoms().length;
	}
	
	int selected = 0;
	QuomSlot[] quomSlots;
	
	@Override
	public void init() {
		this.quomSlots = new QuomSlot[this.number_of_slots];
		
		int x = this.guiLeft + 12;
		int y = this.guiTop + 10;
		
		int farRightSlotX = x + (8 * 18) + 3;
		
		y += 18;
		Quom[] hotBar = this.arcanePlayer.getHotBar();
		for (int i = 0; i < 9; i++) {
			this.quomSlots[i] = new QuomSlot(x + (i * 18), y, 0, 20, ACOptions.icons);
			this.quomSlots[i].setQuom(hotBar[i]);
		}
		
		int x1 = x;
		int y1 = y + 18 + 9;
		Quom[] quoms = arcanePlayer.getLearnedQuoms();
		for (int i = 0; i < quoms.length; i++) {
			if (x1 > farRightSlotX) {
				x1 = x;
				y1 += 18;
			}
			this.quomSlots[i + 10] = new QuomSlot(x1, y1, 0, 20, ACOptions.icons);
			this.quomSlots[i + 10].setQuom(quoms[i]);
			
			x1 += 18;
		}
		
		// y += 18 + 10;
		// Quom[] quoms = arcanePlayer.getQuoms();
		
		/*
		for (int i = 10; i < quoms.length; i++) {
			
			this.quomSlots[i] = new QuomSlot(x + (i * 18), y, 0, 20, ACOptions.icons);
			
			Quom quom = quoms[i];
			this.quomSlots[i].setQuom(quom);
		}
		 */
		
	}
	
	@Override
	public void drawForeground() {
		String title = "My Quomi";
		mc.fontRenderer.drawString(title,
				this.guiLeft + (this.xSize / 2) - (mc.fontRenderer.getStringWidth(title) / 2),
				this.guiTop + 5, this.grayColor);
	}
	
	@Override
	public void drawBackground(int mouseX, int mouseY, float renderPartialTicks) {
		int[] coords = this.quomSlots[0].getCoords();
		int x = coords[0];
		int y = coords[1];
		for (int i = 0; i < this.quomSlots.length; i++) {
			if (this.quomSlots[i] != null) this.quomSlots[i].draw();
		}
		g.drawTexturedModalRect(x + (this.selected * 18), y, 18, 20, 18, 18);
		
	}
	
	@Override
	public void onMouseClick(int x, int y, int mouseButton) {
		int[] coords = null;
		boolean hotbar_has_changed = false;
		
		int hotbarY = this.quomSlots[0].getCoords()[1];
		if (y >= hotbarY && y <= hotbarY + 18) {
			for (int i = 0; i < 9; i++) {
				coords = this.quomSlots[i].getCoords();
				if (x >= coords[0] && x <= coords[0] + 18) {
					if (mouseButton == 1) {
						this.quomSlots[i].setQuom(null);
						hotbar_has_changed = true;
					}
					else {
						this.selected = i;
						// System.out.println("Selected hotbar slot " + i);
						arcanePlayer.setCurrentHotBarIndex(this.selected);
					}
					break;
				}
			}
		}
		else {
			QuomSlot quomSlot = null;
			for (int i = 10; i < this.quomSlots.length; i++) {
				coords = this.quomSlots[i].getCoords();
				boolean validX = x >= coords[0] && x <= coords[0] + 18;
				boolean validY = y >= coords[1] && y <= coords[1] + 18;
				if (validX && validY) {
					//System.out.println("Found clicked slot");
					quomSlot = this.quomSlots[i];
					break;
				}
			}
			if (quomSlot != null) {
				if (quomSlot.getQuom() != null) {
					//System.out.println("Quom slot " + this.selected + "'s quom set to "
					//		+ quomSlot.getQuom().getName());
					this.quomSlots[this.selected].setQuom(quomSlot.getQuom());
					hotbar_has_changed = true;
				}
			}
		}
		
		if (hotbar_has_changed) {
			//System.out.println("Changed Hotbar");
			Quom[] hotBar = new Quom[9];
			for (int i = 0; i < 9; i++) {
				hotBar[i] = quomSlots[i].getQuom();
			}
			arcanePlayer.setHotBar(hotBar);
			// arcanePlayer.syncEntity();
		}
	}
	
}
