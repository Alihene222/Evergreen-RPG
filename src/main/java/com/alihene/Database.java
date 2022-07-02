package com.alihene;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.github.cdimascio.dotenv.Dotenv;
import org.javacord.api.DiscordApi;

public class Database {
    private final DiscordApi api;

    private final MongoCollection users;

    public Database(DiscordApi api) {
        this.api = api;

        MongoClient mongoClient = MongoClients.create(Dotenv.load().get("CONNECTION-STRING"));
        MongoDatabase mongoDatabase = mongoClient.getDatabase("Bot");

        users = mongoDatabase.getCollection("users");
    }

    public MongoCollection getUsers() {
        return users;
    }
}
