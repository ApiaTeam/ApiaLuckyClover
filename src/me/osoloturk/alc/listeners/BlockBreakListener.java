package me.osoloturk.alc.listeners;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import me.osoloturk.alc.ALC;
import me.osoloturk.alc.misc.Config;

public class BlockBreakListener implements Listener{

	private ALC instance;

	public BlockBreakListener(ALC instance) {
		this.instance = instance;
	}
	
	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled = true)
	public void BlockBreak(BlockBreakEvent event) {
		if(event.getBlock().getType() == Material.GRASS || event.getBlock().getType() == Material.LONG_GRASS) {
			if(ThreadLocalRandom.current().nextInt(101) <= Config.CHANCE.getInt()) {
				Player player = event.getPlayer();
				Map<Integer, ItemStack> returnedItems = player.getInventory().addItem(instance.getClover());
				if(returnedItems.containsKey(0)) {
					player.getWorld().dropItem(player.getLocation(), instance.getClover());
					Config.sendMessage(player, Config.SPACE_ERROR.getString());
				} else {
					Config.sendMessage(player, Config.CLOVER_GIVE.getString());
				}
			}
		}
	}
}
