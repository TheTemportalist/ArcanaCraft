package com.countrygamer.arcanacraft.common;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.player.FillBucketEvent;

import com.countrygamer.arcanacraft.client.gui.GuiArcana;
import com.countrygamer.arcanacraft.client.particle.Particles;
import com.countrygamer.arcanacraft.common.extended.EnumSmokeAction;
import com.countrygamer.arcanacraft.common.extended.ExtendedArcanePlayer;
import com.countrygamer.arcanacraft.common.quom.QuomRegistry;
import com.countrygamer.core.Base.Plugin.ExtendedEntity;
import com.countrygamer.core.Base.Plugin.PluginBase;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = ArcanaCraft.pluginID, name = ArcanaCraft.pluginName,
		version = ArcanaCraft.pluginVersion)
public class ArcanaCraft extends PluginBase implements IFuelHandler, IGuiHandler {
	
	public static final String pluginName = "ArcanaCraft";
	public static final String pluginID = "arcanacraft";
	public static final String pluginVersion = "0.0.1";
	
	public static final Logger logger = Logger.getLogger(ArcanaCraft.pluginName);
	
	@SidedProxy(serverSide = "com.countrygamer.arcanacraft.common.CommonProxy",
			clientSide = "com.countrygamer.arcanacraft.client.ClientProxy")
	public static CommonProxy proxy;
	
	@Instance(ArcanaCraft.pluginID)
	public static ArcanaCraft instance;
	
	public static KeyHandler keyHandler;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		super.preInitialize(pluginName, event, ArcanaCraft.proxy, new ACOptions(),
				new ACItems(), new ACBlocks(), new ACBiomes(), null);
		this.registerHandlers(this, this, this, this);
		this.registerPacketClass(PacketCastQuom.class);
		
		FMLCommonHandler.instance().bus().register(this);
		this.registerExtendedPlayer("Extended Arcane Player",
				ExtendedArcanePlayer.class, true);
		if (event.getSide() == Side.CLIENT) {
			ArcanaCraft.keyHandler = new KeyHandler();
			FMLCommonHandler.instance().bus().register(ArcanaCraft.keyHandler);
		}
		QuomRegistry.registerQuoms();
		
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		super.initialize(event);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		super.postInitialize(event);
	}
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity tileEnt = world.getTileEntity(x, y, z);
		if (ID == ACOptions.arcanaGui || ID == ACOptions.arcanaGuiReset) {
		}
		return null;
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity tileEnt = world.getTileEntity(x, y, z);
		if (ID == ACOptions.arcanaGui || ID == ACOptions.arcanaGuiReset) {
			boolean reset = ID == ACOptions.arcanaGuiReset;
			return new GuiArcana(player);
		}
		return null;
	}
	
	@Override
	public int getBurnTime(ItemStack fuel) {
		return 0;
	}
	
	public static Map<Block, Item> buckets = new HashMap<Block, Item>();
	
	@SubscribeEvent
	public void onBucketFill(FillBucketEvent event) {
		// ArcanaCraft.logger.info("On Bucket Fill");
		ItemStack result = fillCustomBucket(event.world, event.target);
		
		if (result == null) return;
		
		event.result = result;
		event.setResult(Result.ALLOW);
	}
	
	private ItemStack fillCustomBucket(World world, MovingObjectPosition pos) {
		
		Block block = world.getBlock(pos.blockX, pos.blockY, pos.blockZ);
		
		Item bucket = buckets.get(block);
		// ArcanaCraft.logger.info("Null bucket = " + (bucket == null));
		if (bucket != null
				&& world.getBlockMetadata(pos.blockX, pos.blockY, pos.blockZ) == 0) {
			world.setBlockToAir(pos.blockX, pos.blockY, pos.blockZ);
			// ArcanaCraft.logger.info(bucket.getUnlocalizedName());
			return new ItemStack(bucket);
		}
		else
			return null;
		
	}
	
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {
		if (event.entity != null && event.entity instanceof EntityPlayer) {
			// System.out.println("Entity Constructing");
			
		}
	}
	
	@SubscribeEvent
	public void playerTick(PlayerTickEvent event) {
		if (!(event.player != null && event.phase == TickEvent.Phase.START)) return;
		// System.out.println("PlayerTick");
		
		IExtendedEntityProperties props = ExtendedEntity.getExtended(event.player,
				ExtendedArcanePlayer.class);
		if (props == null) return;
		
		ExtendedArcanePlayer arcanePlayer = (ExtendedArcanePlayer) props;
		
		if (arcanePlayer.isPlayerArcaic()) {
			EnumSmokeAction smokeAction = EnumSmokeAction.NONE;
			if (event.side == Side.SERVER) {
				if (arcanePlayer.getManus() < arcanePlayer.getMaxManus()) {
					if (arcanePlayer.getManusCounter() <= 0) {
						arcanePlayer.incrementManus(1);
						arcanePlayer
								.setManusCounter(ExtendedArcanePlayer.maxManusTicks);
					}
					else
						arcanePlayer
								.setManusCounter(arcanePlayer.getManusCounter() - 1);
				}
				if (arcanePlayer.isChanging()) {
					// System.out.println("Player is changing");
					event.player.addPotionEffect(new PotionEffect(
							Potion.moveSlowdown.id, 2, 1000));
					int smokeTick = arcanePlayer.getSmokeTick();
					if (smokeTick > 0) {
						arcanePlayer.setSmokeTick(smokeTick - 1);
					}
					else {
						smokeAction = arcanePlayer.getSmokeAction();
						arcanePlayer.setChanging(EnumSmokeAction.NONE);
					}
				}
			}
			else if (event.side == Side.CLIENT) {
				if (arcanePlayer.getSmokeTick() > 0) {
					// System.out.println("Spawn smoke set");
					Particles.spawnArcaneSmokeConversion(event.player);
				}
			}
			if (smokeAction != EnumSmokeAction.NONE)
				arcanePlayer.triggerSmokeAction(smokeAction);
		}
		
	}
	
}
