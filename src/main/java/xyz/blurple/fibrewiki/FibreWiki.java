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

package xyz.blurple.fibrewiki;

import xyz.blurple.chatmsglib.ChatMessage;
import xyz.blurple.fibrewiki.account.AccountHandler;
import xyz.blurple.fibrewiki.config.ModConfigs;
import xyz.blurple.fibrewiki.web.ServerHandler;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FibreWiki implements DedicatedServerModInitializer {

	public static final String MOD_ID = "fibrewiki";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final Logger VERBOSELOGGER = LoggerFactory.getLogger(MOD_ID + " - VERBOSE LOGGER");
	public static Boolean ISFIRSTSTART = false;
	public static MinecraftServer MC_SERVER;
	public static final ChatMessage CM = new ChatMessage();

	@Override
	public void onInitializeServer() {

		// register configs
		ModConfigs.registerConfigs();
		LOGGER.info("FibreWiki initialized!");

		if (ISFIRSTSTART) {
			LOGGER.info("");
			ServerHandler.createServerDir();

		}

		// start the account system
		AccountHandler.loadAccounts();

		// start the server
		ServerHandler.start();
	}
}
