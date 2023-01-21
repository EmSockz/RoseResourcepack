package me.emsockz.roserp.commands.sub;

import me.emsockz.roserp.commands.SubCommandModel;
import me.emsockz.roserp.file.config.MessagesCFG;

public class HelpCMD extends SubCommandModel {
	
    public HelpCMD() {
        this.setPlayerCommand(false);
    }
    
	@Override
	public boolean execute() {
		if (checkPermission("roserp.commands.help.admin", false))
			sendMessage(MessagesCFG.HELP_COMMAND_ADMIN);
		else if (checkPermission("roserp.commands.help", false)) 
			sendMessage(MessagesCFG.HELP_COMMAND);	
		else
			sendMessage(MessagesCFG.NO_PERMISSIONS);
		
		return true;
	}
}
