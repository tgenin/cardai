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

package org.cardai.simplegame;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.cardai.exception.InternalException;
import org.cardai.exception.UnexpectedSituationException;
import org.cardai.game.GameHand;
import org.cardai.game.GameLoader;
import org.cardai.game.card.Card;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SimpleGameTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testComputeWinnerArray() throws UnexpectedSituationException {
        SimpleGame g = (SimpleGame) GameLoader.load("simple-game");
        List<Card> cards = new ArrayList<Card>(12);
        cards.addAll(Arrays.asList(
            new Card("7P"), new Card("KP"), new Card("8P"), new Card("JP"), // 1
            new Card("JK"), new Card("8K"), new Card("KK"), new Card("7K"), // 3
            new Card("9C"), new Card("7C"), new Card("8C"), new Card("XC")  // 2
        ));

        int[] winnerArray = g.computeWinnerArray(cards);
        int[] expected = {1,3,2};
        assertArrayEquals(expected, winnerArray);
    }

    @Test
    public void testNextPlayer() throws UnexpectedSituationException {
        SimpleGame g = (SimpleGame) GameLoader.load("simple-game");
        GameHand gh = new GameHand(g.getNumOfPlayers());
        List<List<Card>> hands = new ArrayList<List<Card>>(g.getNumOfPlayers());
        List<Card> h0 = new ArrayList<Card>(2);
        List<Card> h1 = new ArrayList<Card>(2);
        List<Card> h2 = new ArrayList<Card>(2);
        List<Card> h3 = new ArrayList<Card>(2);

        h0.addAll(Arrays.asList(new Card("7P"), new Card("7K")));
        h1.addAll(Arrays.asList(new Card("KP"), new Card("KK")));
        h2.addAll(Arrays.asList(new Card("8P"), new Card("8K")));
        h3.addAll(Arrays.asList(new Card("JP"), new Card("JK")));

        hands.addAll(Arrays.asList(h0,h1,h2,h3));
        gh.setHands(hands);


        // Player 0 turn
        assertEquals(0,gh.getReferencePosition().intValue());
        gh.play(h0.remove(0));
        gh.setReferencePosition(g.nextPlayer(gh));


        // Player 1 turn
        assertEquals(1,gh.getReferencePosition().intValue());
        gh.play(h1.get(0));
        gh.setReferencePosition(g.nextPlayer(gh));

        // Player 2 turn
        assertEquals(2,gh.getReferencePosition().intValue());
        gh.play(h2.get(0));
        gh.setReferencePosition(g.nextPlayer(gh));

        // Player 3 turn
        assertEquals(3,gh.getReferencePosition().intValue());
        gh.play(h3.get(0));

        // Player wins with KP
        gh.setReferencePosition(g.nextPlayer(gh));

        // 2nd trick
        // Player 1 turn
        assertEquals(1,gh.getReferencePosition().intValue());
        gh.play(h1.get(0));
        gh.setReferencePosition(g.nextPlayer(gh));

        // Player 2 turn
        assertEquals(2,gh.getReferencePosition().intValue());
        gh.play(h2.get(0));
        gh.setReferencePosition(g.nextPlayer(gh));

        // Player 3 turn
        assertEquals(3,gh.getReferencePosition().intValue());
        gh.play(h3.get(0));
        gh.setReferencePosition(g.nextPlayer(gh));

        // Player 0 turn
        assertEquals(0,gh.getReferencePosition().intValue());
        gh.play(h0.get(0));
        gh.setReferencePosition(g.nextPlayer(gh));

        // game is over, no more cards
        assertNull(gh.getReferencePosition());
    }


    @Test
    public void  testComputeWinner() throws UnexpectedSituationException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        SimpleGame g = (SimpleGame) GameLoader.load("simple-game");
        List<Card> cards = new ArrayList<Card>(g.getNumOfPlayers());
        Method computeWinnerIndex = SimpleGame.class.getDeclaredMethod("computeWinnerIndex",  List.class);
        computeWinnerIndex.setAccessible(true);

        cards.add(new Card("7P"));
        cards.add(new Card("KT"));
        cards.add(new Card("JK"));
        cards.add(new Card("XC"));

        int winIndex = (int) computeWinnerIndex.invoke(g,cards);
        assertEquals(cards.get(winIndex).getValue().value, "K");
        assertEquals(cards.get(winIndex).getSuit().suit, "T");
    }

    @SuppressWarnings("unchecked")
    @Test
    public void  testLastTrick() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, UnexpectedSituationException {
        SimpleGame g = (SimpleGame) GameLoader.load("simple-game");
        List<Card> cards = new ArrayList<Card>();
        Method lastTrick = SimpleGame.class.getDeclaredMethod("lastTrick",  List.class);
        lastTrick.setAccessible(true);

        try {
            lastTrick.invoke(g,cards);
            fail("InternalException expected");
        }
        catch (InvocationTargetException e) {
            try {
                throw e.getCause();
            } catch (InternalException e1) {
                // Expected situation !
            } catch (Throwable e1) {
                e1.printStackTrace();
                fail("Unexpected exception");
            }
        }

        for (int i = 0; i <g.getNumOfPlayers()-1; i++) // add N-1 cards
            cards.add(new Card("7P"));

        try {
            lastTrick.invoke(g,cards);
            fail("InternalException expected");
        }
        catch (InvocationTargetException e) {
            try {
                throw e.getCause();
            } catch (InternalException e1) {
                // Expected situation !
            } catch (Throwable e1) {
                e1.printStackTrace();
                fail("Unexpected exception");
            }
        }
        cards.add(new Card("8K"));
        List<Card> trick = (List<Card>) lastTrick.invoke(g,cards);
        for (int i = 0; i <g.getNumOfPlayers()-1; i++) { // add N-1 cards
            assertEquals(trick.get(i).getValue().value, "7");
            assertEquals(trick.get(i).getSuit().suit, "P");
        }
        assertEquals(trick.get(g.getNumOfPlayers()-1).getValue().value, "8");
        assertEquals(trick.get(g.getNumOfPlayers()-1).getSuit().suit, "K");
    }

    @Test
    public void  testIsTrickDone() throws UnexpectedSituationException, NoSuchMethodException,
                                          SecurityException, IllegalAccessException,
                                          IllegalArgumentException, InvocationTargetException {

        SimpleGame g = (SimpleGame) GameLoader.load("simple-game");
        List<Card> cards = new ArrayList<Card>();
        Method isTrickDone = SimpleGame.class.getDeclaredMethod("isTrickDone",  List.class);
        isTrickDone.setAccessible(true);

        boolean output = (boolean) isTrickDone.invoke(g, cards);
        assertTrue(output); //TODO Is this what we want ?
        Card c = new Card("7P");
        cards.add(c);
        output = (boolean) isTrickDone.invoke(g, cards);
        assertFalse(output);
        for (int i = 2; i < g.getNumOfPlayers(); i++) {
            cards.add(c);
            output = (boolean) isTrickDone.invoke(g, cards);
            assertFalse(output);
        }
        cards.add(c);
        output = (boolean) isTrickDone.invoke(g, cards);
        assertTrue(output);
        for (int i = 1; i < g.getNumOfPlayers(); i++) {
            cards.add(c);
            output = (boolean) isTrickDone.invoke(g, cards);
            assertFalse(output);
        }
        cards.add(c);
        output = (boolean) isTrickDone.invoke(g, cards);
        assertTrue(output);
    }
}
