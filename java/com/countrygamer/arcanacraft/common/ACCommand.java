package com.countrygamer.arcanacraft.common;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

import com.countrygamer.arcanacraft.common.extended.ExtendedArcanePlayer;
import com.countrygamer.arcanacraft.common.quom.Quom;
import com.countrygamer.arcanacraft.common.quom.QuomRegistry;
import com.countrygamer.core.Base.Plugin.extended.ExtendedEntity;

public class ACCommand extends CommandBase {
	
	@Override
	public String getCommandName() {
		return "arcana";
	}
	
	@Override
	public String getCommandUsage(ICommandSender var1) {
		return "/arcana <me:PLAYERNAME> quom <add:remove> <QUOMNAME:all> OR ...";
	}
	
	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (sender instanceof EntityPlayer) {
			EntityPlayer senderPlayer = (EntityPlayer) sender;
			
			if (args.length < 2) return;
			
			EntityPlayer affectedPlayer = null;
			
			if (args[0].equalsIgnoreCase("me")) {
				// affecting this player
				affectedPlayer = senderPlayer;
			}
			else {
				// affecting that player
				affectedPlayer = MinecraftServer.getServer().getConfigurationManager()
						.getPlayerForUsername(args[0]);
			}
			
			ExtendedArcanePlayer arcanePlayer = (ExtendedArcanePlayer) ExtendedEntity.getExtended(
					affectedPlayer, ExtendedArcanePlayer.class);
			
			if (args[1].equalsIgnoreCase("quom")) {
				// command affects the quoms
				// arcana PLAYER quom ...
				
				if (args.length < 3) return;
				
				if (args[2].equalsIgnoreCase("add")) {
					// adding quom(s)
					// arcana PLAYER quom add ...
					
					if (args.length < 4) return;
					
					if (args[3].equalsIgnoreCase("all")) {
						// adding all quoms
						// arcana PLAYER quom add all
						
						for (int i = 0; i < QuomRegistry.quomRegistry.size(); i++) {
							arcanePlayer.forceLearnQuom(QuomRegistry.quomRegistry.get(i), true);
						}
						ArcanaCraft.logger.info("Added all Quoms");
						return;
					}
					else {
						// adding a specific quom
						// arcana PLAYER quom add QUOMNAME
						
						Quom quom = QuomRegistry.getQuom(this.getQuomKey(args[3]));
						if (!arcanePlayer.hasDiscoveredQuom(quom)
								|| !arcanePlayer.hasLearnedQuom(quom)) {
							arcanePlayer.forceLearnQuom(quom, true);
						}
						
						return;
					}
					
				}
				else if (args[2].equalsIgnoreCase("remove")) {
					// removing quom(s)
					// arcana PLAYER quom remove ...
					
					if (args.length < 4) return;
					
					if (args[3].equalsIgnoreCase("all")) {
						// removing all quoms (except initals)
						// arcana PLAYER quom remove all
						
						arcanePlayer.removeAllQuoms();
						arcanePlayer.learnStartingQuoms();
						ArcanaCraft.logger.info("Removed all quoms");
						return;
					}
					else {
						// removing a specific quom (add there for all it's children)
						// cannot be a starting quom
						// arcana PLAYER quom remove QUOMNAME
						
						Quom quom = QuomRegistry.getQuom(this.getQuomKey(args[3]));
						if (arcanePlayer.hasDiscoveredQuom(quom)
								|| arcanePlayer.hasLearnedQuom(quom)) {
							arcanePlayer.forceRemoveQuom(quom, false);
						}
						
						return;
					}
				}
			}
			ExtendedEntity.syncEntity(arcanePlayer);
		}
	}
	
	private String getQuomKey(String argInput) {
		String quomName = "";
		char[] chars = argInput.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			quomName += chars[i] == '_' ? " " : chars[i];
		}
		return quomName;
	}
	
}
