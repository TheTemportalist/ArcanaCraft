package com.countrygamer.arcanacraft.client.gui;

import java.util.List;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import com.countrygamer.arcanacraft.common.ArcanaCraft;
import com.countrygamer.arcanacraft.common.tile.TileEntityActuator;
import com.countrygamer.countrygamercore.Base.client.gui.GuiButtonRedstone;
import com.countrygamer.countrygamercore.Base.client.gui.GuiContainerBase;
import com.countrygamer.countrygamercore.Base.common.inventory.ContainerBase;
import com.countrygamer.countrygamercore.Base.common.network.PacketHandler;
import com.countrygamer.countrygamercore.common.Core;
import com.countrygamer.countrygamercore.common.network.MessageUpdateActivity;
import com.countrygamer.countrygamercore.common.network.MessageUpdateRedstoneState;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiActuator extends GuiContainerBase {
	
	final ResourceLocation background = new ResourceLocation(ArcanaCraft.pluginID,
			"textures/gui/Actuator.png");
	
	GuiButtonRedstone redstoneStateButton;
	GuiButtonActivity activityButton;
	
	public GuiActuator(ContainerBase container) {
		super(container);
		this.setupGui("Actuator", this.background);
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		super.initGui();
		
		TileEntityActuator actuatorTE = (TileEntityActuator) this.getTileEntity();
		
		this.buttonList.add(this.redstoneStateButton = new GuiButtonRedstone(0, this.guiLeft + 50,
				this.guiTop + 29, actuatorTE.getRedstoneState(), true));
		this.buttonList.add(this.activityButton = new GuiButtonActivity(1, this.guiLeft + 50,
				this.guiTop + 48, actuatorTE.getActivity()));
		
	}
	
	@Override
	protected void buttonPress(int id) {
		TileEntity te = this.getTileEntity();
		if (id == this.redstoneStateButton.id) {
			this.redstoneStateButton.nextRedstoneState();
			PacketHandler.sendToServer(Core.pluginID, new MessageUpdateRedstoneState(te.xCoord,
					te.yCoord, te.zCoord, this.redstoneStateButton.getRedstoneState()));
			PacketHandler.sendToAll(Core.pluginID, new MessageUpdateRedstoneState(te.xCoord,
					te.yCoord, te.zCoord, this.redstoneStateButton.getRedstoneState()));
		}
		else if (id == this.activityButton.id) {
			this.activityButton.nextState();
			PacketHandler.sendToServer(Core.pluginID, new MessageUpdateActivity(te.xCoord,
					te.yCoord, te.zCoord, this.activityButton.activity));
			PacketHandler.sendToAll(Core.pluginID, new MessageUpdateActivity(te.xCoord,
					te.yCoord, te.zCoord, this.activityButton.activity));
		}
		
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
	
}
