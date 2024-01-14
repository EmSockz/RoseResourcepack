package me.emsockz.roserp.commands;

import me.emsockz.roserp.RoseRP;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TabCommandManager implements TabCompleter {

   public TabCommandManager() {
      super();
   }

   public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
      if (args.length == 1) {
         if (sender.hasPermission("roserp.help.admin")) {
            return listSubCommandAdmin;
         } else {
            return sender.hasPermission("roserp.help") ? listSubCommandUser : null;
         }
      } else if (args.length == 2 && args[1].equalsIgnoreCase("texture")) {
         return new ArrayList<>(RoseRP.getInstance().packs.keySet());
      } else if (args.length == 2 && args[1].equalsIgnoreCase("zip")) {
         List<String> arr = new ArrayList<>(List.of(Arrays.toString(RoseRP.getInstance().packs.keySet().toArray())));
         arr.add("all");
         return arr;
      }

      return  null;
   }

   private static final List<String> listSubCommandAdmin = List.of("help", "reload", "zip", "texture");
   private static final List<String> listSubCommandUser = List.of("help", "texture");
}
