package com.countrygamer.arcanacraft.common.extended;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

import com.countrygamer.arcanacraft.common.quom.Quom;
import com.countrygamer.arcanacraft.common.quom.QuomRegistry;
import com.countrygamer.core.Base.Plugin.ExtendedEntity;
import com.countrygamer.countrygamercore.lib.CoreUtil;

public class ExtendedArcanePlayer extends ExtendedEntity {
	
	public static final int maxManusTicks = 20 * 5;
	public static final int maxSmokeTicks = 20 * 6;
	
	private boolean isActive;
	
	private int manus, maxManus;
	private int manusTick;
	
	private String currentArcanaPage;
	
	private Quom[] quoms;
	private Quom[] hotBar;
	private int currentSelectedHotBarIndex;
	
	private EnumSmokeAction turningToSmoke;
	private int smokeTick;
	
	private double[] teleportationDestination;
	
	public ExtendedArcanePlayer(EntityPlayer player) {
		super(player);
		this.isActive = false;
		this.maxManus = 20;
		this.manus = 0;
		this.currentArcanaPage = "Info";
		this.turningToSmoke = EnumSmokeAction.NONE;
		
		this.quoms = new Quom[QuomRegistry.quomRegistry.size()];
		this.hotBar = new Quom[9];
		this.currentSelectedHotBarIndex = 0;
		
		for (Quom quom : QuomRegistry.quomRegistry) {
			this.learnQuom(quom);
		}
	}
	
	@Override
	public void init(Entity entity, World world) {
		this.syncEntity();
	}
	
	@Override
	public void saveNBTData(NBTTagCompound compound) {
		compound.setBoolean("isActive", this.isActive);
		compound.setInteger("staminaCounter", this.manusTick);
		compound.setInteger("maxStamina", this.maxManus);
		compound.setInteger("stamina", this.manus);
		compound.setString("currentPage", this.currentArcanaPage);
		compound.setInteger("currentHotBarIndex", this.currentSelectedHotBarIndex);
		
		compound.setInteger("quomLength", this.quoms.length);
		NBTTagList quomList = new NBTTagList();
		for (int i = 0; i < this.quoms.length; i++) {
			if (this.quoms[i] != null) {
				NBTTagCompound quomTag = new NBTTagCompound();
				quomTag.setInteger("slot", i);
				quomTag.setInteger("quomID", this.quoms[i].getID());
				quomList.appendTag(quomTag);
				//System.out
				//		.println("\nSlot: " + i + "\nID: " + this.quoms[i].getID());
			}
		}
		compound.setTag("quoms", quomList);
		
		NBTTagList hotbarList = new NBTTagList();
		for (int i = 0; i < 9; i++) {
			if (this.hotBar[i] != null) {
				NBTTagCompound quomTag = new NBTTagCompound();
				quomTag.setInteger("slot", i);
				quomTag.setInteger("quomID", this.hotBar[i].getID());
				hotbarList.appendTag(quomTag);
			}
		}
		compound.setTag("hotbar", hotbarList);
		
		compound.setInteger("turningToSmoke", this.turningToSmoke.getID());
		compound.setInteger("smokeTick", this.smokeTick);
		
		compound.setBoolean("hasDest", this.teleportationDestination != null);
		if (this.teleportationDestination != null) {
			compound.setDouble("teleportDestX", this.teleportationDestination[0]);
			compound.setDouble("teleportDestY", this.teleportationDestination[1]);
			compound.setDouble("teleportDestZ", this.teleportationDestination[2]);
		}
	}
	
	@Override
	public void loadNBTData(NBTTagCompound compound) {
		this.isActive = compound.getBoolean("isActive");
		this.manusTick = compound.getInteger("staminaCounter");
		this.maxManus = compound.getInteger("maxStamina");
		this.manus = compound.getInteger("stamina");
		this.currentArcanaPage = compound.getString("currentPage");
		this.currentSelectedHotBarIndex = compound.getInteger("currentHotBarIndex");
		
		this.quoms = new Quom[compound.getInteger("quomLength")];
		NBTTagList quomList = compound.getTagList("quoms", 10);
		for (int i = 0; i < quomList.tagCount(); i++) {
			NBTTagCompound quomTag = quomList.getCompoundTagAt(i);
			this.quoms[quomTag.getInteger("slot")] = QuomRegistry.quomRegistry
					.get(quomTag.getInteger("quomID"));
			//System.out.println("Loaded "
			//		+ this.quoms[quomTag.getInteger("slot")].getName());
		}
		
		this.hotBar = new Quom[9];
		NBTTagList hotbarList = compound.getTagList("hotbar", 10);
		for (int i = 0; i < hotbarList.tagCount(); i++) {
			NBTTagCompound quomTag = hotbarList.getCompoundTagAt(i);
			this.hotBar[quomTag.getInteger("slot")] = QuomRegistry.quomRegistry
					.get(quomTag.getInteger("quomID"));
		}
		
		this.turningToSmoke = EnumSmokeAction.getEnumFromID(compound
				.getInteger("turningToSmoke"));
		this.smokeTick = compound.getInteger("smokeTick");
		
		if (compound.getBoolean("hasDest")) {
			double x = compound.getDouble("teleportDestX");
			double y = compound.getDouble("teleportDestY");
			double z = compound.getDouble("teleportDestZ");
			this.teleportationDestination = new double[] {
					x, y, z
			};
		}
	}
	
	public void setActiveState(boolean value) {
		this.isActive = value;
		this.syncEntity();
	}
	
	public boolean isPlayerArcaic() {
		return this.isActive;
	}
	
	public void setManus(int value) {
		this.manus = value;
		this.syncEntity();
	}
	
	public void incrementManus(int value) {
		if (this.manus + value <= this.maxManus) this.setManus(this.manus + value);
	}
	
	public int getManus() {
		return this.manus;
	}
	
	public void setManusCounter(int i) {
		this.manusTick = i;
		this.syncEntity();
	}
	
	public int getManusCounter() {
		return this.manusTick;
	}
	
	public void setMaxManus(int value) {
		this.maxManus = value;
		this.syncEntity();
	}
	
	public int getMaxManus() {
		return this.maxManus;
	}
	
	public void setCurrentArcanaPage(String page) {
		this.currentArcanaPage = page;
		this.syncEntity();
	}
	
	public String getCurrentArcanaPage() {
		return this.currentArcanaPage;
	}
	
	public void setQuoms(Quom[] activeQuoms) {
		this.quoms = activeQuoms;
		this.syncEntity();
	}
	
	public Quom[] getQuoms() {
		return this.quoms;
	}
	
	public void setCurrentHotBarIndex(int val) {
		this.currentSelectedHotBarIndex = val;
		this.syncEntity();
	}
	
	public int getCurrentSelectedQuomIndex() {
		return this.currentSelectedHotBarIndex;
	}
	
	public void setHotBar(Quom[] hotBar) {
		this.hotBar = hotBar;
		this.printHotBar();
		this.syncEntity();
	}
	
	public Quom[] getHotBar() {
		// this.printHotBar();
		return this.hotBar;
	}
	
	public void printHotBar() {
		String index = "";
		String val = "";
		for (int i = 0; i < 9; i++) {
			index += i + ",";
			val += ((this.hotBar[i] == null) + "").substring(0, 1) + ",";
		}
		System.out.println(this.player.worldObj.isRemote ? "Client" : "Server");
		System.out.println(index);
		System.out.println(val);
	}
	
	public Quom getCurrentQuom() {
		return this.hotBar[this.currentSelectedHotBarIndex];
	}
	
	public void setChanging(EnumSmokeAction val) {
		this.turningToSmoke = val;
		if (val != EnumSmokeAction.NONE)
			this.smokeTick = ExtendedArcanePlayer.maxSmokeTicks;
		this.syncEntity();
	}
	
	public boolean isChanging() {
		return this.turningToSmoke != EnumSmokeAction.NONE;
	}
	
	public EnumSmokeAction getSmokeAction() {
		return this.turningToSmoke;
	}
	
	public void setSmokeTick(int tick) {
		this.smokeTick = tick;
		this.syncEntity();
	}
	
	public int getSmokeTick() {
		return this.smokeTick;
	}
	
	public void setTeleportDestination(double[] coords) {
		this.teleportationDestination = coords;
		this.syncEntity();
	}
	
	public void triggerSmokeAction(EnumSmokeAction action) {
		System.out.println("Triggered");
		if (action == EnumSmokeAction.TELEPORT) {
			if (this.teleportationDestination == null) return;
			System.out.println("Teleporting");
			double x = this.teleportationDestination[0];
			double y = this.teleportationDestination[1];
			double z = this.teleportationDestination[2];
			System.out.println(x + ":" + y + ":" + z);
			CoreUtil.teleportPlayer(this.player, x, y, z, true, false);
			/*
			player.setPositionAndUpdate(this.teleportationDestination[0],
					this.teleportationDestination[1],
					this.teleportationDestination[2]);
			 */
		}
		else if (action == EnumSmokeAction.SMOKE) {
			
		}
		this.teleportationDestination = null;
		this.syncEntity();
	}
	
	public boolean canLearn(String quomKey) {
		return QuomRegistry.canPlayerLearn(this, quomKey);
	}
	
	public void learnQuom(Quom quom) {
		//System.out.println("Learning " + quom.getName());
		QuomRegistry.unlockQuom(this, quom.getName());
	}
	
}
