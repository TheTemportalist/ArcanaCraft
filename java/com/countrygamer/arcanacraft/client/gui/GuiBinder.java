package com.countrygamer.arcanacraft.client.gui;

import java.util.List;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import com.countrygamer.arcanacraft.commom.network.MessageNewBindingQuom;
import com.countrygamer.arcanacraft.common.ArcanaCraft;
import com.countrygamer.arcanacraft.common.extended.ExtendedArcanePlayer;
import com.countrygamer.arcanacraft.common.quom.Quom;
import com.countrygamer.arcanacraft.common.tile.EnumBinderType;
import com.countrygamer.arcanacraft.common.tile.TileEntityAugmentedTank;
import com.countrygamer.arcanacraft.common.tile.TileEntityBinder;
import com.countrygamer.core.Base.Plugin.extended.ExtendedEntity;
import com.countrygamer.core.Base.common.inventory.ContainerBase;
import com.countrygamer.core.Base.common.network.PacketHandler;

public class GuiBinder extends GuiManusPowered {
	
	private static final ResourceLocation guiIcons = new ResourceLocation(ArcanaCraft.pluginID,
			"textures/gui/GuiIcons.png");
	
	ExtendedArcanePlayer arcanePlayer;
	
	QuomSlot bindingQuomSlot;
	QuomSlot[] slots;
	final EnumBinderType binderType;
	
	public GuiBinder(ContainerBase container) {
		super(container, 7, 17);
		
		TileEntityBinder tileEnt = (TileEntityBinder) this.getTileEntity();
		
		String name = tileEnt.getInventoryName();
		this.setupGui(name, new ResourceLocation(ArcanaCraft.pluginID, "textures/gui/Binder.png"));
		
		this.binderType = tileEnt.binderType;
		
		this.arcanePlayer = (ExtendedArcanePlayer) ExtendedEntity.getExtended(this.getPlayer(),
				ExtendedArcanePlayer.class);
		
	}
	
	@Override
	public void initGui() {
		super.initGui();
		
		TileEntityBinder tileEnt = (TileEntityBinder) this.getTileEntity();
		
		if (this.binderType == EnumBinderType.QUOM) {
			this.bindingQuomSlot = new QuomSlot(this.guiLeft + 71, this.guiTop + 18);
			Quom bindingQuom = tileEnt.getBindingQuom();
			if (bindingQuom != null) {
				this.bindingQuomSlot.setQuom(bindingQuom.copy());
			}
			
			int x = this.guiLeft + 72 + 18;
			int y = this.guiTop + 19;
			
			int cols = 4;
			int numberOfQuoms = this.arcanePlayer.getLearnedQuoms().length;
			this.slots = new QuomSlot[numberOfQuoms];
			int rows = numberOfQuoms / cols + (numberOfQuoms % cols > 0 ? 1 : 0);
			
			int i;
			for (i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					int id = (i * cols) + j;// + row1;
					if (id >= numberOfQuoms) continue;
					
					QuomSlot quomSlot = new QuomSlot(x + (j * 18), y + (i * 18));
					Quom quom = this.arcanePlayer.getLearnedQuoms()[id];
					if (quom != null) quomSlot.setQuom(quom.copy());
					this.slots[id] = quomSlot;
				}
			}
			
		}
		
	}
	
	@Override
	protected void mouseClicked(int x, int y, int mouseButton) {
		super.mouseClicked(x, y, mouseButton);
		
		if (this.binderType == EnumBinderType.QUOM) {
			Quom previousBindingQuom = this.bindingQuomSlot.getQuom();
			
			if (this.isMouseOver(this.bindingQuomSlot.x, this.bindingQuomSlot.y, 18, 18, x, y)) {
				if (this.bindingQuomSlot.getQuom() != null) {
					this.bindingQuomSlot.setQuom(null);
				}
			}
			else {
				for (int i = 0; i < this.slots.length; i++) {
					if (this.slots[i] == null) continue;
					QuomSlot slot = this.slots[i];
					if (this.isMouseOver(slot.x, slot.y, 18, 18, x, y)) {
						Quom quom = slot.getQuom();
						if (quom != null) {
							this.bindingQuomSlot.setQuom(quom.copy());
						}
						break;
					}
				}
			}
			
			boolean differentQuom = false;
			if (previousBindingQuom == null) {
				if (this.bindingQuomSlot.getQuom() != null) {
					differentQuom = true;
				}
			}
			else {
				if (this.bindingQuomSlot.getQuom() == null) {
					differentQuom = true;
				}
				else if (!previousBindingQuom.equals(this.bindingQuomSlot.getQuom())) {
					differentQuom = true;
				}
			}
			if (differentQuom) {
				TileEntity tileEnt = this.getTileEntity();
				PacketHandler.sendToServer(ArcanaCraft.pluginID,
						new MessageNewBindingQuom(tileEnt.xCoord, tileEnt.yCoord, tileEnt.zCoord,
								this.bindingQuomSlot.getQuom()));
			}
		}
		else if (this.binderType == EnumBinderType.FLUID) {
			// TODO
			/*
			if (this.isMouseOver(this.guiLeft + 63, this.guiTop + 14, 36, 68, x, y)) {
				// System.out.println("Sending message");
				PacketHandler.sendToServer(ArcanaCraft.pluginID, new MessageGuiAddFluid(
						this.tileEnt.xCoord, this.tileEnt.yCoord, this.tileEnt.zCoord));
			}
			 */
		}
		
	}
	
	/*
	@Override
	public void drawScreen(int par1, int par2, float par3) {
		this.drawGuiContainerBackgroundLayer(par3, par1, par2);
		this.drawGuiContainerForegroundLayer(par1, par2);
		
		super.drawScreen(par1, par2, par3);
		
		this.drawHoverInfo(par1, par2);
		
	}
	
	private void drawHoverInfo(int mouseX, int mouseY) {
		List<String> hoverInfo = new ArrayList<String>();
		
		this.addHoverInfomation(mouseX, mouseY, hoverInfo);
		
		if (!hoverInfo.isEmpty()) this.renderHoverTip(hoverInfo, mouseX, mouseY);
	}
	
	@SuppressWarnings({
			"rawtypes", "unchecked"
	})
	protected void renderHoverTip(List hoverInfo, int mouseX, int mouseY) {
		for (int k = 0; k < hoverInfo.size(); ++k) {
			hoverInfo.set(k, EnumChatFormatting.GRAY + (String) hoverInfo.get(k));
		}
		
		this.func_146283_a(hoverInfo, mouseX, mouseY);
		drawHoveringText(hoverInfo, mouseX, mouseY, this.fontRendererObj);
	}
	 */
	
	@Override
	public void addHoverInfomation(int mouseX, int mouseY, List<String> hoverInfo) {
		if (this.binderType == EnumBinderType.QUOM) {
			for (int i = 0; i < this.slots.length; i++) {
				QuomSlot slot = this.slots[i];
				if (slot != null && this.isMouseOver(slot.x, slot.y, 18, 18, mouseX, mouseY)) {
					if (slot.quom != null) {
						hoverInfo.add(slot.quom.getName());
					}
				}
			}
		}
	}
	
	@Override
	protected void backgroundObjects() {
		super.backgroundObjects();
		
		int x = this.guiLeft, y = this.guiTop, u = 0, v = 0, w = 0, h = 0;
		EnumBinderType type = ((TileEntityBinder) this.getTileEntity()).binderType;
		if (type == EnumBinderType.QUOM) {
			if (this.bindingQuomSlot != null) this.bindingQuomSlot.draw();
			if (this.slots != null) for (int i = 0; i < this.slots.length; i++)
				if (this.slots[i] != null) this.slots[i].draw();
		}
		else if (type == EnumBinderType.ITEM) {
			this.mc.getTextureManager().bindTexture(guiIcons);
			x += 71;
			y += 18;
			u = 0;
			v = 20;
			w = 18;
			h = 18;
			this.drawTexturedModalRect(x, y, u, v, w, h);
		}
		else if (type == EnumBinderType.FLUID) {
			this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
			
			TileEntity thisTileEntity = this.getTileEntity();
			TileEntity tileEntity = thisTileEntity.getWorldObj().getTileEntity(
					thisTileEntity.xCoord, thisTileEntity.yCoord - 1, thisTileEntity.zCoord);
			// this.tileEnt.getWorldObj().getTileEntity(this.tileEnt.xCoord, this.tileEnt.yCoord -
			// 1, this.tileEnt.zCoord);
			if (tileEntity != null && tileEntity instanceof TileEntityAugmentedTank) {
				FluidStack storedFluid = ((TileEntityAugmentedTank) tileEntity).getFluidStack();
				if (storedFluid != null) {
					IIcon renderIndex = storedFluid.getFluid().getStillIcon();
					
					x += 77;
					y += 79;
					float buckets = (float) storedFluid.amount / 1000.0F;
					
					int liquidIconHeight = (int) buckets * 6;
					int fullIcons = liquidIconHeight / 16;
					int leftOverIconHeight = liquidIconHeight - (fullIcons * 16);
					for (int icon = 1; icon <= fullIcons; icon++) {
						for (int xi = 0; xi < 5; xi++) {
							this.drawLiquidRect(x + (xi * 16), y - (icon * 16), renderIndex, 16, 16);
						}
					}
					for (int xi = 0; xi < 5; xi++) {
						this.drawLiquidRect(x + (xi * 16), y - (fullIcons * 16)
								- leftOverIconHeight, renderIndex, 16, leftOverIconHeight);
					}
					
					x = this.guiLeft;
					y = this.guiTop;
				}
			}
			this.mc.getTextureManager().bindTexture(guiIcons);
			x += 75;
			y += 17;
			this.drawTexturedModalRect(x, y, 36, 38, 84, 64);
		}
		
	}
}
