package com.countrygamer.arcanacraft.common;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.player.FillBucketEvent;

import com.countrygamer.arcanacraft.common.extended.ExtendedArcanePlayer;
import com.countrygamer.core.Base.Plugin.PluginBase;

import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.IGuiHandler;

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
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		super.preInitialize(pluginName, event, ArcanaCraft.proxy, new ACOptions(),
				new ACItems(), new ACBlocks(), new ACBiomes(), null);
		this.registerHandlers(this, this, this, this);
		this.registerExtendedPlayer("Extended Arcane Player",
				ExtendedArcanePlayer.class, true);
		
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
		if (ID == ACOptions.arcanaGui) {
		}
		return null;
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity tileEnt = world.getTileEntity(x, y, z);
		if (ID == ACOptions.arcanaGui) {
			// TODO return the gui
			return null;
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
			System.out.println("Entity Constructing");
		}
	}
	
}
