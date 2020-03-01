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
			return false;
		}
		if(args[0].equalsIgnoreCase("yenile") || args[0].equalsIgnoreCase("reload")) {
			if(sender.hasPermission("alc.reload")) {
				instance.reloadConfig();
	            Config.setConfig(instance.getConfig());
				Config.sendMessage(sender, Config.RELOAD.getString());
				instance.hookSoftDepends();
				return true;
			}else {
				Config.sendMessage(sender, Config.NO_PERM.getString());;
				return false;
			}
		}else if(args.length == 1){
			sendHelpMessage(sender);
			return false;
		}
		if(args.length > 1) {
			if(args[0].equalsIgnoreCase("ver") || args[0].equalsIgnoreCase("give")) {
				if(!sender.hasPermission("alc.give")) {
					Config.sendMessage(sender, Config.NO_PERM.getString());
					return false;
				}
				int amount = 0;
				Player target = Bukkit.getPlayerExact(args[1]);
				if(target == null) {
					Config.sendMessage(sender, Config.PLAYER_ERROR.getString().replace("<PLAYER>", args[1]));
					return false;
				}
				try {
					amount = args.length != 3 ? 1 : Integer.parseInt(args[2]);
				} catch(NumberFormatException e) {
					amount = 1;
				}
				ItemStack clover = instance.getClover().clone();
				clover.setAmount(amount);
				Map<Integer, ItemStack> returnedItems = target.getInventory().addItem(clover);
				if(!returnedItems.isEmpty()) {
					target.getWorld().dropItemNaturally(target.getLocation(), returnedItems.get(0));
					Config.sendMessage(target, Config.SPACE_ERROR.getString().replace("<AMOUNT>", ""+returnedItems.get(0).getAmount()));
				} else {
					Config.sendMessage(target, Config.CLOVER_GAVE.getString());
				}
				Config.sendMessage(sender, Config.GAVE_MESSAGE.getString().replace("<PLAYER>", target.getName()).replace("<AMOUNT>", ""+amount));
			} else {
				sendHelpMessage(sender);
			}
		}
		return false;		
	}	
	
	public void sendHelpMessage(CommandSender sender) {
		if(sender.hasPermission("alc.give") || sender.hasPermission("alc.reload")) {
			Config.sendMessages(sender, Config.HELP.getStringList());
		}else {
			Config.sendMessage(sender, Config.NO_PERM.getString());
		}
	}
}
