package com.alihene;


import com.alihene.player.item.items.Stick;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class Main {
    @Test
    public void itemEqualsTest() {
        Stick stick1 = new Stick();
        Stick stick2 = new Stick();
        stick2.setName("NotStick");

        assertTrue(stick1.equals(stick2));
    }
}
