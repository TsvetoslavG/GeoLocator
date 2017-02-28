package net.c0dei.geolocator.commands;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;

import net.c0dei.geolocator.Plugin;
import net.minecraft.util.org.apache.commons.io.IOUtils;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.json.JSONException;
import org.json.JSONObject;

public class GeoLocatorCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(isAllowed((Player) sender)) {
			if(args.length > 0) {
				Player selPlayer = Plugin.getPlugin().getServer().getPlayer(args[0]);
				if(selPlayer != null) {
					InetAddress selPlayerAddress = selPlayer.getAddress().getAddress();
					String selPlayerIP = selPlayerAddress.toString().replaceAll("/", "");
					if(!selPlayerIP.matches("(?i).*127.0.0.1*")) {
						String apiAddress = "http://ip-api.com/json/" + selPlayerIP;
						String JSONData = null;
						try {
							JSONData = IOUtils.toString(new URL(apiAddress));
						} catch(IOException ie) {
							ie.printStackTrace();
						}
						try {
							JSONObject JSONResult = new JSONObject(JSONData);
							if(JSONResult.get("status").equals("fail")) {
								sender.sendMessage(ChatColor.RED + "Unable to locate this player!");
							} else {
								sender.sendMessage(ChatColor.RED + "Successfully retrieved location information for " + selPlayer.getDisplayName() + ChatColor.RED + ".");
								sender.sendMessage(ChatColor.RED + "IP Address: " + ChatColor.GREEN + selPlayerIP);
								if(JSONResult.get("country").toString().length() > 1) sender.sendMessage(ChatColor.RED + "Country: " + ChatColor.GREEN + JSONResult.get("country") + " (" + JSONResult.get("countryCode") + ")");
								if(JSONResult.get("regionName").toString().length() > 1) sender.sendMessage(ChatColor.RED + "Region: " + ChatColor.GREEN + JSONResult.get("regionName") + " (" + JSONResult.get("region") + ")");
								if(JSONResult.get("city").toString().length() > 1) sender.sendMessage(ChatColor.RED + "City: " + ChatColor.GREEN + JSONResult.get("city"));
								if(JSONResult.get("lat").toString().length() > 1) sender.sendMessage(ChatColor.RED + "Latitude: " + ChatColor.GREEN + JSONResult.get("lat"));
								if(JSONResult.get("lon").toString().length() > 1) sender.sendMessage(ChatColor.RED + "Longitude: " + ChatColor.GREEN + JSONResult.get("lon"));
								if(JSONResult.get("zip").toString().length() > 1) sender.sendMessage(ChatColor.RED + "Region Code: " + ChatColor.GREEN + JSONResult.get("zip"));
								if(JSONResult.get("timezone").toString().length() > 1) sender.sendMessage(ChatColor.RED + "Local Timezone: " + ChatColor.GREEN + JSONResult.get("timezone"));
								if(JSONResult.get("as").toString().length() > 1) sender.sendMessage(ChatColor.RED + "AS#: " + ChatColor.GREEN + JSONResult.get("as"));
								if(JSONResult.get("isp").toString().length() > 1) sender.sendMessage(ChatColor.RED + "ISP: " + ChatColor.GREEN + JSONResult.get("isp"));
							}
						} catch(JSONException je) {
							je.printStackTrace();
						}
					} else {
						sender.sendMessage(ChatColor.RED + "This player is connected to the local host, and cannot be located.");
					}
					return true;
				} else {
					sender.sendMessage(ChatColor.RED + "Player not online!");
					return true;
				}
			} else {
				sender.sendMessage(ChatColor.RED + "Not enough arguments!");
				sender.sendMessage(ChatColor.RED + "Usage: /" + cmd.getName() + " [player]");
				return true;
			}
		} else {
			sender.sendMessage(ChatColor.RED + "You don't have permission!");
			return true;
		}
	}
	
	public boolean isAllowed(Player player) {
		if(player instanceof Player && player.isOp()) return true;
		if(player.hasPermission("geolocator.allow")) return true;
		if(player instanceof ConsoleCommandSender) return true;
		return false;
	}

}
