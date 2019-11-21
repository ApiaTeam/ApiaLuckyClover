package me.bertek41.alc;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import me.bertek41.alc.listeners.BlockBreakListener;
import me.bertek41.alc.listeners.CloverOpenListener;
import me.bertek41.alc.managers.FileManager;
import me.bertek41.alc.misc.Config;

public class ALC extends JavaPlugin {
	private static ALC instance;
	private String nmsVersion;
	private ItemStack clover;
	private FileManager fileManager;
	
	public static ALC getInstance() {
		return instance;
	}
	
	public void onEnable() {		
		instance = this;
		fileManager = new FileManager(this);
		nmsVersion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			@Override
			public void run() {
				fileManager.createFiles();
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
	
	public String getNMSVersion() {
		return nmsVersion;
	}
	
	public ItemStack getClover() {
		return clover;
	}
	
}
