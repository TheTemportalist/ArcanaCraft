package com.countrygamer.arcanacraft.common;

import java.util.UUID;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;

import com.countrygamer.arcanacraft.common.extended.ExtendedArcanePlayer;
import com.countrygamer.arcanacraft.common.quom.Quom;
import com.countrygamer.arcanacraft.common.quom.QuomRegistry;
import com.countrygamer.countrygamercore.base.extended.ExtendedEntity;
import com.countrygamer.countrygamercore.common.lib.util.Player;

public class ACCommand extends CommandBase {
	
	@Override
	public String getCommandName() {
		return "arcana";
	}
	
	@Override
	public String getCommandUsage(ICommandSender var1) {
		return "/arcana <me:PLAYERNAME> quom <remove:discover:learn> <QUOMNAME:all> OR ...";
	}
	
	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (sender instanceof EntityPlayer) {
			EntityPlayer senderPlayer = (EntityPlayer) sender;
			boolean didSomething = false;
			
			if (args.length < 2) return;
			
			EntityPlayer affectedPlayer = null;
			
			if (args[0].equalsIgnoreCase("me")) {
				// affecting this player
				affectedPlayer = senderPlayer;
			}
			else {
				// affecting that player
				affectedPlayer = Player.getPlayerByUUID(UUID.fromString(args[0]));
			}
			
			ExtendedArcanePlayer arcanePlayer = (ExtendedArcanePlayer) ExtendedEntity.getExtended(
					affectedPlayer, ExtendedArcanePlayer.class);
			
			if (args[1].equalsIgnoreCase("quom")) {
				// command affects the quoms
				// arcana PLAYER quom ...
				
				if (args.length < 3) return;
				
				if (args[2].equalsIgnoreCase("remove")) {
					// removing quom(s)
					// arcana PLAYER quom remove ...
					
					if (args.length < 4) return;
					
					if (args[3].equalsIgnoreCase("all")) {
						// removing all quoms (except initals)
						// arcana PLAYER quom remove all
						
						arcanePlayer.removeAllQuoms();
						ArcanaCraft.logger.info("Removed all quoms");
						
						if (args.length == 5 && args[4].equalsIgnoreCase("true")) {
							arcanePlayer.learnStartingQuoms();
						}
						
						didSomething = true;
					}
					else {
						// removing a specific quom (add there for all it's children)
						// cannot be a starting quom
						// arcana PLAYER quom remove QUOMNAME
						
						String quomKey = this.getQuomKey(args[3]);
						System.out.println(quomKey);
						Quom quom = QuomRegistry.getQuom(quomKey);
						if (quom != null
								&& (arcanePlayer.hasDiscoveredQuom(quom) || arcanePlayer
										.hasLearnedQuom(quom))) {
							arcanePlayer.forceRemoveQuom(quom, false);
						}
						didSomething = true;
					}
				}
				
				else if (args[2].equalsIgnoreCase("discover")) {
					// discovering quom(s)
					// arcana PLAYER quom discover ...
					
					if (args.length < 4) return;
					
					if (args[3].equalsIgnoreCase("all")) {
						// discovering all quoms
						// arcana PLAYEr quom discover all
						
						for (int i = 0; i < QuomRegistry.quomRegistry.size(); i++) {
							arcanePlayer.discoverQuom(QuomRegistry.quomRegistry.get(i));
						}
						ArcanaCraft.logger.info("Discovered all Quoms");
						didSomething = true;
						
					}
					else {
						// discovering a specific quom
						// arcana PLAYER quom discover QUOMNAME
						String quomKey = this.getQuomKey(args[3]);
						System.out.println(quomKey);
						Quom quom = QuomRegistry.getQuom(quomKey);
						if (quom != null && !arcanePlayer.hasDiscoveredQuom(quom)) {
							arcanePlayer.discoverQuom(quom);
						}
						didSomething = true;
					}
					
				}
				else if (args[2].equalsIgnoreCase("learn")) {
					// adding quom(s)
					// arcana PLAYER quom learn ...
					
					if (args.length < 4) return;
					
					if (args[3].equalsIgnoreCase("all")) {
						// adding all quoms
						// arcana PLAYER quom learn all
						
						for (int i = 0; i < QuomRegistry.quomRegistry.size(); i++) {
							arcanePlayer.forceLearnQuom(QuomRegistry.quomRegistry.get(i), true);
						}
						ArcanaCraft.logger.info("Learned all Quoms");
						didSomething = true;
					}
					else {
						// adding a specific quom
						// arcana PLAYER quom learn QUOMNAME
						
						String quomKey = this.getQuomKey(args[3]);
						System.out.println(quomKey);
						Quom quom = QuomRegistry.getQuom(quomKey);
						if (quom != null
								&& (!arcanePlayer.hasDiscoveredQuom(quom) || !arcanePlayer
										.hasLearnedQuom(quom))) {
							arcanePlayer.forceLearnQuom(quom, true);
						}
						didSomething = true;
					}
					
				}
			}
			ExtendedEntity.syncEntity(arcanePlayer);
			
			if (!didSomething) {
				Player.sendMessageToPlayer(senderPlayer, "Something was invalid");
			}
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
