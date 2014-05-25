package com.countrygamer.arcanacraft.common.quom;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import com.countrygamer.arcanacraft.common.ArcanaCraft;
import com.countrygamer.arcanacraft.common.extended.ExtendedArcanePlayer;
import com.countrygamer.countrygamercore.lib.UtilCursor;

public abstract class Quom {
	
	private String				name;
	private String				parentName;
	private int					id;
	private ResourceLocation	iconSource;
	
	public Quom(String name) {
		this(name, null);
	}
	
	public Quom(String name, Quom parent) {
		this.name = name;
		this.parentName = parent == null ? "" : parent.getName();
		this.iconSource = new ResourceLocation(ArcanaCraft.pluginID, "textures/quoms/" + this.name
				+ ".png");
		
		this.id = QuomRegistry.quomRegistry.size();
		QuomRegistry.registerQuom(this);
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getID() {
		return this.id;
	}
	
	public void draw(Minecraft mc, Gui g, int x, int y) {
		mc.getTextureManager().bindTexture(this.iconSource);
		g.drawTexturedModalRect(x, y, 0, 0, 16, 16);
	}
	
	public void saveToNBT(NBTTagCompound tagCom) {
		tagCom.setString("name", this.name);
		tagCom.setString("parentName", this.parentName);
		tagCom.setInteger("id", this.id);
		tagCom.setString("iconSource_dir", this.iconSource.getResourceDomain());
		tagCom.setString("iconSource_path", this.iconSource.getResourcePath());
		
	}
	
	public void loadFromNBT(NBTTagCompound tagCom) {
		this.name = tagCom.getString("name");
		this.parentName = tagCom.getString("parentName");
		this.id = tagCom.getInteger("id");
		this.iconSource = new ResourceLocation(tagCom.getString("iconSource_dir"),
				tagCom.getString("iconSource_path"));
		
	}
	
	public Quom copy() {
		// NBTTagCompound tagCom = new NBTTagCompound();
		// this.saveToNBT(tagCom);
		// return Quom.getFromNBT(tagCom);
		return this;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Quom) {
			Quom that = (Quom) obj;
			return this.name.equals(that.name) && this.id == that.id
					&& this.parentName.equals(that.parentName);
		}
		return false;
	}
	
	public Quom getParent() {
		return QuomRegistry.getQuom(this.parentName);
	}
	
	public double getReachLength(Tiers.Cast castTier) {
		return 5.0D;
	}
	
	public void onUse_do(EntityPlayer player, ExtendedArcanePlayer arcanePlayer, World world,
			Tiers.Cast castTier, Tiers.MANUS manusTier) {
		// ArcanaCraft.logger.info("Quom");
		
		// TODO Decrement Manus
		
		boolean usedOnEntity = false;
		MovingObjectPosition mop = UtilCursor.getMOPFromPlayer(world, player,
				this.getReachLength(castTier));
		if (mop != null) {
			if (mop.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
				// ArcanaCraft.logger.info("Found Entity");
				if (mop.entityHit instanceof EntityLivingBase) {
					usedOnEntity = this.onEntityUse(player, arcanePlayer, world,
							(EntityLivingBase) mop.entityHit, castTier);
				}
			}
			else {
				// ArcanaCraft.logger.info("Not An Entity");
			}
		}
		else {
			// ArcanaCraft.logger.info("Null MOP");
		}
		
		if (!usedOnEntity) {
			UtilCursor.MovingObjectPositionTarget mopT = UtilCursor.getBlockFromCursor(world,
					arcanePlayer.player, this.getReachLength(castTier));
			if (mopT != null)
				this.onUse(player, arcanePlayer, world, mopT.x, mopT.y, mopT.z, mopT.side, castTier);
		}
	}
	
	private boolean onEntityUse(EntityPlayer player, ExtendedArcanePlayer arcanePlayer,
			World world, EntityLivingBase entity, Tiers.Cast castTier) {
		return false;
	}
	
	public abstract void onUse(EntityPlayer player, ExtendedArcanePlayer arcanePlayer, World world,
			int x, int y, int z, int side, Tiers.Cast castTier);
	
	public void checkForDiscovery(ExtendedArcanePlayer arcanePlayer,
			PlayerInteractEvent.Action action, ItemStack itemStack) {
		if (this.canDiscover(arcanePlayer, action, itemStack)) {
			ArcanaCraft.logger.info("Can discover " + this.getName());
			arcanePlayer.discoverQuom(this);
			return;
		}
		ArcanaCraft.logger.info("Cannot discover " + this.getName());
	}
	
	public boolean canDiscover(ExtendedArcanePlayer arcanePlayer,
			PlayerInteractEvent.Action action, ItemStack itemStack) {
		boolean hasLearnedParent = this.parentName == null
				|| arcanePlayer.hasLearnedQuom(this.getParent());
		return hasLearnedParent && !arcanePlayer.hasDiscoveredQuom(this);
	}
}
