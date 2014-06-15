package com.countrygamer.arcanacraft.client.gui;

import java.util.List;

import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import com.countrygamer.arcanacraft.commom.network.MessageSaveSackName;
import com.countrygamer.arcanacraft.common.ArcanaCraft;
import com.countrygamer.arcanacraft.common.inventory.ContainerSack;
import com.countrygamer.arcanacraft.common.inventory.InventorySack;
import com.countrygamer.arcanacraft.common.item.ItemInventorySack;
import com.countrygamer.countrygamercore.Base.client.gui.GuiContainerBase;
import com.countrygamer.countrygamercore.Base.common.inventory.ContainerBase;
import com.countrygamer.countrygamercore.Base.common.item.ItemInvBase;
import com.countrygamer.countrygamercore.Base.common.network.PacketHandler;

public class GuiSack extends GuiContainerBase {
	
	private GuiTextField nameTextField;
	
	public GuiSack(EntityPlayer player, InventorySack inv) {
		super(169 + 7, 176 + 7, new ContainerSack(player, inv));
		
		NBTTagCompound tagCom = new NBTTagCompound();
		if (player.getHeldItem().hasTagCompound()) tagCom = player.getHeldItem().getTagCompound();
		String name = tagCom.getCompoundTag(ItemInvBase.basicDataKey).getString(
				ItemInventorySack.sackName);
		this.setupGui(name != "" ? name : ((ContainerBase) this.inventorySlots).getIInventory()
				.getInventoryName(), new ResourceLocation(ArcanaCraft.pluginID,
				"textures/gui/inventory.png"));
	}
	
	@Override
	public void initGui() {
		super.initGui();
		int w = 80;
		this.setupTextField(this.nameTextField = new GuiTextField(this.fontRendererObj,
				this.guiLeft + this.xSize - w - 8, this.guiTop + 4, w, 10), 13);
	}
	
	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		if (!this.nameTextField.getText().equals("")) {
			PacketHandler.sendToServer(ArcanaCraft.pluginID, new MessageSaveSackName(
					this.nameTextField.getText()));
		}
	}
	
	@Override
	protected void renderTitle() {
		this.drawTitle(7, 5);
	}
	
	@Override
	protected void foregroundText() {
	}
	
	@Override
	protected void backgroundObjects() {
	}
	
	@Override
	protected void addHoverInfomation(int mouseX, int mouseY, List<String> hoverInfo) {
	}
	
	@Override
	protected void buttonPress(int id) {
		
	}
	
}
