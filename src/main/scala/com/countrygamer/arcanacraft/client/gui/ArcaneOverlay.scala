package com.countrygamer.arcanacraft.client.gui

import com.countrygamer.arcanacraft.common.ArcanaCraft
import com.countrygamer.arcanacraft.common.extended.ArcanePlayer
import com.countrygamer.arcanacraft.common.quom.Quom
import com.countrygamer.cgo.wrapper.common.extended.ExtendedEntityHandler
import cpw.mods.fml.common.eventhandler.{EventPriority, SubscribeEvent}
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Gui
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType
import org.lwjgl.opengl.GL11

/**
 *
 *
 * @author CountryGamer
 */
@SideOnly(Side.CLIENT)
object ArcaneOverlay extends Gui {

	val mc: Minecraft = Minecraft.getMinecraft

	@SubscribeEvent(priority = EventPriority.NORMAL)
	def renderGameOverlay(event: RenderGameOverlayEvent.Post): Unit = {
		if (event.isCanceled) return

		val arcanePlayer: ArcanePlayer = ExtendedEntityHandler
				.getExtended(this.mc.thePlayer, classOf[ArcanePlayer]).asInstanceOf[ArcanePlayer]

		if (arcanePlayer.isArcaic() && arcanePlayer.isHoldingValidCaster()) {
			val width: Int = event.resolution.getScaledWidth
			val height: Int = event.resolution.getScaledHeight

			if (event.`type` == ElementType.HOTBAR) {
				GL11.glPushMatrix()
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F)
				GL11.glEnable(GL11.GL_BLEND)
				GL11.glDisable(GL11.GL_LIGHTING)

				val quomX: Int = width / 2 - 9
				val quomY: Int = height - 55
				this.mc.getTextureManager.bindTexture(
					new ResourceLocation(ArcanaCraft.pluginID,
						"textures/gui/GuiIcons.png"))
				this.drawTexturedModalRect(quomX, quomY, 0, 20, 18, 18)
				val quom: Quom = arcanePlayer.getCurrentQuom()
				if (quom != null) quom.draw(this, quomX + 1, quomY + 1)

				GL11.glDisable(GL11.GL_BLEND)
				GL11.glPopMatrix()

			}

		}

	}

}
