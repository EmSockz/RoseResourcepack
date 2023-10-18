package me.emsockz.roserp.infrastructure;

import java.io.File;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import me.emsockz.roserp.Main;
import me.emsockz.roserp.file.config.PluginCFG;

public class Hosting {
	
    private HttpServer httpServer;

    public Hosting() {
        start();
    }

    public boolean start() {
        int port = PluginCFG.PORT;
        
        try {
            httpServer = Vertx.vertx().createHttpServer();
            httpServer.requestHandler(httpServerRequest -> httpServerRequest.response().sendFile(getFileLoc()));
            httpServer.listen(port);
        } catch(Exception ex){
            Main.logSevere("Unable to bind to port. Please assign the plugin to a different port!");
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public void stop() {
        httpServer.close();
    }
    
    private String getFileLoc() {
    	return Main.getInstance().getDataFolder().getPath()+File.separator+"resourcepack"+File.separator+PluginCFG.ZIP_ARCHIVE_NAME.replace(".zip", "")+".zip";
    }
}
