package com.complexcode.hpl2plus.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class Command_hplus_completer implements TabCompleter {
	public List<String> onTabComplete(CommandSender sender, Command command, String string, String[] args) {
		if(sender instanceof Player && args.length == 1) {
			Player p = (Player)sender;
		    List<String> commands = new ArrayList<>();
		    
		    if(p.hasPermission("hideplugins2.hplus") || p.isOp()) {
		    	commands.add("help");
		    	commands.add("dev");
		    	commands.add("version");
		    	commands.add("reload");
		    	commands.add("disable");
		    } 
		    return commands;
		}
		return null;
	}
}
