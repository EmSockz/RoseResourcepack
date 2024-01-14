package me.emsockz.roserp;

import me.emsockz.roserp.commands.SubCommandManager;
import me.emsockz.roserp.commands.TabCommandManager;
import me.emsockz.roserp.file.MessagesFile;
import me.emsockz.roserp.file.config.MessagesCFG;
import me.emsockz.roserp.file.config.PluginCFG;
import me.emsockz.roserp.infrastructure.Hosting;
import me.emsockz.roserp.infrastructure.Packer;
import me.emsockz.roserp.packs.Pack;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

public class RoseRP extends JavaPlugin {

    // *****************************************************************************************************************
    // Logger
    private static final Logger logger = Logger.getLogger("Minecraft");

    // *****************************************************************************************************************
    // Fields

    private static RoseRP instance = null;
    private static Hosting host = null;

    public final HashMap<String, Pack> packs = new HashMap<>();

    private BukkitAudiences adventure;

    private PluginCFG pluginConfig;

    private MessagesFile messages = null;


    public void onEnable() {
        instance = this;
        adventure = BukkitAudiences.create(instance);
        this.loadMessagesFiles();
        this.saveDefaultConfig();
        if (instance.getConfig().getBoolean("loadDefaultFiles", false)) {
            loadDefaultFiles();
        }

        // load files
        pluginConfig = new PluginCFG();
        messages = new MessagesFile();
        MessagesCFG.refreshAll();

        // register command
        PluginCommand pluginCommand = instance.getCommand("roserp");
        pluginCommand.setExecutor(new SubCommandManager());
        pluginCommand.setTabCompleter(new TabCommandManager());

        // load resourcepack
        this.loadResourcepacks();

        // create web host
        host = new Hosting();
    }

    public void onDisable() {
        if (adventure != null) {
            adventure.close();
            adventure = null;
        }

        getServer().getScheduler().cancelTasks(instance);

        host.stop();
    }

    public void loadMessagesFiles() {
        for (String lang : Set.of("en", "ru")) {
            if (!(new File(instance.getDataFolder(), "lang/messages_" + lang + ".yml")).exists()) {
                instance.saveResource("lang/messages_" + lang + ".yml", false);
            }
        }
    }

    public static void loadDefaultFiles() {
        instance.saveResource("resourcepacks/", false);
        instance.saveResource("resourcepacks/low_quality/", false);
        instance.saveResource("resourcepacks/low_quality/pack.mcmeta", false);
        instance.saveResource("resourcepacks/main/", false);
        instance.saveResource("resourcepacks/main/pack.mcmeta", false);
        instance.getConfig().set("loadDefaultFiles", false);
        instance.saveConfig();
        instance.reloadConfig();
    }

    public void loadResourcepacks() {
        FileConfiguration cfg = instance.getConfig();
        cfg.getConfigurationSection("packs").getKeys(false).forEach((pack) -> {
                    packs.put(pack, new Pack(pack, cfg.getBoolean("packs." + pack + ".enableHash"), cfg.getString("packs." + pack + ".zipArchiveName")));
                }
        );

        for (Map.Entry<String, Pack> entry : packs.entrySet()) {
            String name = entry.getKey();
            Pack pack = entry.getValue();
            File path = new File(instance.getDataFolder(), "resourcepacks/" + name + "/" + pack.getZipArchiveName() + ".zip");

            if (path.exists()) {
                pack.setPath(path);
                pack.setHash(Packer.getSHA1Checksum(path.getPath()));
            }

            else {
                CompletableFuture.runAsync(() -> {
                    Packer.packFiles(pack);
                }).whenComplete((result, ex) -> {
                    MessagesCFG.RP_SUCCESSFULLY_PACKED.sendMessage(Bukkit.getConsoleSender(), "{pack}", pack.getName());
                });
            }
        }
    }

    public static void logInfo(String text) {
        logger.info(text);
    }

    public static void logWarning(String text) {
        logger.warning(text);
    }

    public static void logSevere(String text) {
        logger.severe(text);
    }

    public static RoseRP getInstance() {
        return instance;
    }

    public static Hosting getHosting() {
        return host;
    }

    public static PluginCFG getPluginConfig() {
        return instance.pluginConfig;
    }

    public static MessagesFile getMessages() {
        return instance.messages;
    }

    public static BukkitAudiences getAdventure() {
        if (instance.adventure == null) {
            throw new IllegalStateException("Tried to acces Adventure when the plugin was disables!");
        } else {
            return instance.adventure;
        }
    }

    public static Pack getPack(String name) {
        return instance.packs.getOrDefault(name, null);
    }

    public void reloadPlugin() {
        instance.reloadConfig();
        messages.reload();

        // reload config && messages.yml
        pluginConfig = new PluginCFG();
        MessagesCFG.refreshAll();

        // restart host
        host.stop();
        host = new Hosting();
    }
}
