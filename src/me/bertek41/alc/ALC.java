package me.bertek41.alc;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import me.bertek41.alc.commands.AlcMainCommand;
import me.bertek41.alc.listeners.BlockBreakListener;
import me.bertek41.alc.listeners.CloverOpenListener;
import me.bertek41.alc.managers.FileManager;
import me.bertek41.alc.misc.Config;
import me.bertek41.alc.misc.MetricsLite;
import me.bertek41.updater.UpdateChecker;

public class ALC extends JavaPlugin {
	private static ALC instance;
	private String nmsVersion;
	private ItemStack clover;
	private FileManager fileManager;
	
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
		new MetricsLite(this);
		Config.setConfig(instance.getConfig());
        getCommand("alc").setExecutor(new AlcMainCommand(this));
        String materialName = Config.MATERIAL.getString();
        if(getMethodType().equals("NEW")) {
        	materialName = materialName.replace("DOUBLE_PLANT", "SUNFLOWER");
        }        
		try{
			clover = new ItemStack(Material.valueOf(materialName));
		} catch (IllegalArgumentException error) {
			Bukkit.getConsoleSender().sendMessage("§8[ §6Apia Lucky Clover §8] §7-> §4Your  Material is deprecated or wrong please edit.");
	        if(getMethodType().equals("NEW")) {
	        	materialName = "SUNFLOWER";
	        } else {
	        	materialName = "DOUBLE_PLANT";
	        }
			clover = new ItemStack(Material.valueOf(materialName));
		}		
		ItemMeta itemMeta = clover.getItemMeta();
		itemMeta.setDisplayName(Config.NAME.getString());
		itemMeta.setLore(Config.LORE.getStringList());
		clover.setItemMeta(itemMeta);		
		Bukkit.getPluginManager().registerEvents(new BlockBreakListener(this), this);
		Bukkit.getPluginManager().registerEvents(new CloverOpenListener(this), this);
		getServer().getConsoleSender().sendMessage("§6ApiaLuckyClover §aEnabled");
		getServer().getConsoleSender().sendMessage("§6Created By §8[§4oSoloTurk§8]-§8[§4Bertek41§8]");
		if(Config.UPDATE.getBoolean() != true) return;
        new UpdateChecker(this, 72928).getVersion(version -> {
            if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
                Bukkit.getConsoleSender().sendMessage("§4[ §EUPDATE§4 §7§L| §5ALC ] §7 - §4There is not a new update available.");
            } else {
            	Bukkit.getConsoleSender().sendMessage("§4[ §EUPDATE§4 §7§L| §5ALC ] §7 - §4There is a new update available: "+ version);
            }
        });
	}
	
	public void onDisable() {
		getServer().getConsoleSender().sendMessage("§6ApiaLuckyClover §5Disabled");
		getServer().getConsoleSender().sendMessage("§6Created By §8[§4oSoloTurk§8]-§8[§4Bertek41§8]");
	}
	
	public static ALC getInstance() {
		return instance;
	}
	
	public String getNMSVersion() {
		return nmsVersion;
	}
	
	public ItemStack getClover() {
		return clover;
	}
    
	public String getMethodType() {
		if(Bukkit.getVersion().contains("1.8") || Bukkit.getVersion().contains("1.9") || Bukkit.getVersion().contains("1.10") || Bukkit.getVersion().contains("1.11") || Bukkit.getVersion().contains("1.12")) return "LEGACY";
		else return "NEW";
    }
    
}
