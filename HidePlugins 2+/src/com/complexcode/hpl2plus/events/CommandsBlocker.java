package com.complexcode.hpl2plus.events;

import com.complexcode.hpl2plus.HidePlugins;
import java.util.List;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandsBlocker implements Listener {
	private HidePlugins plugin;
  
	public CommandsBlocker(HidePlugins plugin) {
		this.plugin = plugin;
	}
  
	@EventHandler
	public boolean onBlockedCommand(PlayerCommandPreprocessEvent e) {
		FileConfiguration players = plugin.getPlayers();
		Player p = e.getPlayer();
		String name = p.getName();
		String ip = p.getAddress().getAddress().getHostAddress();
		int port = p.getAddress().getPort();
		int reports = plugin.getPlayers().getInt("players." + p.getUniqueId() + ".reports");
		
		if(plugin.getConfig().getBoolean("config.protections.commands-blocker.enable")) {
			for(String string : plugin.getConfig().getStringList("config.blocked-commands")) {
				if(e.getMessage().toLowerCase().startsWith("/" + string) && !p.hasPermission(plugin.getConfig().getString("config.protections.commands-blocker.permission"))) {
					e.setCancelled(true);
					p.sendMessage(plugin.colors(plugin.messages("no-permissions")).replace("%player%", name));
					players.set("players." + p.getUniqueId() + ".name", name);
					players.set("players." + p.getUniqueId() + ".reports", reports+1);
					players.set("players." + p.getUniqueId() + ".latest-command", "/" + string);
					players.set("players." + p.getUniqueId() + ".ip", ip + ":" + port);
					plugin.savePlayers();
					
					if(plugin.getConfig().getBoolean("config.sounds.enable")) {
						if(plugin.getConfig().getStringList("config.sounds.sounds-list").isEmpty() || plugin.getConfig().getStringList("config.sounds.sounds-list") == null) {
							for(Player op : Bukkit.getOnlinePlayers()) {
								if(op.isOp()) {
									op.sendMessage("");
									op.sendMessage(plugin.consoleColors("&e>>> &cHidePlugins 2+"));
									op.sendMessage(plugin.consoleColors("&e>>> " + plugin.messages("sounds-error")));
									op.sendMessage("");
								}
							}
						} else {
							List<String> lengthsound = plugin.getConfig().getStringList("config.sounds.sounds-list");
							int listsize = lengthsound.size();
							Random aleatorio = new Random(System.currentTimeMillis());
							int intAleatorio = aleatorio.nextInt(listsize);
							String soundnum = lengthsound.get(intAleatorio);
							aleatorio.setSeed(System.currentTimeMillis());
							p.playSound(p.getLocation(), Sound.valueOf(soundnum), 10.0F, 100.0F);
						}
					}
				} 
			}
		}
		return false;
	}
}