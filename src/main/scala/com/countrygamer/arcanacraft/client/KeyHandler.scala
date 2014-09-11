package com.countrygamer.arcanacraft.client

import java.util

import com.countrygamer.arcanacraft.common.ArcanaCraft
import com.countrygamer.arcanacraft.common.network.PacketKeyPress
import com.countrygamer.cgo.common.network.PacketHandler
import cpw.mods.fml.client.registry.ClientRegistry
import cpw.mods.fml.common.eventhandler.SubscribeEvent
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.client.Minecraft
import net.minecraft.client.settings.KeyBinding
import org.lwjgl.input.Keyboard

/**
 *
 *
 * @author CountryGamer
 */
@SideOnly(Side.CLIENT)
object KeyHandler {

	private final val keyValues: util.HashMap[String, Int] = new util.HashMap[String, Int]()

	private var keys: util.HashMap[String, KeyBinding] = null

	{
		keyValues.put("key.cast.desc", Keyboard.KEY_P)
		keyValues.put("key.lastQuom.desc", Keyboard.KEY_COMMA)
		keyValues.put("key.nextQuom.desc", Keyboard.KEY_PERIOD)

		this.keys = new util.HashMap[String, KeyBinding]()
		val iterator: util.Iterator[String] = this.keyValues.keySet().iterator()
		while (iterator.hasNext) {
			val desc: String = iterator.next()
			val key: Int = this.keyValues.get(desc)
			this.keys.put(desc, new KeyBinding(desc, key, "keys.arcanacraft.category"))
			ClientRegistry.registerKeyBinding(this.keys.get(desc))
		}

	}

	@SubscribeEvent
	def onKeyInput(event: KeyInputEvent): Unit = {
		if (!Minecraft.getMinecraft.inGameHasFocus) return

		val cast: KeyBinding = this.getKey("key.cast.desc")
		val lastQuom: KeyBinding = this.getKey("key.lastQuom.desc")
		val nextQuom: KeyBinding = this.getKey("key.nextQuom.desc")

		var packet: PacketKeyPress = null
		if (cast != null && cast.isPressed) {
			packet = new PacketKeyPress(0)
		}
		else if (lastQuom != null && lastQuom.isPressed) {
			packet = new PacketKeyPress(1)
		}
		else if (nextQuom != null && nextQuom.isPressed) {
			packet = new PacketKeyPress(2)
		}

		if (packet != null) {
			PacketHandler.sendToServer(ArcanaCraft.pluginID, packet)
			// TODO Does this work? (sending to client)
			PacketHandler
					.sendToPlayer(ArcanaCraft.pluginID, packet, Minecraft.getMinecraft.thePlayer)
		}
	}

	def getKey(desc: String): KeyBinding = {
		if (this.keys.containsKey(desc)) {
			return this.keys.get(desc)
		}
		null
	}

}
