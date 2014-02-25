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
        colorPrettyDisplay.put("P", "♠"); // PIQUE
        colorPrettyDisplay.put("S", "♠"); // SPADE

        colorPrettyDisplay.put("C", "♥"); // COEUR
        colorPrettyDisplay.put("H", "♥"); // HEART

        colorPrettyDisplay.put("K", "♦"); // CARREAU
        colorPrettyDisplay.put("D", "♦"); // DIAMOND

        colorPrettyDisplay.put("T", "♣"); // TREFLE
        colorPrettyDisplay.put("Cl", "♣"); // CLUB
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
        return new ArrayList<Suit>(Arrays.asList(new Suit("♠"), new Suit("♥"),
                                                  new Suit("♦"), new Suit("♣")));
    }
}
