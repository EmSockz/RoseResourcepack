package me.emsockz.roserp.packs;

import me.emsockz.roserp.RoseRP;

import java.io.File;

public class Pack {
   String name;
   String rpURL;
   String zipArchiveName;
   boolean enableHash;

   File path;
   byte[] hash;

   public Pack(String name, boolean enableHash, String zipArchiveName) {
      this.name = name;
      this.enableHash = enableHash;
      this.rpURL = RoseRP.getPluginConfig().HOST_URL + "/" + zipArchiveName + ".zip";
      this.zipArchiveName = zipArchiveName;
   }

   public String getName() {
      return this.name;
   }

   public byte[] getHash() {
      return hash;
   }


   public File getPath() {
      return path;
   }

   public String getZip() {
      return RoseRP.getInstance().getDataFolder().getPath() + File.separator + "resourcepacks" + File.separator + this.name + File.separator + this.zipArchiveName + ".zip";
   }

   public String getRpURL() {
      return this.rpURL;
   }

   public boolean isEnableHash() {
      return this.enableHash;
   }

   public String getZipArchiveName() {
      return this.zipArchiveName;
   }

   public void setHash(byte[] hash) {
      this.hash = hash;
   }


   public void setPath(File path) {
      this.path = path;
   }

   public String toString() {
      return "Pack [name=" + this.name + ", rpURL=" + this.rpURL + ", zipArchiveName=" + this.zipArchiveName + ", enableHash=" + this.enableHash + "]";
   }
}
