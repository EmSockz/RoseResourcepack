package me.emsockz.roserp.infrastructure;

import me.emsockz.roserp.RoseRP;
import me.emsockz.roserp.commands.sub.TextureCMD;
import me.emsockz.roserp.packs.Pack;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Packer {
    public Packer() {
        super();
    }

    public static void apply(Player player, Pack pack) {
        if (pack.isEnableHash()) {
            player.setResourcePack(pack.getRpURL(), pack.getHash());
        } else {
            player.setResourcePack(pack.getRpURL());
        }
    }

    public static void packFiles(Pack obj) {
        TextureCMD.enable = false;

        try {
            File resourcePack = new File(RoseRP.getInstance().getDataFolder(), "resourcepacks/" + obj.getName());
            File pack = new File(RoseRP.getInstance().getDataFolder(), "resourcepacks/" + obj.getName() + "/" + obj.getZipArchiveName() + ".zip");
            FileOutputStream fos = new FileOutputStream(pack);
            ZipOutputStream zos = new ZipOutputStream(fos);
            addFolderToZip(resourcePack, resourcePack.getName(), zos, obj);
            zos.close();
            fos.close();

            TextureCMD.enable = true;
            File path = new File(RoseRP.getInstance().getDataFolder(), "resourcepacks/" + obj.getName() + "/" + obj.getZipArchiveName() + ".zip");

            obj.setPath(path);
            obj.setHash(getSHA1Checksum(path.getPath()));
        } catch (IOException exception) {
            exception.printStackTrace();
            TextureCMD.enable = true;
        }

    }


    private static void addFolderToZip(final File folder, final String baseName, final ZipOutputStream zos, final Pack obj) {
        final File[] files = folder.listFiles();
        final Set<String> ignoreFiles = RoseRP.getInstance().getPluginConfig().IGNORE_FILES;

        try {
            File[] array;
            for (int length2 = (array = files).length, i = 0; i < length2; ++i) {
                final File file = array[i];

                if (!ignoreFiles.contains(com.google.common.io.Files.getFileExtension(file.getName()))) {
                    if (file.isDirectory()) {
                        addFolderToZip(file, baseName.replace(obj.getName() + "/", "") + "/" + file.getName().replace(obj.getName(), ""), zos, obj);
                    }

                    else {
                        final byte[] buffer = new byte[1024];
                        final FileInputStream fis = new FileInputStream(file);
                        zos.putNextEntry(new ZipEntry((baseName.equalsIgnoreCase(obj.getName()) ? "" : baseName + "/") + file.getName()));
                        int length;
                        while ((length = fis.read(buffer)) > 0) {
                            zos.write(buffer, 0, length);
                        }
                        zos.closeEntry();
                        fis.close();
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //get sha1 checksum
    public static byte[] getSHA1Checksum(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
            byte[] data = new byte[1024];
            int read = 0;
            while ((read = fis.read(data)) != -1) {
                sha1.update(data, 0, read);
            }
            return sha1.digest();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
