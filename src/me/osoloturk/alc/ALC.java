package me.osoloturk.alc;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import me.osoloturk.alc.listeners.BlockBreakListener;
import me.osoloturk.alc.listeners.CloverOpenListener;
import me.osoloturk.alc.managers.FileManager;
import me.osoloturk.alc.misc.Config;

public class ALC extends JavaPlugin {
	
	private static ALC instance;
	private ItemStack clover;
	private HashMap<Integer, String> commands = new HashMap<>();
	private int commandMaxNumber;
	private FileManager fileManager;

	public static ALC getInstance() {
		return instance;
	}

	public void onEnable() {		
		instance = this;
		fileManager = new FileManager(this);
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			@Override
			public void run() {
				fileManager.createFiles();
				int number = 1;
				for (String command : Config.REWARDS.getStringList()) {
					commands.put(number, command);
					setCommandMaxNumber(number);
					number++;
				}
			}
		});
		Config.setConfig(instance.getConfig());
		clover = new ItemStack(Material.valueOf(Config.MATERIAL.getString()));
		ItemMeta itemMeta = clover.getItemMeta();
		itemMeta.setDisplayName(Config.NAME.getString());
		itemMeta.setLore(Config.LORE.getStringList());
		clover.setItemMeta(itemMeta);
		
		Bukkit.getPluginManager().registerEvents(new BlockBreakListener(this), this);
		Bukkit.getPluginManager().registerEvents(new CloverOpenListener(this), this);
		Bukkit.getConsoleSender().sendMessage("Start ALC");
	}
	
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage("Off ALC");
	}

	public ItemStack getClover() {
		return clover;
	}

	public int getCommandMaxNumber() {
		return commandMaxNumber;
	}

	public void setCommandMaxNumber(int commandMaxNumber) {
		this.commandMaxNumber = commandMaxNumber;
	}
	
	public HashMap<Integer, String> getCommands() {
		return commands;
	}

	public void setCommands(HashMap<Integer, String> commands) {
		this.commands = commands;
	}
	
}
