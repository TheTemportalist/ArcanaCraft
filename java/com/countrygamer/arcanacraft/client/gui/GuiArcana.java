package com.countrygamer.arcanacraft.client.gui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.IExtendedEntityProperties;

import com.countrygamer.arcanacraft.common.ArcanaCraft;
import com.countrygamer.arcanacraft.common.extended.ExtendedArcanePlayer;
import com.countrygamer.countrygamercore.Base.Plugin.extended.ExtendedEntity;
import com.countrygamer.countrygamercore.Base.client.gui.GuiScreenBase;

public class GuiArcana extends GuiScreenBase {
	
	private String currPage;
	private Map<String, ComponentPage> pages = new HashMap<String, ComponentPage>();
	private GuiButton infoTab, quomiTab, skillTab;
	
	private ExtendedArcanePlayer arcanePlayer;
	
	public GuiArcana(EntityPlayer player, boolean revertPages) {
		super("", new ResourceLocation(ArcanaCraft.pluginID, "textures/gui/Scroll.png"));
		this.xSize = 200;
		this.ySize = 200;
		IExtendedEntityProperties props = ExtendedEntity.getExtended(player,
				ExtendedArcanePlayer.class);
		if (props != null) this.arcanePlayer = (ExtendedArcanePlayer) props;
		if (revertPages) {
			this.currPage = "Info";
		}
		else {
			this.currPage = this.arcanePlayer.getCurrentArcanaPage();
		}
		
		//this.arcanePlayer.printQuoms();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		super.initGui();
		this.createPages();
		this.initPages();
		
		int bID = -1;
		this.buttonList.add(this.infoTab = new GuiButton(bID += 1, this.guiLeft + this.xSize - 10,
				this.guiTop + 5, 20, 20, ""));
		this.buttonList.add(this.quomiTab = new GuiButton(bID += 1, this.guiLeft + this.xSize - 10,
				this.guiTop + 35, 20, 20, ""));
		this.buttonList.add(this.skillTab = new GuiButton(bID += 1, this.guiLeft + this.xSize - 10,
				this.guiTop + 65, 20, 20, ""));
		
		ComponentPageQuomi page = (ComponentPageQuomi) this.pages.get("Quomi");
		page.selected = arcanePlayer.getCurrentSelectedQuomIndex();
		
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
						- (mc.fontRenderer.getStringWidth(title) / 2), this.guiTop + 5,
						this.grayColor);
				
			}
			
			@Override
			public void drawBackground() {
				
			}
			
		});
		
		this.pages.put("Quomi", new ComponentPageQuomi(a, b, c, d, e, this.arcanePlayer));
		
		this.pages.put("Skill Tree", new ComponentPage(a, b, c, d, e) {
			
			@Override
			public void init() {
				
			}
			
			@Override
			public void drawForeground() {
				String title = "Quom Skill Tree";
				mc.fontRenderer.drawString(title, this.guiLeft + (this.xSize / 2)
						- (mc.fontRenderer.getStringWidth(title) / 2), this.guiTop + 5,
						this.grayColor);
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
	
	protected void foregroundText() {
		if (this.pages.containsKey(this.currPage)) {
			this.pages.get(this.currPage).drawForeground();
		}
	}
	
	protected void backgroundObjects() {
		if (this.pages.containsKey(this.currPage)) {
			this.pages.get(this.currPage).drawBackground();
		}
	}
	
	public void addHoverInfomation(int mouseX, int mouseY, List<String> hoverInfo) {
		if (this.currPage == "Quomi") {
			ComponentPageQuomi page = (ComponentPageQuomi) this.pages.get("Quomi");
			
			for (int i = 0; i < page.quomSlots.length; i++) {
				QuomSlot slot = page.quomSlots[i];
				if (slot != null && this.isMouseOver(slot.x, slot.y, 18, 18, mouseX, mouseY)) {
					if (slot.quom != null) {
						hoverInfo.add(slot.quom.getName());
						slot.quom.addHoverInfo(this.arcanePlayer, hoverInfo);
					}
				}
			}
			
		}
	}
	
	
}
