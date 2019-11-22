package me.bertek41.alc.listeners;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.bertek41.alc.ALC;
import me.bertek41.alc.misc.Config;

public class CloverOpenListener  implements Listener{
	private ALC instance;
	
	public CloverOpenListener(ALC instance) {
		this.instance = instance;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled = true)
	public void BlockBreak(PlayerInteractEvent event) {
		if(!event.hasItem() || (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK)) return;
		Player player = event.getPlayer();
		ItemStack item;
		if(instance.getNMSVersion().startsWith("v1_8_")) item = player.getItemInHand();
		else item = player.getInventory().getItemInMainHand();
		if(item == null || item.getItemMeta() == null) return;
		if(item.isSimilar(instance.getClover())) {
			event.setCancelled(true);
			item.setAmount(item.getAmount() - 1);
			if(item.getAmount() > 1) item.setAmount(item.getAmount()-1);
			else item = null;
			if(instance.getNMSVersion().startsWith("v1_8_")) player.setItemInHand(item);
			else player.getInventory().setItemInMainHand(item);
			player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK);
			String sound = Config.SOUND.getString();
			if(instance.getNMSVersion().startsWith("v1_14") || instance.getNMSVersion().startsWith("v1_13")) {
				sound = sound.replace("NOTE_PLING", "BLOCK_NOTE_BLOCK_PLING");
			}
			try {
				player.playSound(player.getLocation(), Sound.valueOf(sound), 3.0F, 0.5F);
			} catch (IllegalArgumentException error) {
				Bukkit.getConsoleSender().sendMessage("§8[ §6Apia Lucky Clover §8] §7-> §4Your  Sound is deprecated or wrong please edit.");
				if(instance.getNMSVersion().startsWith("v1_14") || instance.getNMSVersion().startsWith("v1_13")) sound = "BLOCK_NOTE_BLOCK_PLING";
				else sound = "NOTE_PLING";
				player.playSound(player.getLocation(), Sound.valueOf(sound), 3.0F, 0.5F);
			}
			player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK);
			String command = Config.REWARDS.getStringList().get(ThreadLocalRandom.current().nextInt(Config.REWARDS.getStringList().size()));
	        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("<PLAYER>", player.getName()));
	        Config.sendMessage(player, Config.SUCCESSFUL.getString());
		}
	}
}
