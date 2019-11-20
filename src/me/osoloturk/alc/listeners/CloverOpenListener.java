package me.osoloturk.alc.listeners;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.osoloturk.alc.ALC;
import me.osoloturk.alc.misc.Config;

public class CloverOpenListener  implements Listener{

	private ALC instance;

	public CloverOpenListener(ALC instance) {
		this.instance = instance;
	}
	
	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled = true)
	public void BlockBreak(PlayerInteractEvent event) {
		if(event.getPlayer().getItemInHand() == instance.getClover()) {
			event.setCancelled(true);
			Player player = event.getPlayer();
			ItemStack cloverOfPlayer = player.getItemInHand();
			cloverOfPlayer.setAmount(cloverOfPlayer.getAmount() - 1);
			player.setItemInHand(cloverOfPlayer);	
			player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK);
			player.playSound(player.getLocation(), Sound.valueOf(Config.SOUND.getString()), 3.0F, 0.5F);
			player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK);
			int commandNumber = ThreadLocalRandom.current().nextInt(instance.getCommandMaxNumber() + 1);
			String command = instance.getCommands().get(commandNumber);
			command.replaceAll("<PLAYER>", player.getName());
	        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "/" + command);
	        Config.sendMessage(player, Config.APPLY.getString());
		}
	}
}
