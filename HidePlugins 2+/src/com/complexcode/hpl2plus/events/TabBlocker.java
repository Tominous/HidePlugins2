package com.complexcode.hpl2plus.events;

import com.complexcode.hpl2plus.HidePlugins;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;

public class TabBlocker implements Listener {
	private HidePlugins plugin;
  
	public TabBlocker(HidePlugins plugin) {
		this.plugin = plugin;
	}
  
	@EventHandler
	public void onCommandTabSend(PlayerCommandSendEvent e) {
		if(plugin.config.getBoolean("config.protections.tab-blocker.enable") && !e.getPlayer().hasPermission(plugin.config.getString("config.protections.tab-blocker.permission"))) {
			e.getCommands().clear();
			
			for(String listcommands : plugin.config.getStringList("config.tab-commands")) {
				e.getCommands().add(listcommands); 
			}
		} 
	}
}