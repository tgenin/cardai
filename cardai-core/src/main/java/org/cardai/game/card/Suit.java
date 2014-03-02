/* Cardai - A card game engine
 * Copyright (C) 2014 Thomas Génin
 *
 * This file is part of Cardai.
 *
 * Cardai is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * Cardai is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <http://www.gnu.org/licenses/>.
 */

package org.cardai.game.card;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Suit {

    public String suit;
    private String display;

    public static final Map<String, String> PRETTY_DISPLAY;
    static {
        PRETTY_DISPLAY = new HashMap<String, String>();
        PRETTY_DISPLAY.put("P", "\u2660"); // PIQUE
        PRETTY_DISPLAY.put("S", "\u2660"); // SPADE

        PRETTY_DISPLAY.put("C", "\u2665"); // COEUR
        PRETTY_DISPLAY.put("H", "\u2665"); // HEART

        PRETTY_DISPLAY.put("K", "\u2666"); // CARREAU
        PRETTY_DISPLAY.put("D", "\u2666"); // DIAMOND

        PRETTY_DISPLAY.put("T", "\u2663"); // TREFLE
        PRETTY_DISPLAY.put("Cl", "\u2663"); // CLUB
    }

    public Suit(String suit) {
        this.suit = suit;
        this.display = (PRETTY_DISPLAY.containsKey(suit)) ? PRETTY_DISPLAY.get(suit)
                                                          : suit;
    }

    public String toString() {
        return display;
    }

    public boolean equals(Suit suit) {
        return this.suit.equals(suit.suit);
    }

    public static List<Suit> getClassicalSuits() {
        return new ArrayList<Suit>(Arrays.asList(new Suit("\u2660"), new Suit("\u2665"),
                                                  new Suit("\u2666"), new Suit("\u2663")));
    }
}
