package me.emsockz.roserp.commands.sub;

import me.emsockz.roserp.commands.SubCommandModel;
import me.emsockz.roserp.infrastructure.Packer;

public class ZipCMD extends SubCommandModel {
	
    public ZipCMD() {
        this.setPlayerCommand(false);
    }

	@Override
	public boolean execute() {
		if (!checkPermission("roserp.commands.zip", true)) return true; 
		Packer.packFiles(sender);
		return true;
	}
}
