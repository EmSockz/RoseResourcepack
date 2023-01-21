package me.emsockz.roserp.infrastructure;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.io.Files;

import java.io.IOException;
import me.emsockz.roserp.Main;
import me.emsockz.roserp.file.config.MessagesCFG;
import me.emsockz.roserp.file.config.PluginCFG;

public class Packer {
    
	public static void packFiles(CommandSender sender) {
        try {
            File resourcePack = new File(Main.getInstance().getDataFolder(), "resourcepack");
            File pack = new File(Main.getInstance().getDataFolder(), "resourcepack/"+PluginCFG.ZIP_ARCHIVE_NAME+".zip");
            FileOutputStream fos = new FileOutputStream(pack);
            ZipOutputStream zos = new ZipOutputStream(fos);
            addFolderToZip(resourcePack, resourcePack.getName(), zos);
            zos.close();
            fos.close();
            if (sender != null)
            	((sender instanceof Player) ? Main.getAdventure().player((Player)sender) : Main.getAdventure().console())
            	.sendMessage(MessagesCFG.RP_SUCCESSFULLY_PACKED.getString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addFolderToZip(File folder, String baseName, ZipOutputStream zos) {
        File[] files = folder.listFiles();
        try {
        	for (File file : files) {
        		if (PluginCFG.IGNORE_FILES.contains(Files.getFileExtension(file.getName()))) return;
        		
                if (file.isDirectory()) {
                    addFolderToZip(file, baseName.replace("resourcepack/", "")+"/"+file.getName().replace("resourcepack", ""), zos);
                } else {
                    byte[] buffer = new byte[1024];
                    FileInputStream fis = new FileInputStream(file);
                    zos.putNextEntry(new ZipEntry((baseName.equalsIgnoreCase("resourcepack") ? "" : baseName+"/") + file.getName()));
                    int length;
                    while ((length = fis.read(buffer)) > 0) {
                        zos.write(buffer, 0, length);
                    }
                    zos.closeEntry();
                    fis.close();
                }
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    //get sha1 checksum
    private static byte[] getSHA1Checksum(String filePath) {
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
    
    //load sha-1 checksum
    public static byte[] loadSHA1Checksum() {
    	return getSHA1Checksum(new File(Main.getInstance().getDataFolder(), "files/"+PluginCFG.ZIP_ARCHIVE_NAME+".zip").getPath());
    }
}
