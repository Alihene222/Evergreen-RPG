package com.alihene;

import com.alihene.command.CommandManager;
import com.alihene.command.RegisterCommand;
import io.github.cdimascio.dotenv.Dotenv;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

import java.awt.*;

public class Bot {

    private static final Logger LOGGER = LogManager.getLogger(Bot.class);
    private static final Color EMBED_COLOR = new Color(23, 191, 29);

    private static Bot instance;
    private static DiscordApi api;
    private final CommandManager commandManager;
    private final Database database;

    public static void main(String[] args) {
        instance = new Bot();
    }

    private Bot() {
        api = new DiscordApiBuilder().setToken(Dotenv.load().get("TOKEN")).login().exceptionally(throwable -> {
            getLogger().fatal("Logging in failed");
            System.exit(1);
            return null;
        }).join();
        getLogger().info("Successfully logged in");

        database = new Database(api);

        commandManager = CommandManager.create(api);
        addCommands();
    }

    private void addCommands() {
        commandManager.addCommand(new RegisterCommand());
    }

    public static Color getEmbedColor() {
        return EMBED_COLOR;
    }

    public Database getDatabase() {
        return database;
    }

    public static Bot getInstance() {
        return instance;
    }

    public static Logger getLogger() {
        return LOGGER;
    }
}
