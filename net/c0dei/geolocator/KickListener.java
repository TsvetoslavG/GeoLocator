package net.c0dei.geolocator;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPreLoginEvent;

@SuppressWarnings("deprecation")
public class KickListener implements Listener {
	
	@EventHandler
	public void onPlayerPreLogin(PlayerPreLoginEvent le) {
		String ISPName = IPLookup.getISP(le.getAddress());
		if(Plugin.getBannedISPs().getBoolean("blocked." + ISPName) == Boolean.TRUE) {
			le.setResult(PlayerPreLoginEvent.Result.KICK_FULL);
			le.setKickMessage(ChatColor.RED + "Your ISP has been banned.");
		}
	}
}