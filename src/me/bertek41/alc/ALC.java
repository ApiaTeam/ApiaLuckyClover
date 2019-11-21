package me.bertek41.alc;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import me.bertek41.alc.commands.AlcMainCommand;
import me.bertek41.alc.listeners.BlockBreakListener;
import me.bertek41.alc.listeners.CloverOpenListener;
import me.bertek41.alc.managers.FileManager;
import me.bertek41.alc.misc.Config;
import me.bertek41.alc.misc.Metrics;

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
        Metrics metrics = new Metrics(this);
        metrics.addCustomChart(new Metrics.SimplePie("chart_id", () -> "My value"));
		Config.setConfig(instance.getConfig());
        getCommand("alc").setExecutor(new AlcMainCommand(this));
		clover = new ItemStack(Material.valueOf(Config.MATERIAL.getString()));
		ItemMeta itemMeta = clover.getItemMeta();
		itemMeta.setDisplayName(Config.NAME.getString());
		itemMeta.setLore(Config.LORE.getStringList());
		clover.setItemMeta(itemMeta);		
		Bukkit.getPluginManager().registerEvents(new BlockBreakListener(this), this);
		Bukkit.getPluginManager().registerEvents(new CloverOpenListener(this), this);
		Bukkit.getConsoleSender().sendMessage("§6Apia Lucky Clover §aEnabled");
		Bukkit.getConsoleSender().sendMessage("§6Created By §8[§4oSoloTurk§8]-§8[§4Bertek41§8]");
	}
	
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage("§6Apia Lucky Clover §5Enabled");
		Bukkit.getConsoleSender().sendMessage("§6Created By §8[§4oSoloTurk§8]-§8[§4Bertek41§8]");
	}
	
	public String getNMSVersion() {
		return nmsVersion;
	}
	
	public ItemStack getClover() {
		return clover;
	}
	
}
