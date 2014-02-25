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

package org.cardai.game;

import org.cardai.game.card.Card;
import org.cardai.game.card.Suit;
import org.cardai.game.card.Value;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {

    private List<Card> cards;

    public Deck(Game g) {
        cards = new ArrayList<Card>(g.suits().size() * 8);
        for (Suit suit : g.suits()) {
            for (Value value : g.values()) {
                cards.add(new Card(value,suit));
            }
        }
        shuffle();
    }

    public List<Card> shuffle() {
        Collections.shuffle(cards,RandomSeed.getGenerator());
        return cards;
    }

    public List<Card> getCards() {
        return cards;
    }
}
