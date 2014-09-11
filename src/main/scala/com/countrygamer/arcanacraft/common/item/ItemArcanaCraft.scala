package com.countrygamer.arcanacraft.common.item

import com.countrygamer.arcanacraft.common.ArcanaCraft
import com.countrygamer.cgo.common.Origin
import com.countrygamer.cgo.wrapper.common.item.ItemWrapper

/**
 *
 *
 * @author CountryGamer
 */
class ItemArcanaCraft(name: String) extends ItemWrapper(ArcanaCraft.pluginID, name) {

	Origin.addItemToTab(this)

}
