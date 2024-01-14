package me.emsockz.roserp.infrastructure;

import me.emsockz.roserp.RoseRP;
import me.emsockz.roserp.packs.Pack;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Hosting {

    private BukkitTask task;

    private ServerSocket serverSocket;

    public boolean isRunning = true;


    public Hosting() {
        try {
            serverSocket = new ServerSocket(RoseRP.getPluginConfig().PORT);
            serverSocket.setSoTimeout(0);
        } catch (Exception e) {
            Bukkit.getLogger().info("Something went wrong hosting the server! " + e);
            return;
        }

        handleRequest();
    }

    public void handleRequest() {
        task = Bukkit.getScheduler().runTaskAsynchronously(RoseRP.getInstance(), () -> {
            try {
                Socket socket = serverSocket.accept();
                String response = "HTTP/1.1 200 OK\r\nContent-Type: application/zip\r\n\r\n";

                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String requestLine = reader.readLine(); // Читаем первую строку запроса (GET /filename.zip HTTP/1.1)
                if (requestLine != null) {
                    String[] split = requestLine.split(" ");

                    if (split.length >= 2) {
                        String url = split[1];
                        String[] urlParts = url.split("/");

                        if (urlParts.length >= 2) {
                            String name = urlParts[1].replace(".zip", "");
                            // Получите объект Pack, замените на ваш способ получения объекта Pack
                            Pack pack = RoseRP.getPack(name);

                            if (pack != null) {
                                OutputStream outputStream = socket.getOutputStream();
                                outputStream.write(response.getBytes(StandardCharsets.UTF_8));

                                try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(pack.getPath()));
                                     BufferedOutputStream bos = new BufferedOutputStream(outputStream)) {

                                    byte[] buffer = new byte[8192]; // Размер буфера может быть изменен
                                    int bytesRead;
                                    while ((bytesRead = bis.read(buffer)) != -1) {
                                        bos.write(buffer, 0, bytesRead);
                                    }
                                }
                            } else {
                                String notFoundResponse = "HTTP/1.1 404 Not Found\r\n\r\n";
                                socket.getOutputStream().write(notFoundResponse.getBytes());
                            }
                        }
                    }
                }
            } catch (Exception ignored) {
                ignored.printStackTrace(); // Рекомендуется логировать исключения для дальнейшего анализа
            }

            if (isRunning) {
                handleRequest();
            }
        });
    }




    public void stop() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
