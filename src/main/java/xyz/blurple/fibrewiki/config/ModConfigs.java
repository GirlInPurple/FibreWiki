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

package xyz.blurple.fibrewiki.config;

import com.mojang.datafixers.util.Pair;

import xyz.blurple.fibrewiki.FibreWiki;
import xyz.blurple.fibrewiki.util.VerboseLogger;

public class ModConfigs {
    public static SimpleConfig CONFIG;
    private static ModConfigProvider config;

    //config
    public static int WEB_PORT;
    public static String WEB_ROOT;
    public static String WEB_FILE_ROOT;
    public static String WEB_FILE_404;
    public static String WEB_FILE_NOSUPPORT;
    public static Boolean VERBOSE = false; //needs to be set to false since the verbose logger is called before config file is fully loaded


    public static void registerConfigs() {

        config = new ModConfigProvider();

        createConfigs();

        CONFIG = SimpleConfig.of(FibreWiki.MOD_ID).provider(config).request();

        assignConfigs();

        //make verbose logger show that it is active and print configs to logger
        VerboseLogger.info("Verbose Logger is now logging.");
        VerboseLogger.info("Loaded FibreWiki config file successfully: found " + config.getConfigsList().size() + " overrides and configurations.");
    }

    private static void createConfigs() {
        config.addKeyValuePair(new Pair<>("web.port", 8080), "The port of the webserver");
        config.addKeyValuePair(new Pair<>("web.root", "webserver/"), "the root directory of the webserver, starting from the main server directory");
        config.addKeyValuePair(new Pair<>("web.file.root", "index.html"), "the name of the html file for the homepage");
        config.addKeyValuePair(new Pair<>("web.file.404", "404.html"), "the name of the html file for 404 page");
        config.addKeyValuePair(new Pair<>("web.file.notSupported", "not_supported.html"), "the name of the html file for 'not supported' page");
        config.addKeyValuePair(new Pair<>("logger.verbose", false), "whether to log verbose output");
    }

    private static void assignConfigs() {
        WEB_PORT = CONFIG.getOrDefault("web.port", 8080);
        WEB_ROOT = CONFIG.getOrDefault("web.root", "webserver/");
        WEB_FILE_ROOT = CONFIG.getOrDefault("web.file.root", "index.html");
        WEB_FILE_404 = CONFIG.getOrDefault("web.file.404", "404.html");
        WEB_FILE_NOSUPPORT = CONFIG.getOrDefault("web.file.notSupported", "not_supported.html");
        VERBOSE = CONFIG.getOrDefault("logger.verbose", true);
    }
}
