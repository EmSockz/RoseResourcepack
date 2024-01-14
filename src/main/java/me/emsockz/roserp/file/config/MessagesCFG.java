package me.emsockz.roserp.file.config;

import me.emsockz.roserp.RoseRP;
import me.emsockz.roserp.util.StringUtil;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;

import java.util.List;

public enum MessagesCFG {
   RELOAD_PLUGIN("ReloadPlugin"),
   NO_PERMISSIONS("NoPerm"),
   NOT_FOR_CONSOLE("NotForConsole"),
   HELP_COMMAND("HelpCommand"),
   HELP_COMMAND_ADMIN("HelpCommandAdmin"),
   RP_SUCCESSFULLY_PACKED("RPSuccessfullyPacked"),
   PLAYER_NOT_FOUND("PlayerNotFound"),
   PACK_NOT_FOUND("PackNotFound"),
   COMMAND_DOES_NOT_EXIST("CommandNotFound");

   String path;
   List<Component> text;

   MessagesCFG(String path) {
      this.path = path;
      this.text = StringUtil.getMessage(path);
   }

   public String getPath() {
      return this.path;
   }

   public List<Component> getText() {
      return this.text;
   }

   public void refresh() {
      text = StringUtil.getMessage(path);
   }

   public void sendMessage(Audience audience) {
      for (Component component : text) {
         audience.sendMessage(component);
      }
   }

   public void sendMessage(CommandSender audience, String... replaces) {
      for (Component component : text) {
         RoseRP.getAdventure().sender(audience).sendMessage(StringUtil.replace(component, replaces));
      }
   }

   public static void refreshAll() {
      for (MessagesCFG e : values()) e.refresh();
   }
}
