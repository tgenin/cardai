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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.cardai.exception.UnexpectedSituationException;
import org.cardai.game.card.Card;
import org.cardai.game.card.Suit;
import org.cardai.game.card.Value;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HandTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testHand() {
        Hand h = new Hand();
        assertFalse(h.getCards() == null);
    }

    @Test
    public void testHandInt() {
        Hand h = new Hand(10);
        assertFalse(h.getCards() == null);
    }

    @Test
    public void testHandListOfCard() {
        List<Card> cards = new ArrayList<Card>();
        Hand h2 = new Hand(cards);
        assertTrue(h2.getCards() == cards);
    }

    @Test
    public void testClone() throws UnexpectedSituationException {
        Card card = new Card("7P");
        List<Card> cards = new ArrayList<Card>(Arrays.asList(card));
        Hand h = new Hand(cards);
        Hand hclone = h.clone();
        Hand hcopy = h;

        assertTrue(h == hcopy);
        assertTrue(h.getCards() == hcopy.getCards());

        assertFalse(h == hclone);
        assertFalse(h.getCards() == hclone.getCards());
        assertTrue(h.getCards().get(0) == hclone.getCards().get(0));
    }

    @Test
    public void testAdd() throws UnexpectedSituationException {
        Hand h = new Hand(1);
        Card c = new Card("7P");
        h.add(c);
        assertTrue(h.getCards().get(0) == c);
    }

    @Test
    public void testGetCards() {
        List<Card> cards = new ArrayList<Card>();
        Hand h2 = new Hand(cards);
        assertTrue(h2.getCards() == cards);
    }

    @Test
    public void testGetCardsSuit() throws UnexpectedSituationException {
        Hand h = new Hand(Arrays.asList(new Card("7P"), new Card("8P"), new Card("4T")));
        Suit p = new Suit("P");
        Suit t = new Suit("T");
        Suit k = new Suit("K");

        assertEquals(h.getCards(p).size(), 2);
        assertEquals(h.getCards(t).size(), 1);
        assertEquals(h.getCards(k).size(), 0);

        assertTrue(h.getCards(t).get(0).equals("4T"));
    }

    @Test
    public void testComputePoints() {
        Suit P = new Suit("P");
        Suit T = new Suit("T");
        Value J = new Value(0,0,2,20,"J");
        Value N = new Value(0,0,0,14,"N");
        Value R = new Value(0,0,4,4,"R");

        Hand h = new Hand(Arrays.asList(new Card(J,P),
                                        new Card(N,T),
                                        new Card(R,P)));

        assertEquals((int) h.computePoints(P), 20+4);
        assertEquals((int) h.computePoints(T), 2+14+4);
    }

    @Test
    public void testContains() throws UnexpectedSituationException {
        Hand h = new Hand(Arrays.asList(new Card("7P"), new Card("8P"), new Card("4T")));
        assertTrue(h.contains("8P"));
        assertFalse(h.contains("4P"));
    }

    @Test
    public void testRemoveCard() throws UnexpectedSituationException {
        Card c1 = new Card("7P");
        Card c2 = new Card("8P");
        Card c3 = new Card("4T");
        List<Card> lc = new ArrayList<Card>(Arrays.asList(c1,c2,c3));
        Hand h = new Hand(lc);
        assertEquals(h.getCards().size(),3);
        assertTrue(h.contains("8P"));
        h.removeCard(c2);
        assertEquals(h.getCards().size(),2);
        assertFalse(h.contains("8P"));
    }

    @Test
    public void testAddAll() throws UnexpectedSituationException {
        Hand h = new Hand(
                     new ArrayList<Card>(
                         Arrays.asList(new Card("7P"), new Card("8P"), new Card("4T"))
                     )
                 );
        h.addAll(Arrays.asList(new Card("1P"), new Card("2P"), new Card("3T")));
        assertEquals(h.getCards().size(), 6);
    }
}
