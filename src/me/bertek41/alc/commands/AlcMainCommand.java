package me.bertek41.alc.commands;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.bertek41.alc.ALC;
import me.bertek41.alc.misc.Config;


public class AlcMainCommand implements CommandExecutor {
	private ALC instance;	
	
	public AlcMainCommand(ALC instance) {
		this.instance = instance;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length == 0) {
			sendHelpMessage(sender);
		}
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase("yenile") || args[0].equalsIgnoreCase("reload")) {
				if(sender.hasPermission("alc.reload")) {
					instance.reloadConfig();
		            Config.setConfig(instance.getConfig());
					Config.sendMessage(sender, Config.RELOAD.getString());
				} else {
					Config.sendMessage(sender, Config.PERM.getString());;
				}
			} else {
				sendHelpMessage(sender);
			}
		}
		if(args.length == 2 || args.length == 3) {
			if(args[0].equalsIgnoreCase("ver") || args[0].equalsIgnoreCase("give")) {
				if(sender.hasPermission("alc.give")) {
					int amount = 0;
					Player target = Bukkit.getPlayer(args[1]);
					if(args.length != 3) {
						amount = 1;
					} else {			
						try {
							amount = Integer.parseInt(args[2]);
						} catch (NumberFormatException error) {
							amount = 1;
						}
					}		
					if (target != null) {
						ItemStack clover = instance.getClover().clone();
						clover.setAmount(amount);
						Map<Integer, ItemStack> returnedItems = target.getInventory().addItem(clover);
						if(!returnedItems.isEmpty()) {
							returnedItems.values().forEach(item -> target.getWorld().dropItemNaturally(target.getLocation(), item));
							Config.sendMessage(target, Config.SPACE_ERROR.getString().replace("<AMOUNT>", ""+returnedItems.size()));
						} else {
							Config.sendMessage(target, Config.CLOVER_GAVE.getString());
						}	
						Config.sendMessage(sender, Config.GAVE_MESSAGE.getString().replace("<PLAYER>", target.getName()).replace("<AMOUNT>", ""+amount));
					
					} else {
						Config.sendMessage(sender, Config.PLAYER_ERROR.getString().replace("<PLAYER>", target.getName()));
					}
				} else {
					Config.sendMessage(sender, Config.PERM.getString());;
				}
			} else {
				sendHelpMessage(sender);
			}
		}
		return false;		
	}	
	
	public void sendHelpMessage(CommandSender sender) {
		if(sender.hasPermission("alc.give")) {
			for(String message : Config.HELP.getStringList()) Config.sendMessage(sender, message);
		} else {
			Config.sendMessage(sender, Config.PERM.getString());
		}
	}
}
