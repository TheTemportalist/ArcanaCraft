package com.countrygamer.arcanacraft.client.gui;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.IExtendedEntityProperties;

import org.lwjgl.opengl.GL11;

import com.countrygamer.arcanacraft.common.ArcanaCraft;
import com.countrygamer.arcanacraft.common.extended.ExtendedArcanePlayer;
import com.countrygamer.arcanacraft.common.quom.Quom;
import com.countrygamer.core.Base.Plugin.ExtendedEntity;

public class GuiArcana extends GuiScreen {
	
	protected int xSize = 200;
	protected int ySize = 200;
	private ResourceLocation bkgdTex = null;
	protected final int grayTextColor = 4210752;
	
	private String currPage;
	private Map<String, ComponentPage> pages = new HashMap<String, ComponentPage>();
	private GuiButton infoTab, quomiTab, skillTab;
	
	private ExtendedArcanePlayer arcanePlayer;
	
	public GuiArcana(EntityPlayer player, boolean revertPages) {
		IExtendedEntityProperties props = ExtendedEntity.getExtended(player,
				ExtendedArcanePlayer.class);
		if (props != null) this.arcanePlayer = (ExtendedArcanePlayer) props;
		this.bkgdTex = new ResourceLocation(ArcanaCraft.pluginID,
				"textures/gui/Scroll.png");
		if (revertPages) {
			this.currPage = "Info";
		}
		else {
			this.currPage = this.arcanePlayer.getCurrentArcanaPage();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		this.createPages();
		this.initPages();
		
		int left = (this.width - this.xSize) / 2;
		int top = (this.height - this.ySize) / 2;
		
		int bID = -1;
		this.buttonList.add(this.infoTab = new GuiButton(bID += 1, left + this.xSize
				- 10, top + 5, 20, 20, ""));
		this.buttonList.add(this.quomiTab = new GuiButton(bID += 1, left
				+ this.xSize - 10, top + 35, 20, 20, ""));
		this.buttonList.add(this.skillTab = new GuiButton(bID += 1, left
				+ this.xSize - 10, top + 65, 20, 20, ""));
		
		ComponentPageQuomi page = (ComponentPageQuomi) this.pages.get("Quomi");
		page.selected = arcanePlayer.getCurrentSelectedQuomIndex();
		Quom[] hotbar = arcanePlayer.getHotBar();
		for (int i = 0; i < 9; i++) {
			page.quomSlots[i].setQuom(hotbar[i]);
			System.out.println("Set hotbar slot " + i + " to quom ("
					+ (hotbar[i] == null ? "null" : hotbar[i].getName()) + ")");
		}
		
	}
	
	private void createPages() {
		int a = (this.width - this.xSize) / 2;
		int b = this.xSize;
		int c = (this.height - this.ySize) / 2;
		int d = this.ySize;
		int e = this.grayTextColor;
		
		this.pages.put("Info", new ComponentPage(a, b, c, d, e) {
			
			@Override
			public void init() {
				
			}
			
			@Override
			public void drawForeground() {
				String title = "ArcanaCraft; The Arcana";
				mc.fontRenderer.drawString(title, this.guiLeft + (this.xSize / 2)
						- (mc.fontRenderer.getStringWidth(title) / 2),
						this.guiTop + 5, this.grayColor);
				
			}
			
			@Override
			public void drawBackground() {
				
			}
			
		});
		
		this.pages.put("Quomi", new ComponentPageQuomi(a, b, c, d, e,
				this.arcanePlayer));
		
		this.pages.put("Skill Tree", new ComponentPage(a, b, c, d, e) {
			
			@Override
			public void init() {
				
			}
			
			@Override
			public void drawForeground() {
				String title = "Quom Skill Tree";
				mc.fontRenderer.drawString(title, this.guiLeft + (this.xSize / 2)
						- (mc.fontRenderer.getStringWidth(title) / 2),
						this.guiTop + 5, this.grayColor);
			}
			
			@Override
			public void drawBackground() {
				
			}
			
		});
		
	}
	
	private void initPages() {
		for (String pageKey : this.pages.keySet()) {
			this.pages.get(pageKey).init();
		}
	}
	
	@Override
	protected void actionPerformed(GuiButton gB) {
		if (gB.id == this.infoTab.id) {
			this.currPage = "Info";
		}
		else if (gB.id == this.quomiTab.id) {
			this.currPage = "Quomi";
		}
		else if (gB.id == this.skillTab.id) {
			this.currPage = "Skill Tree";
		}
	}
	
	@Override
	protected void mouseClicked(int x, int y, int mouseButton) {
		super.mouseClicked(x, y, mouseButton);
		if (this.pages.containsKey(this.currPage))
			this.pages.get(this.currPage).onMouseClick(x, y, mouseButton);
	}
	
	@Override
	public void drawScreen(int par1, int par2, float par3) {
		this.drawGuiContainerBackgroundLayer(par3, par1, par2);
		this.drawGuiContainerForegroundLayer(par1, par2);
		
		super.drawScreen(par1, par2, par3);
	}
	
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		if (this.pages.containsKey(this.currPage)) {
			this.pages.get(this.currPage).drawForeground();
		}
	}
	
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(this.bkgdTex);
		this.drawTexturedModalRect((this.width - this.xSize) / 2,
				(this.height - this.ySize) / 2, 0, 0, this.xSize, this.ySize);
		
		if (this.pages.containsKey(this.currPage)) {
			this.pages.get(this.currPage).drawBackground();
		}
		
	}
	
}
