package me.bertek41.alc.listeners;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import me.bertek41.alc.ALC;
import me.bertek41.alc.misc.Config;

public class BlockBreakListener implements Listener{

	private ALC instance;

	public BlockBreakListener(ALC instance) {
		this.instance = instance;
	}
	
	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled = true)
	public void BlockBreak(BlockBreakEvent event) {
		if(!event.getBlock().getType().toString().endsWith("GRASS") || !event.getPlayer().hasPermission("alc.user")) return;
		if(ThreadLocalRandom.current().nextInt(101) <= Config.CHANCE.getInt()) {
			Player player = event.getPlayer();
			Map<Integer, ItemStack> returnedItems = player.getInventory().addItem(instance.getClover());
			if(!returnedItems.isEmpty()) {
				returnedItems.values().forEach(item -> player.getWorld().dropItemNaturally(player.getLocation(), item));
				Config.sendMessage(player, Config.SPACE_ERROR.getString());
			} else {
				Config.sendMessage(player, Config.CLOVER_GAVE.getString());
			}
		}
	}
}
