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

    public final int suitOrder;
    public final int trumpOrder;
    public final int suitPoint;
    public final int trumpPoint;
    public final String value;

    private static final Map<String, Integer> REF_ORDER;
    static {
        REF_ORDER = new HashMap<String, Integer>();
        REF_ORDER.put("1", 1);
        REF_ORDER.put("2", 2);  REF_ORDER.put("3", 3);  REF_ORDER.put("4", 4);
        REF_ORDER.put("5", 5);  REF_ORDER.put("6", 6);  REF_ORDER.put("7", 7);
        REF_ORDER.put("8", 8);  REF_ORDER.put("9", 9);  REF_ORDER.put("X", 10);
        REF_ORDER.put("J", 11); REF_ORDER.put("C", 12); REF_ORDER.put("Q", 13);
        REF_ORDER.put("K", 15); REF_ORDER.put("A", 15);
    }

    // TOODO Generalized, not all card games have a trump system
    public Value(final int suitOrder, final int trumpOrder, final int suitPoint,
                 final int trumpPoint,final String value) {
        this.suitOrder  = suitOrder;
        this.trumpOrder = trumpOrder;
        this.suitPoint  = suitPoint;
        this.trumpPoint = trumpPoint;
        this.value       = value;
    }

    public Value(final String value) throws UnexpectedSituationException {

        if (REF_ORDER.get(value) == null) {
            throw new UnexpectedSituationException("Unknown value <" + value + ">");
        }

        this.suitOrder  = REF_ORDER.get(value);
        this.trumpOrder = REF_ORDER.get(value);
        this.suitPoint  = REF_ORDER.get(value);
        this.trumpPoint = REF_ORDER.get(value);
        this.value      = value;
    }

    public String toString() {
        return value;
    }

    public boolean equals(final Value otherValue) {
        return this.value.equals(otherValue.value);
    }

    public static List<Value> get32(final boolean isAceBest) {
        final List<Value> list = new ArrayList<Value>(Arrays.asList(
                                         new Value (1,1,0,0, "7"),
                                         new Value (2,2,0,0, "8"),
                                         new Value (3,3,0,0, "9"),
                                         new Value (4,4,0,0, "X"),
                                         new Value (5,5,0,0, "V"),
                                         new Value (6,6,0,0, "D"),
                                         new Value (7,7,0,0, "K"))
                                     );
        if (isAceBest) {
            list.add(new Value (8,8,0,0, "A"));
        }
        else {
            list.add(new Value (0,0,0,0, "A"));
        }

        return list;
    }

    public int getSuitOrder() {
        return suitOrder;
    }

    public int getTrumpOrder() {
        return trumpOrder;
    }

    public int getSuitPoint() {
        return suitPoint;
    }

    public int getTrumpPoint() {
        return trumpPoint;
    }
}
