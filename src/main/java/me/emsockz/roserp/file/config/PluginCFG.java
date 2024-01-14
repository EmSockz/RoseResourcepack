package me.emsockz.roserp.file.config;

import me.emsockz.roserp.RoseRP;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashSet;
import java.util.Set;

public class PluginCFG {
   public final String LANG;
   public final String IP;
   public final Integer PORT;
   public final String HOST_URL;
   public final Set<String> IGNORE_FILES;

   public PluginCFG() {
      FileConfiguration cfg = RoseRP.getInstance().getConfig();
      LANG = cfg.getString("lang");
      PORT = cfg.getInt("port");
      IP = cfg.getString("serverIP");
      HOST_URL = "http://" + IP + ":" + PORT;

      Set<String> igF = new HashSet<>();
      cfg.getStringList("ignoreFiles").forEach((s) -> {
         igF.add(s.replace(".",""));
      });

      IGNORE_FILES = igF;
   }
}
