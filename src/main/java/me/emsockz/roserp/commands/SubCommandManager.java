package me.emsockz.roserp.commands;

import me.emsockz.roserp.RoseRP;
import me.emsockz.roserp.commands.sub.HelpCMD;
import me.emsockz.roserp.commands.sub.ReloadCMD;
import me.emsockz.roserp.commands.sub.TextureCMD;
import me.emsockz.roserp.commands.sub.ZipCMD;
import me.emsockz.roserp.file.config.MessagesCFG;
import net.kyori.adventure.audience.Audience;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class SubCommandManager implements CommandExecutor {
   public static HashMap subcommands = new HashMap();

   public SubCommandManager() {
      subcommands.put("reload", new ReloadCMD());
      subcommands.put("help", new HelpCMD());
      subcommands.put("zip", new ZipCMD());
      subcommands.put("texture", new TextureCMD());
   }

   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      if (args.length == 0) {
         ((SubCommandModel)subcommands.get("help")).onExecute(sender, command, label, args);
         return true;
      } else {
         String subcommand = args[0].toLowerCase();
         if (subcommands.get(subcommand) == null) {
            Audience aud = sender instanceof Player ? RoseRP.getInstance().getAdventure().player((Player)sender) : RoseRP.getInstance().getAdventure().console();
            MessagesCFG.COMMAND_DOES_NOT_EXIST.sendMessage(aud);
            return true;
         } else {
            ((SubCommandModel)subcommands.get(subcommand)).onExecute(sender, command, label, args);
            return true;
         }
      }
   }
}
