package com.alihene.player.item;

import com.alihene.Bot;
import com.alihene.Database;
import com.alihene.player.Player;
import com.mongodb.client.model.Updates;
import org.bson.Document;

public abstract class Item {
    private String name;
    private Rarity rarity;
    private final Database database;

    public Item() {
        database = Bot.getInstance().getDatabase();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }

    public boolean hasItem(Player player) {
        Document inventory = (Document) player.toDocument().get("inventory");

        if(inventory.get(getName()) != null) {
            return true;
        }
        return false;
    }

    public int getAmount(Player player) {
        if(!hasItem(player)) {
            return 0;
        } else {
            Document inventory = (Document) player.toDocument().get("inventory");

            return inventory.getInteger(getName());
        }
    }

    public void add(Player player, int amount) {
        if(amount < 0) {
            throw new IllegalArgumentException("Amount is a negative");
        }

        Document inventory = (Document) player.toDocument().get("inventory");

        if(!hasItem(player)) {
            inventory.append(getName(), amount);
        } else {
            database.getUsers().findOneAndUpdate(new Document("ID", player.asUser().getId()), Updates.set("inventory." + getName(), getAmount(player) + amount));
        }
    }

    public void remove(Player player, int amount) {
        if(amount < 0) {
            throw new IllegalArgumentException("Amount is a negative");
        }

        if(amount < getAmount(player)) {

        }
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }

        if(!(o instanceof Item)) {
            return false;
        }

        Item i = (Item) o;

        if(getName() == i.getName() && getRarity() == i.getRarity()) {
            return true;
        }
        return false;
    }
}
