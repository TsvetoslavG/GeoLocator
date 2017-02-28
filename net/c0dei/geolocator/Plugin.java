package net.c0dei.geolocator;

import java.io.File;
import java.io.IOException;

import net.c0dei.geolocator.commands.BanPlayerISPCommand;
import net.c0dei.geolocator.commands.GeoLocatorCommand;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Plugin extends JavaPlugin {
	
	private static Plugin plugin;
	public static FileConfiguration ispBanListConfigFile;
	public static File ispBanListFile;
	
	@Override
	public void onEnable() {
		plugin = this;
		ispBanListFile = new File(getDataFolder().getAbsolutePath() +  "/ispbanlist.yml");
		ispBanListConfigFile = YamlConfiguration.loadConfiguration(ispBanListFile);
		saveDefaultConfig();
		getServer().getPluginManager().registerEvents(new KickListener(), this);
		getCommand("bpisp").setExecutor(new BanPlayerISPCommand());
		getCommand("glp").setExecutor(new GeoLocatorCommand());
		getServer().broadcastMessage("loaded");
	}
	
	public static Plugin getPlugin() {
		return plugin;
	}
	
	public static FileConfiguration getBannedISPs() {
		return ispBanListConfigFile;
	}
	
	public static void saveBannedISPs() {
		try {
			ispBanListConfigFile.save(ispBanListFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}