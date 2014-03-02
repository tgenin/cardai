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

import java.util.List;

import org.cardai.exception.UnexpectedSituationException;

public class Card {

    private Suit color;
    private Value value;

    public Card(String s) throws UnexpectedSituationException {
        this(new Value(s.substring(0,1)), new Suit(s.substring(1)));
    }

    public Card(Value value, Suit suit) {
        super();
        this.color = suit;
        this.value = value;
    }

    public boolean equals(String s) {
        return this.value.value.equals(s.substring(0,1)) && this.color.suit.equals(s.substring(1));
    };

    public Suit getSuit() {
        return color;
    }

    public Value getValue() {
        return value;
    }

    public String toStringValue() { //TODO use toString() autoload
        return this.getValue().toString();
    }

    /**
     * Get card order wrt whether the suit is trump or not
     * @param isTrump indicate whether the suit is truc or not
     * @return int Card order
     */
    public int getOrder(boolean isTrump) {
        // TODO make this method private and expose getOrder(Suit trump)
        return isTrump ? this.value.getTrumpOrder() : this.value.getSuitOrder();
    }

    public int getPoint(boolean isTrump) {
        return isTrump ? this.value.getTrumpPoint() : this.value.getSuitPoint();
    }

    /**
     * Give the card point according to the trump suit
     * @param trump the trump suit
     * @return int num of points
     */
    public int getPoint(Suit trump) {
        return getPoint(this.getSuit().equals(trump));
    }

    public String toString() {
        return this.value + "" + this.color;
    }

    public int getOrder() {
        return getOrder(false);
    }

    /**
     * Compute the lowest card of a List of card
     * @param cards
     * @return
     */
    public static Card minCard(List<Card> cards) {
        if (cards == null)
            return null;

        Card card = cards.get(0);
        for (Card c : cards) {
            if (c.getOrder() < card.getOrder()) {
                card = c;
            }
        }

        return card;
    }

    /**
     * Compute the highest card of a List of card
     * @param cards
     * @return
     */
    public static Card maxCard(List<Card> cards) {
        if (cards == null)
            return null;

        Card card = cards.get(0);
        for (Card c : cards) {
            if (c.getOrder() > card.getOrder()) {
                card = c;
            }
        }

        return card;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;

        if (o == this)
            return true;

        if (!(o instanceof Card))
            return false;

        Card c = (Card) o;
        return this.getValue().equals(c.getValue()) && this.getSuit().equals(c.getSuit());
    }


}
