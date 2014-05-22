package com.countrygamer.arcanacraft.common;

import java.util.ArrayList;

import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class KeyHandler {
	
	private static final ArrayList<String> descriptions = new ArrayList<String>();
	
	private static final int[] keyValues = {
		Keyboard.KEY_P
	};
	
	private final KeyBinding[] keys;
	
	public KeyHandler() {
		descriptions.add("key.cast.desc");
		
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
		KeyBinding key;
		
		key = this.getKey("key.cast.desc");
		if (key.isPressed()) {
			//System.out.println("Press Key");
			PacketCastQuom packet = new PacketCastQuom();
			ArcanaCraft.instance.packetChannel.sendToServer(packet);
		}
	}
	
	public KeyBinding getKey(String desc) {
		return this.keys[descriptions.indexOf(desc)];
	}
	
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		/*
		if (event.phase == TickEvent.Phase.START) {
			if (this.keys[0].isPressed()) {
				EntityPlayer player = event.player;
				if (player != null) {
					
				}
			}
		}
		*/
	}
	
}
