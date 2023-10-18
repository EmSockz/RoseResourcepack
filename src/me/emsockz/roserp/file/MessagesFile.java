package me.emsockz.roserp.file;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.emsockz.roserp.Main;
import me.emsockz.roserp.file.config.PluginCFG;

public class MessagesFile {
	
	private final String path = "lang/messages_"+PluginCFG.LANG+".yml";
	private File file;
	private FileConfiguration config;

	public MessagesFile() {
		if (!new File(Main.getInstance().getDataFolder(), path).exists()) {
			Main.getInstance().saveResource(path, false);
			
			file = new File(Main.getInstance().getDataFolder(), path);
			config = YamlConfiguration.loadConfiguration(file);
		} else {
			file = new File(Main.getInstance().getDataFolder(), path);
			config = YamlConfiguration.loadConfiguration(file);
		}
	}
	

	public void save() {
		file = new File(Main.getInstance().getDataFolder(), path);
		
		try {
			config.save(file);
		} catch (IOException e) {
			throw new RuntimeException("Failed to save file "+path, e);
		}
		
		config = YamlConfiguration.loadConfiguration(file);
	}

	public void reload() {
		file = new File(Main.getInstance().getDataFolder(), path);
		config = YamlConfiguration.loadConfiguration(file);
	}

	public FileConfiguration getConfig() {
		return config;
	}
}
