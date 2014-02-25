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

import org.cardai.exception.UnexpectedSituationException;

public class Value {

    public int suit_order;
    public int trump_order;
    public int suit_point;
    public int trump_point;
    public String value;

    private static final Map<String, Integer> refOrder;
    static {
        refOrder = new HashMap<String, Integer>();
        refOrder.put("1", 1);
        refOrder.put("2", 2);  refOrder.put("3", 3);  refOrder.put("4", 4);
        refOrder.put("5", 5);  refOrder.put("6", 6);  refOrder.put("7", 7);
        refOrder.put("8", 8);  refOrder.put("9", 9);  refOrder.put("X", 10);
        refOrder.put("J", 11); refOrder.put("C", 12); refOrder.put("Q", 13);
        refOrder.put("K", 15); refOrder.put("A", 15);
    }

    // TOODO Generalized, not all card games have a trump system
    public Value(int suit_order, int trump_order, int suit_point, int trump_point, String value) {
        this.suit_order  = suit_order;
        this.trump_order = trump_order;
        this.suit_point  = suit_point;
        this.trump_point = trump_point;
        this.value       = value;
    }

    public Value(String value) throws UnexpectedSituationException {
        if (refOrder.get(value) == null)
            throw new UnexpectedSituationException("Unknown value <" + value + ">");

        this.suit_order  = refOrder.get(value);
        this.trump_order = refOrder.get(value);
        this.suit_point  = refOrder.get(value);
        this.trump_point = refOrder.get(value);
        this.value       = value;
    }

    public String toString() {
        return value;
    }

    public boolean equals(Value v) {
        return this.value.equals(v.value);
    }

    public static List<Value> get32(boolean isAceBest) {
        List<Value> list = new ArrayList<Value>(Arrays.asList(
                                   new Value (1,1,0,0, "7"),
                                   new Value (2,2,0,0, "8"),
                                   new Value (3,3,0,0, "9"),
                                   new Value (4,4,0,0, "X"),
                                   new Value (5,5,0,0, "V"),
                                   new Value (6,6,0,0, "D"),
                                   new Value (7,7,0,0, "K")));
        if (isAceBest) {
            list.add(new Value (8,8,0,0, "A"));
        }
        else {
            list.add(new Value (0,0,0,0, "A"));
        }

        return list;
    }
}
