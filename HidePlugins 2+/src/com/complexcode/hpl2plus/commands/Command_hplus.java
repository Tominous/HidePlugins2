package com.complexcode.hpl2plus.commands;

import com.complexcode.hpl2plus.HidePlugins;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command_hplus implements CommandExecutor {
	private HidePlugins plugin;
  
	public Command_hplus(HidePlugins plugin) {
		this.plugin = plugin;
	}
  
	public boolean onCommand(CommandSender sender, Command command, String string, String[] args) {
		if(command.getName().equalsIgnoreCase("hplus")) {
			if(sender.hasPermission("hideplugins2.hplus")) {
				if(args.length == 0) {
					if(!(sender instanceof Player)) {
						plugin.b.sendMessage(plugin.consoleColors(plugin.messages("help-messages.usage")).replace("%help_command%", "/hplus help"));
					} else {
						Player p = (Player)sender;
						p.sendMessage(plugin.consoleColors(plugin.messages("help-messages.usage")).replace("%help_command%", "/hplus help"));
					}
				} else if(args.length == 1) {
					if(args[0].equalsIgnoreCase("help")) {
						if(!(sender instanceof Player)) {
							plugin.b.sendMessage(plugin.consoleColors(plugin.messages("help-messages.title")));
							plugin.b.sendMessage(plugin.consoleColors(plugin.messages("help-messages.hplus-command")).replace("%hplus_command%", "/hplus help"));
							plugin.b.sendMessage(plugin.consoleColors(plugin.messages("help-messages.dev-command")).replace("%dev_command%", "/hplus dev"));
							plugin.b.sendMessage(plugin.consoleColors(plugin.messages("help-messages.ver-command")).replace("%ver_command%", "/hplus version"));
							plugin.b.sendMessage(plugin.consoleColors(plugin.messages("help-messages.reload-command")).replace("%reload_command%", "/hplus reload"));
							plugin.b.sendMessage(plugin.consoleColors(plugin.messages("help-messages.disable-command")).replace("%disable_command%", "/hplus disable"));
						} else {
							Player p = (Player)sender;
							p.sendMessage(plugin.consoleColors(plugin.messages("help-messages.title")));
							p.sendMessage(plugin.consoleColors(plugin.messages("help-messages.hplus-command")).replace("%hplus_command%", "/hplus help"));
							p.sendMessage(plugin.consoleColors(plugin.messages("help-messages.dev-command")).replace("%dev_command%", "/hplus dev"));
							p.sendMessage(plugin.consoleColors(plugin.messages("help-messages.ver-command")).replace("%ver_command%", "/hplus version"));
							p.sendMessage(plugin.consoleColors(plugin.messages("help-messages.reload-command")).replace("%reload_command%", "/hplus reload"));
							p.sendMessage(plugin.consoleColors(plugin.messages("help-messages.disable-command")).replace("%disable_command%", "/hplus disable"));
						}
					} else if(args[0].equalsIgnoreCase("reload")) {
						plugin.reloadConfig();
						
						if(!(sender instanceof Player)) {
							plugin.b.sendMessage(plugin.consoleColors(plugin.messages("reload-config")));
						} else {
							Player p = (Player)sender;
							p.sendMessage(plugin.consoleColors(plugin.messages("reload-config")));
						}
					} else if(args[0].equalsIgnoreCase("dev")) {
						if(!(sender instanceof Player)) {
							plugin.b.sendMessage(plugin.consoleColors(plugin.messages("dev.line1")).replace("%author%", "ComplexCode"));
							plugin.b.sendMessage(plugin.consoleColors(plugin.messages("dev.line2")).replace("%discord%", "ComplexCode#0332"));
						} else {
							Player p = (Player)sender;
							p.sendMessage(plugin.consoleColors(plugin.messages("dev.line1")).replace("%author%", "ComplexCode"));
							p.sendMessage(plugin.consoleColors(plugin.messages("dev.line2")).replace("%discord%", "ComplexCode#0332"));
						}
						
					} else if(args[0].equalsIgnoreCase("disable")) {
						plugin.getPluginLoader().disablePlugin(plugin);
						
						if(!(sender instanceof Player)) {
							plugin.b.sendMessage(plugin.consoleColors(plugin.messages("disable-messages.message-one")).replace("%version%", plugin.version));
							plugin.b.sendMessage(plugin.consoleColors(plugin.messages("disable-messages.message-two")).replace("%author%", "-ComplexCode"));
						} else {
							Player p = (Player)sender;
							p.sendMessage(plugin.consoleColors(plugin.messages("disable-messages.message-one")).replace("%version%", plugin.version));
							p.sendMessage(plugin.consoleColors(plugin.messages("disable-messages.message-two")).replace("%author%", "-ComplexCode"));
						}
					} else if(args[0].equalsIgnoreCase("version")) {
						if(!(sender instanceof Player)) {
							plugin.b.sendMessage(plugin.consoleColors(plugin.messages("version")).replace("%version%", plugin.version));
						} else {
							Player p = (Player)sender;
							p.sendMessage(plugin.consoleColors(plugin.messages("version")).replace("%version%", plugin.version));
						}
					} else {
						if(!(sender instanceof Player)) {
							plugin.b.sendMessage(plugin.consoleColors(plugin.messages("no-command-exist")));
						} else {
							Player p = (Player)sender;
							p.sendMessage(plugin.consoleColors(plugin.messages("no-command-exist")));
						}
					}
				} else {
					if(!(sender instanceof Player)) {
						plugin.b.sendMessage(plugin.consoleColors(plugin.messages("no-command-exist")));
					} else {
						Player p = (Player)sender;
						p.sendMessage(plugin.consoleColors(plugin.messages("no-command-exist")));
					}
				}
			} else {
				Player p = (Player)sender;
				p.sendMessage(plugin.consoleColors(plugin.messages("no-permissions")));
			}
		}
		return false;
	}
}