package me.emsockz.roserp.commands.sub;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.emsockz.roserp.commands.SubCommandModel;
import me.emsockz.roserp.file.config.MessagesCFG;

public class ResetCMD extends SubCommandModel {
	
    public ResetCMD() {
        this.setPlayerCommand(false);
    }

	@Override
	public boolean execute() {
		if (!checkPermission("roserp.commands.reset", true)) return true; 
		
		if (args.length == 1) {
			if (player == null) {
				sendMessage(MessagesCFG.NOT_FOR_CONSOLE);
				return true;
			}
			
			player.setResourcePack("https://google.com");
		}
		
		else {
			if (!checkPermission("roserp.commands.reset.other", true)) return true; 
			if (Bukkit.getPlayer(args[1]) == null) {
				sendMessage(MessagesCFG.PLAYER_NOT_FOUND);
				return true;
			}
			
			Player p = Bukkit.getPlayer(args[1]);
			p.setResourcePack("https://google.com");
		}
		return true;
	}
}
