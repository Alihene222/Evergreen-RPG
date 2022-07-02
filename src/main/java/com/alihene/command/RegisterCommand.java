package com.alihene.command;

import com.alihene.Bot;
import com.alihene.player.Player;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;

public class RegisterCommand implements CommandExecutor {

    @Command(aliases = "ev.register")
    public void onCommand(MessageAuthor author, Message message) {
        User user = author.asUser().get();
        Player player = Player.of(user);

        try {
            player.register();
            message.reply(new EmbedBuilder()
                    .setTitle("Success!")
                    .setDescription("You have been registered.")
                    .setColor(Bot.getEmbedColor()));
        } catch (IllegalStateException e) {
            message.reply(new EmbedBuilder()
                    .setTitle("Oops!")
                    .setDescription("You are already registered!")
                    .setColor(Bot.getEmbedColor()));
        }
    }
}
