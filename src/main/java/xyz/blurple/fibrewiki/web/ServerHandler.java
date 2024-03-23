/*
 * Copyright (c) 2024 Jonas_Jones, magistermaks, TheColorBlurple
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package xyz.blurple.fibrewiki.web;

import xyz.blurple.fibrewiki.config.ModConfigs;
import net.fabricmc.loader.api.FabricLoader;
import xyz.blurple.fibrewiki.util.VerboseLogger;

import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

import static xyz.blurple.fibrewiki.FibreWiki.LOGGER;
import static xyz.blurple.fibrewiki.config.ModConfigs.WEB_PORT;

public class ServerHandler implements Runnable {
    public static final Socket SOCKET = new Socket();
    private static final ServerHandler webServer = new ServerHandler();
    public static Thread webServerThread = new Thread(webServer);
    public static boolean isServerActive = true;

    public static void start() {
        webServerThread.setName("FibreWiki-Server");
        webServerThread.start();
        // check if webserver is running
        // if not, stop the webserver
        // runs a loop while the webserver thread is alive
        // makes requests to the webserver until it dies (while the thread is listening for requests, it can't die and only checks if the server is running after a request)
        // truly a hacky and awful way to do this, but it works
        // other contributors have tried to improve on this, but it got nowhere
        Thread serverthread = new Thread(() -> {
            while (webServerThread.isAlive()) {
                if (!isServerActive) {
                    sleep(2);
                    try {
                        // Create URL
                        URL url = new URL("http://localhost:" + WEB_PORT + "/");

                        // Open connection
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                        // Set request method
                        connection.setRequestMethod("GET");

                        // Set connection timeout (milliseconds)
                        connection.setConnectTimeout(1000);

                        // Set read timeout (milliseconds)
                        connection.setReadTimeout(1000);

                        // Get response code (optional, but useful for debugging)
                        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
                            int responseCode = connection.getResponseCode();
                            VerboseLogger.info("Response Code: " + responseCode);
                        }

                        // Close connection
                        connection.disconnect();
                    } catch (Exception ignored) {}
                    LOGGER.info("Webserver Stopped!");
                    break;
                } else {
                    sleep(2);
                }
            }
        });
        serverthread.setName("FibreWiki-main");
        serverthread.start();
    }

    private static void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        LOGGER.info("Starting Webserver...");
        new HttpServer(SOCKET);
        HttpServer.main();
    }

    public static void createServerDir() {
        // create server dir as specified in the config WEB_ROOT
        Path path = FabricLoader.getInstance().getGameDir();
        Path webroot = path.resolve(ModConfigs.WEB_ROOT);
        webroot.toFile().mkdirs();
    }
}
