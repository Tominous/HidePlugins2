package com.complexcode.hpl2plus.events;

import com.complexcode.hpl2plus.HidePlugins;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinMessageUpdate implements Listener {
	public String broadcast;
	public String inputline;
  
	private HidePlugins plugin;
  
	public JoinMessageUpdate(HidePlugins plugin) {
		this.plugin = plugin;
	}
  
	@EventHandler
	public void playerJoin(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		if(p.isOp()) {
			if(plugin.config.getBoolean("config.news-broadcast")) {
				broadcast();
				p.sendMessage(plugin.consoleColors(broadcast));
			}
			
			if(!plugin.version.equals(plugin.latestVersion) && plugin.config.getBoolean("config.updates")) {
				p.sendMessage(plugin.consoleColors(plugin.messages("update-messages.message-one")).replace("%newversion%", plugin.latestVersion));
				p.sendMessage(plugin.consoleColors(plugin.messages("update-messages.message-two")).replace("%link%", "https://www.spigotmc.org/resources/25317/"));
			} 
		} 
	}
  
	public void broadcast() {
		try {
			HttpURLConnection con = (HttpURLConnection)(new URL("https://pastebin.com/raw/fky13qvD")).openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			broadcast = "";
			
			while ((inputline = in.readLine()) != null)
				broadcast = broadcast + inputline + "\n"; 
		} catch (Exception exception) {}
	}
}