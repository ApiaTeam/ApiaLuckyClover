package me.bertek41.alc.managers;

import java.io.File;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.bertek41.alc.ALC;
import me.bertek41.alc.misc.Config;

public class FileManager {
	private ALC instance;	
    private File configFile;
    private FileConfiguration config;
    
	public FileManager(ALC instance) {
		this.instance = instance;
	}
	
	public void createFiles() {
		configFile = new File(instance.getDataFolder(), "config.yml");
		if(!configFile.exists()) {
			configFile.getParentFile().mkdir();
			instance.saveResource("config.yml", true);
		}
		config = new YamlConfiguration();
		try {
			config.load(configFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Config.setConfig(config);
	}
}
