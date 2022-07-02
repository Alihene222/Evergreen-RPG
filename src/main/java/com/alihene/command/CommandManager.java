package com.alihene.command;

import de.btobastian.sdcf4j.CommandExecutor;
import de.btobastian.sdcf4j.CommandHandler;
import de.btobastian.sdcf4j.handler.JavacordHandler;
import org.javacord.api.DiscordApi;

public class CommandManager {
    private final DiscordApi api;
    private final CommandHandler handler;

    private CommandManager(DiscordApi api) {
        this.api = api;
        handler = new JavacordHandler(api);
    }

    public void addCommand(CommandExecutor executor) {
        handler.registerCommand(executor);
    }

    public static CommandManager create(DiscordApi api) {
        return new CommandManager(api);
    }
}
