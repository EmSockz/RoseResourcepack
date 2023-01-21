package me.emsockz.roserp.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class TabCommandManager implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 1) {
			if (sender.hasPermission("roserp.help.admin")) return listSubCommandAdmin;
			else if (sender.hasPermission("roserp.help")) return listSubCommandUser;
			else return null;
		}	
		
		return null;
	}
	
	
	private static ArrayList<String> listSubCommandAdmin = new ArrayList<>(List.of("help","reload", "zip", "texture"));
	private static ArrayList<String> listSubCommandUser = new ArrayList<>(List.of("help","texture"));
}
