package me.emsockz.roserp;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import me.emsockz.roserp.commands.SubCommandManager;
import me.emsockz.roserp.commands.TabCommandManager;
import me.emsockz.roserp.file.MessagesFile;
import me.emsockz.roserp.file.config.MessagesCFG;
import me.emsockz.roserp.file.config.PluginCFG;
import me.emsockz.roserp.infrastructure.Hosting;
import me.emsockz.roserp.infrastructure.Packer;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;

public class Main extends JavaPlugin {

	private static Main instance = null;
	private static Hosting host = null;
	private static MessagesFile messages = null;
	private static BukkitAudiences adventure;
	
	private static Logger log = Logger.getLogger("Minecraft");
	
    
	@Override
	public void onEnable() {
		instance = this;
		adventure = BukkitAudiences.create(instance);
		loadMessagesFiles();
		loadResourcepackFiles();
		
		saveDefaultConfig();
		PluginCFG.LANG = getConfig().getString("lang");
		messages = new MessagesFile();
		MessagesCFG.refreshAll();
		PluginCFG.reload();
		
        PluginCommand pluginCommand = instance.getCommand("roserp");
        	pluginCommand.setExecutor(new SubCommandManager());
        	pluginCommand.setTabCompleter(new TabCommandManager());
    	 
        	
        host = new Hosting();
        	
		if (!new File(instance.getDataFolder(), "resourcepack/").exists()) 
			new File(instance.getDataFolder(), "resourcepack/").mkdirs();
        if (!new File(instance.getDataFolder(), "files/"+PluginCFG.ZIP_ARCHIVE_NAME+".zip").exists())
        	Packer.packFiles(null);
        
	}
	
	
	@Override
	public void onDisable() {
		if (adventure != null) {
			adventure.close();
			adventure = null;
		}
		
		host.stop();
	}
	
	public void disablePlugin() {
		if (adventure != null) {
			adventure.close();
			adventure = null;
		}
		host.stop();
		instance.setEnabled(false);
	}
	
	public void loadMessagesFiles() {
		List<String> langs = List.of("en", "ru");;
		
		langs.forEach((lang) -> {
			if (!new File(instance.getDataFolder(), "lang/messages_"+lang+".yml").exists()) 
				instance.saveResource("lang/messages_"+lang+".yml", false);
		});
	}
	
	public void loadResourcepackFiles() {
		if (new File(instance.getDataFolder(), "resourcepack/").exists()) return;
		List<String> files = List.of(
				"resourcepack/", 
				"resourcepack/pack.mcmeta");
		
		files.forEach((f) -> {
			if (!new File(instance.getDataFolder(), f).exists()) 
				instance.saveResource(f, false);
		});
	}
	
    public static void logInfo(String text){
    	log.info(text);
    }
    
    public static void logWarning(String text){
    	log.warning(text);
    }
    
    public static void logSevere(String text){
    	log.severe(text);
    }
    
    public void schedulerRun(Runnable task) {
        Bukkit.getScheduler().runTask(instance, task);
    }
    
    public static Main getInstance() {
    	return instance;
    }
    
    public static Hosting getHosting() {
    	return host;
    }
    
    public static MessagesFile getMessages() {
    	return messages;
    }
    
	public static BukkitAudiences getAdventure() {
		if (adventure == null) {
			throw new IllegalStateException("Tried to acces Adventure when the plugin was disables!");
		}
		
		return adventure;
	}
    
	public static FileConfiguration getMCFG() {
		return messages.getConfig();
	}
	
	public void reloadPlugin() {
		instance.reloadConfig();
		PluginCFG.LANG = instance.getConfig().getString("lang");
		messages.reload();
		MessagesCFG.refreshAll();
		PluginCFG.reload();
	}
}
