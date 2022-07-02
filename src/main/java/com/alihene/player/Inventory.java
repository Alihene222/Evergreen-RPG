package com.alihene.player;

public class Inventory {
    private final Player player;

    private Inventory(Player player) {
        this.player = player;
    }

    Inventory of(Player player) {
        return new Inventory(player);
    }
}
