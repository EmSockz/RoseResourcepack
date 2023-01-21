package me.emsockz.roserp.file.config;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

import me.emsockz.roserp.Main;

public class PluginCFG {

	public static String LANG;
	public static String IP;
	public static Integer PORT;
	public static String ZIP_ARCHIVE_NAME;
	public static String RESOURCEPACK_URL;
	public static Boolean ENABLE_HASH;
	
	public static List<String> IGNORE_FILES;
	
	public static void reload() {
		FileConfiguration cfg = Main.getInstance().getConfig();
		
		ENABLE_HASH = cfg.getBoolean("enableHash");
		LANG = cfg.getString("lang");
		PORT = cfg.getInt("port");
		ZIP_ARCHIVE_NAME = cfg.getString("zipArchiveName").replace(".zip", "");
		IP = cfg.getString("serverIP");
		RESOURCEPACK_URL = "http://"+IP+":"+PORT;
		
		List<String> igF = new ArrayList<>(); cfg.getStringList("ignoreFiles").forEach((s) -> igF.add(s.replace(".", "")));
		IGNORE_FILES = igF;
	}
}
