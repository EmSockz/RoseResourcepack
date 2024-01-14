package me.emsockz.roserp.commands.sub;

import me.emsockz.roserp.commands.SubCommandModel;
import me.emsockz.roserp.file.config.MessagesCFG;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ResetCMD extends SubCommandModel {
   public ResetCMD() {
      super();
      this.setPlayerCommand(false);
   }

   public boolean execute() {
      if (!this.checkPermission("roserp.commands.reset", true)) {
         return true;
      } else {
         if (this.args.length == 1) {
            if (this.player == null) {
               this.sendMessage(MessagesCFG.NOT_FOR_CONSOLE);
               return true;
            }

            this.player.setResourcePack("https://google.com");
         } else {
            if (!this.checkPermission("roserp.commands.reset.other", true)) {
               return true;
            }

            if (Bukkit.getPlayer(this.args[1]) == null) {
               this.sendMessage(MessagesCFG.PLAYER_NOT_FOUND);
               return true;
            }

            Player p = Bukkit.getPlayer(this.args[1]);
            p.setResourcePack("https://google.com");
         }

         return true;
      }
   }
}
