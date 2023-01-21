package me.emsockz.roserp.file.config;

import java.util.List;

import me.emsockz.roserp.util.TextUtil;
import net.kyori.adventure.text.Component;

public enum MessagesCFG {

	RELOAD_PLUGIN("ReloadPlugin", 0),
	NO_PERMISSIONS("NoPerm", 0),
	NOT_FOR_CONSOLE("NotForConsole", 0),
	HELP_COMMAND("HelpCommand", 1),
	HELP_COMMAND_ADMIN("HelpCommandAdmin", 1),
	RP_SUCCESSFULLY_PACKED("RPSuccessfullyPacked", 0),
	PLAYER_NOT_FOUND("PlayerNotFound", 0),
	COMMAND_DOES_NOT_EXIST("CommandNotFound", 0);
	 
	String path;
	Component textString;
	List<Component> textList;
	int id;
	
	MessagesCFG(String text, int id) {
		this.id = id;
		this.path = text;
		
		if (id == 0) {
			this.textString = TextUtil.getStringMessage(text);
		}
		else this.textList = TextUtil.getStringListMessage(text);
		
	}
	
	public int getID() {
		return this.id;
	}
	
	public String getPath() {
		return this.path;
	}
	
	public Component getString() {
		return this.textString;
	}
	
	public List<Component> getStringList() {
		return this.textList;
	}
	
	public void refresh() {
		if (this.id == 0)
			this.textString = TextUtil.getStringMessage(this.path);
		else
			this.textList = TextUtil.getStringListMessage(this.path);
	}

	public static void refreshAll() {
		for (MessagesCFG e : values()) e.refresh();
	}
}
