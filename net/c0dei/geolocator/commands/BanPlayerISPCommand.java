package net.c0dei.geolocator.commands;

import java.net.InetAddress;

import net.c0dei.geolocator.IPLookup;
import net.c0dei.geolocator.Plugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class BanPlayerISPCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(isAllowed((Player) sender)) {
			if(args.length > 0) {
				Player player = Plugin.getPlugin().getServer().getPlayer(args[0]);
				if(player != null) {
					InetAddress playerIP = player.getAddress().getAddress();
					String ISPName = IPLookup.getISP(playerIP);
					FileConfiguration blocked = Plugin.getBannedISPs();
					if(!blocked.getBoolean("blocked." + ISPName) == Boolean.TRUE) {
						blocked.set("blocked." + ISPName, Boolean.TRUE);
					} else {
						sender.sendMessage(ChatColor.RED + "This player's ISP is already blocked!");
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