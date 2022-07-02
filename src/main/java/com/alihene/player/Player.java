package com.alihene.player;

import com.alihene.Bot;
import com.alihene.Database;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.javacord.api.entity.user.User;

import java.math.BigInteger;

public class Player {

    private static final int DEFAULT_HEALTH = 50;
    private static final int DEFAULT_STRENGTH = 10;
    private static final int DEFAULT_DEFENSE = 0;
    private static final int DEFAULT_CRIT_CHANCE = 100;
    private static final int DEFAULT_LUCK = 10;

    private final Document newUserTemplate;
    private final User user;
    private final Database database;

    private Player(User user) {
        this.user = user;
        database = Bot.getInstance().getDatabase();

        newUserTemplate = new Document("ID", user.getId())
                .append("balance", "1000")
                .append("stats", new Document("health", DEFAULT_HEALTH)
                        .append("strength", DEFAULT_STRENGTH)
                        .append("defense", DEFAULT_DEFENSE)
                        .append("crit-damage", DEFAULT_CRIT_CHANCE)
                        .append("luck", DEFAULT_LUCK))
                .append("inventory", new Document())
                .append("equipped-items", new Document("weapon", null)
                        .append("helmet", null)
                        .append("chestplate", null)
                        .append("leggings", null)
                        .append("boots", null))
                .append("effects", new Document());
    }

    public BigInteger getBalance() {
        return new BigInteger(toDocument().getString("balance"));
    }

    public void setBalance(BigInteger newBalance) {
        database.getUsers().findOneAndUpdate(toDocument(), Updates.set("balance", newBalance.toString()));
    }

    public void addBalance(BigInteger balance) {
        setBalance(getBalance().add(balance));
    }

    public void removeBalance(BigInteger balance) {
        setBalance(getBalance().subtract(balance));
    }

    public void register() {
        if(!isRegistered()) {
            database.getUsers().insertOne(newUserTemplate);
        } else {
            throw new IllegalStateException("Player already registered");
        }
    }

    public boolean isRegistered() {
        return database.getUsers().find(new Document("ID", user.getId())).first() != null;
    }

    public User asUser() {
        return user;
    }

    public Document toDocument() {
        return (Document) database.getUsers().find(new Document("ID", user.getId())).first();
    }

    public static Player of(User user) {
        return new Player(user);
    }
}
