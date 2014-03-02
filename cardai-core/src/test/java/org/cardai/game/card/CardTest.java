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

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.cardai.exception.UnexpectedSituationException;

public class CardTest {

    private Value cardValue;
    private Suit cardSuit;
    private Card card;

    @Before
    public void setUp() throws Exception {
        cardValue = new Value(1,2,3,4,"7");
        cardSuit  = new Suit("P");
        card      = new Card(cardValue, cardSuit);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testCardString() throws UnexpectedSituationException{
        Card c = new Card("7P");
        assertTrue(c.equals("7P"));
    }

    @Test
    public void testEqualsString() {
       assertTrue(card.equals("7P"));
       assertFalse(card.equals("8P"));
       assertFalse(card.equals("7T"));
       assertFalse(card.equals("MP"));
       assertFalse(card.equals("7V"));
    }

    @Test
    public void testGetSuit() {
        assertEquals(card.getSuit().suit,"P");
    }

    @Test
    public void testGetValue() {
        assertEquals(card.getValue().value,"7");
    }

    @Test
    public void testToStringValue() {
        assertEquals(card.toString(),"7\u2660");
    }

    @Test
    public void testGetOrder() {
        assertEquals(card.getOrder(false),1);
        assertEquals(card.getOrder(true),2);
    }

    @Test
    public void testGetPointBoolean() {
        assertEquals(card.getPoint(false),3);
        assertEquals(card.getPoint(true),4);
    }

    @Test
    public void testGetPointSuit() {
        assertEquals(card.getPoint(new Suit("K")),3);
        assertEquals(card.getPoint(new Suit("P")),4);
    }

    @Test
    public void testToString() {
        assertEquals(card.toString(),"7\u2660");
    }

}
