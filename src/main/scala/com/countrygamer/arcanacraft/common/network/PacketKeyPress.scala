package com.countrygamer.arcanacraft.common.network

import com.countrygamer.arcanacraft.common.extended.ArcanePlayer
import com.countrygamer.arcanacraft.common.quom.Quom
import com.countrygamer.cgo.wrapper.common.extended.ExtendedEntityHandler
import com.countrygamer.cgo.wrapper.common.network.AbstractPacket
import io.netty.buffer.ByteBuf
import net.minecraft.entity.player.EntityPlayer

/**
 *
 *
 * @author CountryGamer
 */
class PacketKeyPress(private var packetType: Int) extends AbstractPacket {

	def this() {
		this(-1)

	}

	override def writeTo(buffer: ByteBuf): Unit = {
		buffer.writeInt(this.packetType)

	}

	override def readFrom(buffer: ByteBuf): Unit = {
		this.packetType = buffer.readInt()

	}

	override def handleOnClient(player: EntityPlayer): Unit = {
		this.handle(player)
	}

	override def handleOnServer(player: EntityPlayer): Unit = {
		this.handle(player)
	}

	def handle(player: EntityPlayer): Unit = {
		val arcanePlayer: ArcanePlayer = ExtendedEntityHandler
				.getExtended(player, classOf[ArcanePlayer]).asInstanceOf[ArcanePlayer]
		this.packetType match {
			case 0 => // cast
				val quom: Quom = arcanePlayer.getCurrentQuom()
				if (quom != null) {
					quom.onUse_do(player, arcanePlayer, player.worldObj,
						arcanePlayer.getCastingTier(player.getHeldItem, quom))
				}
			case 1 => // last quom
				arcanePlayer.changeCurrentQuom(-1)
			case 2 => // next quom
				arcanePlayer.changeCurrentQuom(1)
			case _ => // nothing
		}
	}

}
