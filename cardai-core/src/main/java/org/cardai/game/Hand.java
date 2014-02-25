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

import java.util.ArrayList;
import java.util.List;


public class Hand {

    private List<Card> cards;

    public Hand() {
        this(0);
    }

    public Hand(int n) {
        super();
        this.cards = new ArrayList<Card>(n);
    }

    public Hand(List<Card> cards) {
        super();
        this.cards = cards;
    }

    /**
     * Create a new Hand, with a new list of cards
     * Warning clone Hand is composed of the same cards !
     */

    public Hand clone() {
        List<Card> cards = new ArrayList<Card>(this.cards.size());

        for (Card card : this.cards)
            cards.add(card);

        return new Hand(cards);
    }

    /**
     * Add a card to the Hand
     * @param c Card instance to add to the Hand
     */
    public void add(Card c) {
        this.cards.add(c);
    }

    /**
     * Return Hand's list of cards
     * @return hand's list of cards
     */
    public List<Card> getCards() {
        return cards;
    }

    /**
     * Return all cards of the hand of a given suit
     * @param suit given suit
     * @return all cards of the hand of a given suit
     */
    public List<Card> getCards(Suit suit) {
        //TODO s/8/Generalize size of a hand
        List<Card> result = new ArrayList<Card>(8);
        for (Card card : cards)
            if (card.getSuit().equals(suit)) {
                result.add(card);
            }
        return result;
    }

    /**
     * Computes points of the hand according to a trump suit
     * @param trump Trump suit
     * @return int num of points
     */
    public Integer computePoints(Suit trump) {
        int result = 0;
        for (Card card : this.cards) {
            result += card.getPoint(trump);
        }
        return result;
    }


    public String toString() {
        return this.cards.toString();
    }


    public boolean contains(String scard) {
        for (Card card : this.cards)
            if (card.equals(scard))
                return true;

        return false;
    }


    public void removeCard(Card c) {
        this.cards.remove(c);
    }

    public void addAll(List<Card> someCards) {
        this.cards.addAll(someCards);
    }

    public int size() {
        return this.cards.size();
    }
}
