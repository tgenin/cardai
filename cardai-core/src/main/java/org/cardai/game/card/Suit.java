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

    public static final Map<String, String> colorPrettyDisplay;
    static {
        colorPrettyDisplay = new HashMap<String, String>();
        colorPrettyDisplay.put("P", "\u2660"); // PIQUE
        colorPrettyDisplay.put("S", "\u2660"); // SPADE

        colorPrettyDisplay.put("C", "\u2665"); // COEUR
        colorPrettyDisplay.put("H", "\u2665"); // HEART

        colorPrettyDisplay.put("K", "\u2666"); // CARREAU
        colorPrettyDisplay.put("D", "\u2666"); // DIAMOND

        colorPrettyDisplay.put("T", "\u2663"); // TREFLE
        colorPrettyDisplay.put("Cl", "\u2663"); // CLUB
    }

    public Suit(String suit) {
        this.suit = suit;
        this.display = (colorPrettyDisplay.containsKey(suit)) ? colorPrettyDisplay.get(suit)
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
