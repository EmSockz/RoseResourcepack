package me.emsockz.roserp.commands.sub;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.emsockz.roserp.commands.SubCommandModel;
import me.emsockz.roserp.file.config.MessagesCFG;
import me.emsockz.roserp.file.config.PluginCFG;
import me.emsockz.roserp.infrastructure.Packer;
public class TextureCMD extends SubCommandModel {
	
    public TextureCMD() {
        this.setPlayerCommand(false);
    }

	@Override
	public boolean execute() {
		if (!checkPermission("roserp.commands.texture", true)) return true; 
	
		if (args.length == 1) {
			if (player == null) {
				sendMessage(MessagesCFG.NOT_FOR_CONSOLE);
				return true;
			}
			if (PluginCFG.ENABLE_HASH) player.setResourcePack(PluginCFG.RESOURCEPACK_URL, Packer.loadSHA1Checksum());
			else player.setResourcePack(PluginCFG.RESOURCEPACK_URL);
		}
		
		else {
			if (!checkPermission("roserp.commands.texture.other", true)) return true; 
			if (Bukkit.getPlayer(args[1]) == null) {
				sendMessage(MessagesCFG.PLAYER_NOT_FOUND);
				return true;
			}
			
			Player p = Bukkit.getPlayer(args[1]);
			if (PluginCFG.ENABLE_HASH) p.setResourcePack(PluginCFG.RESOURCEPACK_URL, Packer.loadSHA1Checksum());
			else p.setResourcePack(PluginCFG.RESOURCEPACK_URL);
		}
		return true;
	}
}
