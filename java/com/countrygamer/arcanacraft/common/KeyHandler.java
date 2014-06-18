package com.countrygamer.arcanacraft.common;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;

import com.countrygamer.arcanacraft.common.network.MessageCastQuom;
import com.countrygamer.arcanacraft.common.network.MessageSelectQuom;
import com.countrygamer.countrygamercore.Base.common.network.PacketHandler;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class KeyHandler {
	
	private static final ArrayList<String> descriptions = new ArrayList<String>();
	
	private static final int[] keyValues = {
			Keyboard.KEY_P, Keyboard.KEY_COMMA, Keyboard.KEY_PERIOD
	};
	
	private final KeyBinding[] keys;
	
	public KeyHandler() {
		descriptions.add("key.cast.desc");
		descriptions.add("key.lastQuom.desc");
		descriptions.add("key.nextQuom.desc");
		
		this.keys = new KeyBinding[descriptions.size()];
		
		for (int i = 0; i < descriptions.size(); i++) {
			this.keys[i] = new KeyBinding(descriptions.get(i), keyValues[i],
					"keys.arcanacraft.category");
			ClientRegistry.registerKeyBinding(this.keys[i]);
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onKeyInput(KeyInputEvent event) {
		if (!Minecraft.getMinecraft().inGameHasFocus) return;
		
		KeyBinding cast = this.getKey("key.cast.desc");
		KeyBinding lastQuom = this.getKey("key.lastQuom.desc");
		KeyBinding nextQuom = this.getKey("key.nextQuom.desc");
		
		if (cast.isPressed()) {
			// System.out.println("Press Key");
			// PacketCastQuom packet = new PacketCastQuom();
			// ArcanaCraft.instance.packetChannel.sendToServer(packet);
			PacketHandler.sendToAll(ArcanaCraft.pluginID, new MessageCastQuom());
			PacketHandler.sendToServer(ArcanaCraft.pluginID, new MessageCastQuom());
		}
		
		if (lastQuom.isPressed()) {
			// PacketSelectQuom packet = new PacketSelectQuom(false);
			// ArcanaCraft.instance.packetChannel.sendToServer(packet);
			PacketHandler.sendToServer(ArcanaCraft.pluginID, new MessageSelectQuom(false));
		}
		
		if (nextQuom.isPressed()) {
			// PacketSelectQuom packet = new PacketSelectQuom(true);
			// ArcanaCraft.instance.packetChannel.sendToServer(packet);
			PacketHandler.sendToServer(ArcanaCraft.pluginID, new MessageSelectQuom(true));
		}
		
	}
	
	public KeyBinding getKey(String desc) {
		return this.keys[descriptions.indexOf(desc)];
	}
	
}
