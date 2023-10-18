package me.emsockz.roserp.commands.sub;

import me.emsockz.roserp.Main;
import me.emsockz.roserp.commands.SubCommandModel;
import me.emsockz.roserp.file.config.MessagesCFG;

public class ReloadCMD extends SubCommandModel {
	
    public ReloadCMD() {
        this.setPlayerCommand(false);
    }

	@Override
	public boolean execute() {
		if (!checkPermission("roserp.commands.reload", true)) return true; 
		Main.getInstance().reloadPlugin();
		sendMessage(MessagesCFG.RELOAD_PLUGIN);
		return true;
	}
}
