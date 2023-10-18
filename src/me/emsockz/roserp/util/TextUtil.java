package me.emsockz.roserp.util;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.emsockz.roserp.Main;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class TextUtil {

	public static String parseComponent(Component string) { 
		return MiniMessage.miniMessage().serialize(string);
	}

	public static Component parseString(String string) {
		return MiniMessage.miniMessage().deserialize(parseLegacyString(string));
	}

	public static Component getStringMessage(String string) {
		return MiniMessage.miniMessage().deserialize(parseLegacyString(Main.getMCFG().getString(string)));
	}

	public static List<Component> getStringListMessage(String string) {
		List<String> text = Main.getMCFG().getStringList(string);		
		List<String> text2 = new ArrayList<>();
		List<Component> result = new ArrayList<>(); 

		for (String s : text) {
			text2.add(parseLegacyString(s));
		}
		
		for (String s : text2) {
			result.add(MiniMessage.miniMessage().deserialize(s));
		}

		return result;
	}

	public static List<Component> parseStringList(List<String> string) {
		List<String> text = new ArrayList<>();
		List<Component> result = new ArrayList<>();
		
		for (String s : string) {
			text.add(parseLegacyString(s));
		}
		
		for (String s : text) {
			result.add(MiniMessage.miniMessage().deserialize(s));
		}
		
		return result;
	}

	public static String parseLegacyString(String string) {
		return string.replace("&", "§").replace("§r", "<reset>").replace("§0", "<black>").replace("§1", "<dark_blue>").replace("§2", "<dark_green>")
	    		.replace("§3", "<dark_aqua>").replace("§4", "<dark_red>").replace("§5", "<dark_purple>").replace("§6", "<gold>").replace("§7", "<gray>")
	    		.replace("§8", "<dark_gray>").replace("§9", "<blue>").replace("§a", "<green>").replace("§c", "<red>").replace("§d", "<light_purple>")
	    		.replace("§b", "<aqua>").replace("§f", "<white>").replace("§l", "<bold>").replace("§n", "<underlined>").replace("§o", "<italic>")
	    		.replace("§m", "<strikethrough>").replace("§k", "<obfuscated>");		
	}
	
	public static Component replaceText(Component text, HashMap<String, String> replaced) {
		String str = MiniMessage.miniMessage().serialize(text);
		
		for (String k : replaced.keySet()) {
			str = str.replace(k, replaced.get(k));
		}
		
		return MiniMessage.miniMessage().deserialize(str);
	}
}
