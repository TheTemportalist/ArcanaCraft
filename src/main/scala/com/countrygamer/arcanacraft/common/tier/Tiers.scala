package com.countrygamer.arcanacraft.common.tier

import java.util

/**
 *
 *
 * @author CountryGamer
 */
object Tiers {

	private val TIERS: util.List[Tier] = new util.ArrayList[Tier]()

	def addTier(tier: Tier): Unit = {
		this.TIERS.add(tier)
	}

	def addTier(index: Int, tier: Tier): Unit = {
		this.TIERS.add(index, tier)
	}

	def getTier(index: Int): Tier = {
		this.TIERS.get(index)
	}

}
