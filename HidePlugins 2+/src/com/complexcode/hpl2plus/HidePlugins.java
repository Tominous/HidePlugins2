package com.complexcode.hpl2plus;

import com.complexcode.hpl2plus.commands.Command_hplus;
import com.complexcode.hpl2plus.events.CommandsBlocker;
import com.complexcode.hpl2plus.events.JoinMessageUpdate;
import com.complexcode.hpl2plus.events.TabBlocker;
import com.complexcode.hpl2plus.metrics.Metrics;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class HidePlugins extends JavaPlugin {
	private FileConfiguration lang = null;
	private File langFile;
	private FileConfiguration players = null;
	private File playersFile;
  
	PluginDescriptionFile pdffile = getDescription();
	
	public ConsoleCommandSender b = Bukkit.getConsoleSender();
	public FileConfiguration config = getConfig();
	public String latestVersion;
	public String rutaConfig;
	public String name = "&e&lHidePlugins>&r";
	public String prefix = config.getString("config.prefix.prefix");
	public String version = pdffile.getVersion();
	public String language = config.getString("config.lang");
  
	public void onEnable() {
		registerCommands();
		registerPlayers();
		registerConfig();
		checkedUpdate();
		
		File langd = new File(getDataFolder(), "languages.yml");
		if(!langd.exists()) {
			saveResource("languages.yml", false);
		}
		
		ArrayList<String> langlist = new ArrayList<>();
		for(String key : getLang().getConfigurationSection("languages.").getKeys(false)) {
			if(!langlist.contains(key)) {
				langlist.add(key); 
			} 
		}
		
		if(language.isEmpty() || !langlist.contains(language)) {
			b.sendMessage(consoleColors(messages("lang-error")));
			getPluginLoader().disablePlugin(this);
		} else {
			Metrics metrics = new Metrics(this, 5432);
		    metrics.addCustomChart(new Metrics.SimplePie("used_language", new Callable<String>() {
		        @Override
		        public String call() throws Exception {
		            return config.getString("config.lang");
		        }
		    }));

			b.sendMessage(consoleColors(messages("enable-messages.message-one")).replace("%version%", version));
			b.sendMessage(consoleColors(messages("enable-messages.message-two")).replace("%author%", "-ComplexCode"));
			b.sendMessage("");
			
			registerEvents();
		} 
	}
  
	public String colors(String s) {
		if(config.getBoolean("config.prefix.enable")) {
			return ChatColor.translateAlternateColorCodes('&', prefix + " " + s);
		} else {
			return ChatColor.translateAlternateColorCodes('&', s);
		}
	}
	
	public String consoleColors(String s) {
		return ChatColor.translateAlternateColorCodes('&', name + " " + s);
	}
  
	public String messages(String s) {
		return getLang().getString("languages." + language + "." + s);
	}
  
	public void registerCommands() {
		getCommand("hplus").setExecutor(new Command_hplus(this));
	}
  
	public void registerEvents() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new CommandsBlocker(this), this);
		pm.registerEvents(new TabBlocker(this), this);
		pm.registerEvents(new JoinMessageUpdate(this), this);
	}
  
	public FileConfiguration getLang() {
		if(lang == null) {
			reloadLang(); 
		}
		return lang;
	}
  
	public void reloadLang() {
		if(lang == null) {
			langFile = new File(getDataFolder(), "languages.yml"); 
		}
		
		lang = (FileConfiguration)YamlConfiguration.loadConfiguration(this.langFile);
		try {
			Reader defConfigStream = new InputStreamReader(getResource("languages.yml"), "UTF8");
			if(defConfigStream != null) {
				YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
				lang.setDefaults(defConfig);
			} 
		} catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
	}
	
	public void registerConfig() {
		File config = new File(getDataFolder(), "config.yml");
		rutaConfig = config.getPath();
		if(!config.exists()) {
			getConfig().options().copyDefaults(true);
			saveDefaultConfig();
		} 
	}
  
	public FileConfiguration getPlayers() {
		if(players == null) {
			reloadPlayers(); 
		}
		return players;
	}
  
	public void reloadPlayers() {
		if(players == null) {
			playersFile = new File(getDataFolder(), "players.yml"); 
		}
		
    	players = YamlConfiguration.loadConfiguration(playersFile);
    	try {
    		Reader defConfigStream = new InputStreamReader(getResource("players.yml"), "UTF8");
    		if(defConfigStream != null) {
    			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
    			players.setDefaults(defConfig);
    		} 
    	} catch(UnsupportedEncodingException e) {
    		e.printStackTrace();
    	} 
	}
  
	public void savePlayers() {
		try {
			players.save(playersFile);
		} catch(IOException e) {
			e.printStackTrace();
		} 
	}
  
	public void registerPlayers() {
    	playersFile = new File(getDataFolder(), "players.yml");
    	if(!playersFile.exists()) {
    		getPlayers().options().copyDefaults(true);
    		savePlayers();
    	} 
	}
  
	public void checkedUpdate() {
		if(getConfig().getBoolean("config.updates")) {
			try {
				HttpURLConnection con = (HttpURLConnection)(new URL("https://api.spigotmc.org/legacy/update.php?resource=25317")).openConnection();
				int timed_out = 1250;
				con.setConnectTimeout(timed_out);
				con.setReadTimeout(timed_out);
				latestVersion = (new BufferedReader(new InputStreamReader(con.getInputStream()))).readLine();
				
				if(latestVersion.length() <= 7 && !version.equals(latestVersion)) {
					b.sendMessage(consoleColors(messages("update-messages.message-one")).replace("%newversion%", latestVersion));
					b.sendMessage(consoleColors(messages("update-messages.message-two")).replace("%link%", "https://www.spigotmc.org/resources/25317/"));
					b.sendMessage("");
				} 
			} catch(Exception ex) {
				b.sendMessage(consoleColors(messages("update-messages.error-message")));
			}  
		}
	}
}