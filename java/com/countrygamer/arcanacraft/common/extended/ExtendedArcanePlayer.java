package com.countrygamer.arcanacraft.common.extended;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import com.countrygamer.arcanacraft.common.ArcanaCraft;
import com.countrygamer.arcanacraft.common.quom.Quom;
import com.countrygamer.arcanacraft.common.quom.QuomRegistry;
import com.countrygamer.arcanacraft.common.quom.Tiers;
import com.countrygamer.countrygamercore.Base.Plugin.extended.ExtendedEntity;
import com.countrygamer.countrygamercore.lib.CoreUtil;
import com.countrygamer.countrygamercore.lib.LogBlock;

public class ExtendedArcanePlayer extends ExtendedEntity {
	
	public static final int maxManusTicks = 20 * 5;
	public static final int maxSmokeTicks = 20 * 6;
	
	private boolean isActive;
	
	private int manus, maxManus;
	private int manusTick;
	
	private int internalDormantFlux;
	
	private String currentArcanaPage;
	
	private Quom[] discoveredQuoms, learnedQuoms;
	private Quom[] hotBar;
	private int currentSelectedHotBarIndex;
	
	private EnumSmokeAction turningToSmoke;
	private int smokeTick;
	
	private double[] teleportationDestination;
	private boolean isSmoke = false;
	
	private Map<String, NBTTagCompound> quomData = new HashMap<String, NBTTagCompound>();
	
	public ExtendedArcanePlayer(EntityPlayer player) {
		super(player);
		this.isActive = false;
		this.maxManus = 20;
		this.manus = 20;
		this.currentArcanaPage = "Info";
		this.turningToSmoke = EnumSmokeAction.NONE;
		
		this.hotBar = new Quom[9];
		this.currentSelectedHotBarIndex = 0;
		
		// ArcanaCraft.logger.info("Constructor");
		if (this.discoveredQuoms == null) {
			this.discoveredQuoms = new Quom[QuomRegistry.quomRegistry.size()];
		}
		if (this.learnedQuoms == null) {
			this.discoveredQuoms = new Quom[QuomRegistry.quomRegistry.size()];
		}
		
	}
	
	@Override
	public void init(Entity entity, World world) {
	}
	
	@Override
	public void saveNBTData(NBTTagCompound compound) {
		ArcanaCraft.logger.info("Save");
		compound.setBoolean("isActive", this.isActive);
		compound.setInteger("staminaCounter", this.manusTick);
		compound.setInteger("maxStamina", this.maxManus);
		compound.setInteger("stamina", this.manus);
		compound.setString("currentPage", this.currentArcanaPage);
		compound.setInteger("currentHotBarIndex", this.currentSelectedHotBarIndex);
		
		NBTTagList discoveredList = new NBTTagList();
		if (this.discoveredQuoms != null) {
			compound.setInteger("discoveredQuoms_length", this.discoveredQuoms.length);
			for (int i = 0; i < this.discoveredQuoms.length; i++) {
				if (this.discoveredQuoms[i] != null) {
					NBTTagCompound quomTag = new NBTTagCompound();
					quomTag.setInteger("quomID", this.discoveredQuoms[i].getID());
					discoveredList.appendTag(quomTag);
				}
			}
		}
		compound.setTag("discoveredQuoms", discoveredList);
		
		NBTTagList learnedList = new NBTTagList();
		if (this.learnedQuoms != null) {
			compound.setInteger("learnedQuoms_length", this.learnedQuoms.length);
			for (int i = 0; i < this.learnedQuoms.length; i++) {
				if (this.learnedQuoms[i] != null) {
					NBTTagCompound quomTag = new NBTTagCompound();
					quomTag.setInteger("quomID", this.learnedQuoms[i].getID());
					learnedList.appendTag(quomTag);
				}
			}
		}
		compound.setTag("learnedQuoms", learnedList);
		
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
		
		compound.setInteger("dormantFlux", this.internalDormantFlux);
		compound.setBoolean("isSmoke", this.isSmoke);
		
		NBTTagList quomDataList = new NBTTagList();
		for (final String key : this.quomData.keySet()) {
			NBTTagCompound quomDataTag = new NBTTagCompound();
			quomDataTag.setString("key", key);
			quomDataTag.setTag(key, this.quomData.get(key));
			quomDataList.appendTag(quomDataTag);
		}
		compound.setTag("quomData", quomDataList);
		
	}
	
	@Override
	public void loadNBTData(NBTTagCompound compound) {
		ArcanaCraft.logger.info("Load");
		this.isActive = compound.getBoolean("isActive");
		this.manusTick = compound.getInteger("staminaCounter");
		this.maxManus = compound.getInteger("maxStamina");
		this.manus = compound.getInteger("stamina");
		this.currentArcanaPage = compound.getString("currentPage");
		this.currentSelectedHotBarIndex = compound.getInteger("currentHotBarIndex");
		
		this.discoveredQuoms = new Quom[compound.getInteger("discoveredQuoms_length")];
		NBTTagList discoveredList = compound.getTagList("discoveredQuoms", 10);
		for (int i = 0; i < discoveredList.tagCount(); i++) {
			NBTTagCompound quomTag = discoveredList.getCompoundTagAt(i);
			int id = quomTag.getInteger("quomID");
			this.discoveredQuoms[id] = QuomRegistry.quomRegistry.get(id);
		}
		
		this.learnedQuoms = new Quom[compound.getInteger("learnedQuoms_length")];
		NBTTagList learnedList = compound.getTagList("learnedQuoms", 10);
		for (int i = 0; i < learnedList.tagCount(); i++) {
			NBTTagCompound quomTag = learnedList.getCompoundTagAt(i);
			int id = quomTag.getInteger("quomID");
			this.learnedQuoms[id] = QuomRegistry.quomRegistry.get(id);
		}
		
		this.hotBar = new Quom[9];
		NBTTagList hotbarList = compound.getTagList("hotbar", 10);
		for (int i = 0; i < hotbarList.tagCount(); i++) {
			NBTTagCompound quomTag = hotbarList.getCompoundTagAt(i);
			this.hotBar[quomTag.getInteger("slot")] = QuomRegistry.quomRegistry.get(quomTag
					.getInteger("quomID"));
		}
		
		this.turningToSmoke = EnumSmokeAction.getEnumFromID(compound.getInteger("turningToSmoke"));
		this.smokeTick = compound.getInteger("smokeTick");
		
		if (compound.getBoolean("hasDest")) {
			double x = compound.getDouble("teleportDestX");
			double y = compound.getDouble("teleportDestY");
			double z = compound.getDouble("teleportDestZ");
			this.teleportationDestination = new double[] {
					x, y, z
			};
		}
		
		this.internalDormantFlux = compound.getInteger("dormantFlux");
		this.isSmoke = compound.getBoolean("isSmoke");
		
		this.quomData.clear();
		NBTTagList quomDataList = compound.getTagList("quomData", 10);
		for (int i = 0; i < quomDataList.tagCount(); i++) {
			NBTTagCompound quomDataTag = quomDataList.getCompoundTagAt(i);
			String key = quomDataTag.getString("key");
			this.quomData.put(key, quomDataTag.getCompoundTag(key));
		}
		
	}
	
	public void setArcaic(boolean value) {
		this.isActive = value;
		this.syncEntity();
		
		this.removeAllQuoms();
		if (this.isActive) {
			this.learnStartingQuoms();
		}
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
	
	public void setCurrentHotBarIndex(int val) {
		this.currentSelectedHotBarIndex = val;
		this.syncEntity();
	}
	
	public int getCurrentSelectedQuomIndex() {
		return this.currentSelectedHotBarIndex;
	}
	
	public void setHotBar(Quom[] hotBar) {
		this.hotBar = hotBar;
		// this.printHotBar();
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
		LogBlock log = new LogBlock(ArcanaCraft.logger, "\n");
		log.addWithLine(this.player.worldObj.isRemote ? "Client" : "Server");
		log.addWithLine(index);
		log.addWithLine(val);
		log.log();
	}
	
	public Quom getCurrentQuom() {
		return this.hotBar[this.currentSelectedHotBarIndex];
	}
	
	public void nextQuom() {
		int count = 0;
		do {
			if (++this.currentSelectedHotBarIndex > 8) this.currentSelectedHotBarIndex = 0;
			count++;
		} while (count < 9 && this.hotBar[this.currentSelectedHotBarIndex] == null);
		this.syncEntity();
	}
	
	public void lastQuom() {
		int count = 0;
		do {
			if (--this.currentSelectedHotBarIndex < 0) this.currentSelectedHotBarIndex = 8;
			count++;
		} while (count < 9 && this.hotBar[this.currentSelectedHotBarIndex] == null);
		this.syncEntity();
	}
	
	public void setChanging(EnumSmokeAction val) {
		this.turningToSmoke = val;
		if (val != EnumSmokeAction.NONE) this.smokeTick = ExtendedArcanePlayer.maxSmokeTicks;
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
			this.isSmoke = true;
		}
		this.teleportationDestination = null;
		this.syncEntity();
	}
	
	public void printQuoms() {
		System.out.println("\n" + (this.player.worldObj.isRemote ? "Client" : "Server"));
		LogBlock log = new LogBlock(ArcanaCraft.logger, "\n");
		log.addWithLine("\tDiscovered:");
		for (int i = 0; i < this.discoveredQuoms.length; i++) {
			log.add((i != 0 ? ", " : "")
					+ (this.discoveredQuoms[i] == null ? "null" : this.discoveredQuoms[i].getName()));
		}
		log.addWithLine("\n\tLearned:");
		for (int i = 0; i < this.learnedQuoms.length; i++) {
			log.add((i != 0 ? ", " : "")
					+ (this.learnedQuoms[i] == null ? "null" : this.learnedQuoms[i].getName()));
		}
		log.log();
	}
	
	public void checkForDiscoveries(int type, PlayerInteractEvent.Action action, ItemStack itemStack) {
		for (int i = 0; i < QuomRegistry.quomRegistry.size(); i++) {
			Quom quom = QuomRegistry.quomRegistry.get(i);
			if (!this.hasDiscoveredQuom(quom)) {
				// ArcanaCraft.logger.info("Checking disc " + quom.getName());
				quom.checkForDiscovery(this, type, action, itemStack);
			}
		}
	}
	
	public boolean hasDiscoveredQuom(Quom quom) {
		for (int i = 0; i < this.discoveredQuoms.length; i++) {
			if (quom != null && this.discoveredQuoms[i] == null)
				return false;
			else if (quom == null)
				return false;
			else if (quom.equals(this.discoveredQuoms[i])) return true;
		}
		return false;
	}
	
	public boolean discoverQuom(Quom quom) {
		if (this.hasDiscoveredQuom(quom))
			return false;
		else {
			this.discoveredQuoms[quom.getID()] = quom;
			// TODO Remove this learning line!
			this.learnQuom(quom);
			//
			this.syncEntity();
			return true;
		}
	}
	
	public boolean hasLearnedQuom(Quom quom) {
		if (!this.hasDiscoveredQuom(quom)) return false;
		for (int i = 0; i < this.learnedQuoms.length; i++) {
			if (quom.equals(this.learnedQuoms[i])) return true;
		}
		return false;
	}
	
	public boolean learnQuom(Quom quom) {
		if (!quom.canLearnNormally(this)) return false;
		
		if (this.hasDiscoveredQuom(quom) && !this.hasLearnedQuom(quom)) {
			this.learnedQuoms[quom.getID()] = quom;
			this.syncEntity();
			return true;
		}
		return false;
	}
	
	public void forceLearnQuom(Quom quom, boolean heritage) {
		if (this.hasLearnedQuom(quom)) return;
		if (heritage && quom.getParent() != null) {
			if (!this.hasLearnedQuom(quom.getParent())) {
				this.forceLearnQuom(quom.getParent(), heritage);
			}
		}
		this.discoveredQuoms[quom.getID()] = quom;
		this.learnedQuoms[quom.getID()] = quom;
		this.syncEntity();
	}
	
	public void forceRemoveQuom(Quom quom, boolean heritage) {
		if (!this.hasDiscoveredQuom(quom) || !this.hasLearnedQuom(quom)) return;
		if (heritage && quom.getParent() != null) {
			if (this.hasDiscoveredQuom(quom.getParent()) || this.hasLearnedQuom(quom.getParent())) {
				this.forceRemoveQuom(quom, heritage);
			}
		}
		this.discoveredQuoms[quom.getID()] = null;
		this.learnedQuoms[quom.getID()] = null;
		this.syncEntity();
	}
	
	public void learnStartingQuoms() {
		for (int i = 0; i < QuomRegistry.quomRegistry.size(); i++) {
			Quom quom = QuomRegistry.quomRegistry.get(i);
			
			if (!quom.canLearnNormally(this)) continue;
			
			if (quom.getParent() == null) {
				this.forceLearnQuom(quom, false);
			}
		}
	}
	
	public void removeAllQuoms() {
		System.out.println("Removing all quoms");
		int size = QuomRegistry.quomRegistry.size();
		this.discoveredQuoms = new Quom[size];
		this.learnedQuoms = new Quom[size];
		this.syncEntity();
	}
	
	public void verifyQuoms() {
		int actualSize = QuomRegistry.quomRegistry.size();
		
		if (this.discoveredQuoms != null && this.discoveredQuoms.length != actualSize) {
			Quom[] savedQuoms = this.discoveredQuoms;
			
			this.discoveredQuoms = new Quom[actualSize];
			
			for (int i = 0; i < actualSize; i++) {
				if (i < savedQuoms.length) {
					if (savedQuoms[i] != null) {
						Quom quom = savedQuoms[i].copy();
						if (quom != null) {
							this.discoveredQuoms[quom.getID()] = quom.copy();
						}
					}
				}
			}
		}
		
		if (this.learnedQuoms != null && this.learnedQuoms.length != actualSize) {
			Quom[] savedQuoms = this.learnedQuoms;
			
			this.learnedQuoms = new Quom[actualSize];
			
			for (int i = 0; i < actualSize; i++) {
				if (i < savedQuoms.length) {
					if (savedQuoms[i] != null) {
						Quom quom = savedQuoms[i].copy();
						if (quom != null) {
							this.learnedQuoms[quom.getID()] = quom.copy();
						}
					}
				}
			}
		}
		
		this.syncEntity();
	}
	
	public Quom[] getLearnedQuoms() {
		return this.learnedQuoms;
	}
	
	public boolean canCast(int cost) {
		return this.manus >= cost;
	}
	
	public void decrementManus(int cost) {
		if (this.canCast(cost)) {
			this.manus -= cost;
		}
	}
	
	public void incrementFlux(Tiers.MANUS manusTier) {
		this.addFlux(manusTier.getTierValue() * 20);
	}
	
	public void addFlux(int value) {
		this.internalDormantFlux += value;
	}
	
	public boolean drainFlux(int value) {
		if (this.internalDormantFlux >= value) {
			this.internalDormantFlux -= value;
			return true;
		}
		return false;
	}
	
	public void clearFlux() {
		this.drainFlux(this.internalDormantFlux);
	}
	
	public boolean isWisp() {
		return this.isSmoke;
	}
	
	public void revertSmoke() {
		this.isSmoke = false;
		this.syncEntity();
	}
	
	public void clearQuomData() {
		this.quomData.clear();
		this.syncEntity();
	}
	
	public void addQuomData(String key, NBTTagCompound data) {
		this.quomData.put(key, data);
		this.syncEntity();
	}
	
	public NBTTagCompound getQuomData(String key) {
		return this.quomData.get(key);
	}
	
	public NBTTagCompound removeQuomData(String key) {
		NBTTagCompound data = this.quomData.remove(key);
		this.syncEntity();
		return data;
	}
	
}
