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

package xyz.blurple.fibrewiki.account;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import xyz.blurple.chatmsglib.Singleton;
import xyz.blurple.fibrewiki.FibreWiki;

import java.net.InetAddress;

import static xyz.blurple.fibrewiki.account.AccountHandler.*;

public class AccountCommands {

    public void registerAccountCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("fibre-account")
                .then(CommandManager.literal("code")
                    .then(CommandManager.argument("Code", StringArgumentType.word())
                        .executes(this::test)
                    )
                )
            );
        });
    }

    public int test(CommandContext<ServerCommandSource> c) throws CommandSyntaxException {

        if (!c.getSource().isExecutedByPlayer()) {
            FibreWiki.CM
                .literal("This command can be used by players only!")
                .send(c);
            return 0;
        }

        String code = StringArgumentType.getString(c, "Code");

        boolean codeExists = false;
        InetAddress address = null;
        for (LoginCode lg : stagedAccounts) {
            if (lg.HEX == code) {
                codeExists = true;
                address = lg.IP;
                break;
            }
        }

        if (codeExists) {
            FibreWiki.CM
                .object(
                    new Singleton()
                    .add(Text.of("The IP Adress \""))
                    .add(Text.of(address.toString()))
                    .add(Text.of("Has been saved"))
                    .create()
                )
                .send(c);

            saveAccount(
                    c.getSource().getPlayer().getUuid(),
                    address
            );
        } else {
            FibreWiki.CM
                .literal("That code isnt valid!")
                .send(c);
        }

        return 0;
    }
}
