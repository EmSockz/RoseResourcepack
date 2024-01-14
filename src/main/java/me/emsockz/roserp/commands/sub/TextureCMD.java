package me.emsockz.roserp.commands.sub;

import me.emsockz.roserp.RoseRP;
import me.emsockz.roserp.commands.SubCommandModel;
import me.emsockz.roserp.file.config.MessagesCFG;
import me.emsockz.roserp.infrastructure.Packer;
import org.bukkit.Bukkit;

public class TextureCMD extends SubCommandModel {
   public static boolean enable = true;

   public TextureCMD() {
      super();
      this.setPlayerCommand(false);
   }

   public boolean execute() {
      if (!this.checkPermission("roserp.commands.texture", true)) {
         return true;
      } else if (!enable) {
         return true;
      } else if (this.args.length <= 1) {
         this.sendMessage(MessagesCFG.HELP_COMMAND_ADMIN);
         return true;
      } else if (this.args.length == 2) {
         if (RoseRP.getPack(this.args[1]) == null) {
            this.sendMessage(MessagesCFG.PACK_NOT_FOUND);
            return true;
         } else {
            Packer.apply(this.player, RoseRP.getPack(this.args[1]));
            return true;
         }
      } else if (this.args.length == 3) {
         if (!this.checkPermission("roserp.commands.texture.other", true)) {
            return true;
         } else if (RoseRP.getPack(this.args[1]) == null) {
            this.sendMessage(MessagesCFG.PACK_NOT_FOUND);
            return true;
         } else if (Bukkit.getPlayer(this.args[2]) == null) {
            this.sendMessage(MessagesCFG.PLAYER_NOT_FOUND);
            return true;
         } else {
            Packer.apply(Bukkit.getPlayer(this.args[2]), RoseRP.getPack(this.args[1]));
            return true;
         }
      } else {
         this.sendMessage(MessagesCFG.HELP_COMMAND_ADMIN);
         return true;
      }
   }
}
