package me.osoloturk.alc.misc;

import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public enum Config {
	PREFIX("Prefix"),
	MATERIAL("CLOVER.MATERIAL"),
	NAME("CLOVER.NAME"),
	LORE("CLOVER.LORE"),
	CHANCE("CLOVER.CHANCE"),
	SPACE_ERROR("INVENTORY_FULl"),
	CLOVER_GIVE("GIVE_YOUR_CLOVER"),
	SOUND("SOUND"),
	REWARDS("REWARDS"),
	APPLY("SUCCESFULL-MESSAGE");
    
    private static FileConfiguration config;
    private final String path;    

    private Config(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }

    public static void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(String.valueOf(PREFIX.getString()) + message);
    }

    public static void sendMessage(Player player, String message) {
        player.sendMessage(String.valueOf(PREFIX.getString()) + message);
    }

    public static void sendMessages(CommandSender sender, List<String> messages) {
        messages.forEach(message -> sender.sendMessage(String.valueOf(PREFIX.getString()) + message));
    }

    public static void sendMessages(Player player, List<String> messages) {
        messages.forEach(message -> player.sendMessage(String.valueOf(PREFIX.getString()) + message));
    }
    
    public static void broadcastMessage(List<String> messages) {
        messages.forEach(message -> Bukkit.broadcastMessage(message));
    }    
    
    public static void broadcastMessage(String message) {
        Bukkit.broadcastMessage(message);
    }

    public static void setConfig(FileConfiguration message) {
        if(Config.config == null) {
            Config.config = message;
        }
    }

    public Boolean getBoolean() {
        return config == null ? null : Boolean.valueOf(config.getBoolean(this.path));
    }

    public Double getDouble() {
        return config == null ? null : Double.valueOf(config.getDouble(this.path));
    }

    public Integer getInt() {
        return config == null ? null : Integer.valueOf(config.getInt(this.path));
    }

    public String getString() {
        return config == null ? null : ChatColor.translateAlternateColorCodes('&', config.getString(this.path));
    }

    public List<String> getStringList() {
        return config == null ? null : config.getStringList(this.path).stream().map(line -> ChatColor.translateAlternateColorCodes('&', line)).collect(Collectors.toList());
    }

    public String toString() {
        return config == null ? null : config.getString(this.path);
    }
}

