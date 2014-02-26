package org.cardai.simplegame.playingstrategy;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.cardai.exception.UnexpectedSituationException;
import org.cardai.game.Game;
import org.cardai.game.GameLoader;
import org.cardai.game.Hand;
import org.cardai.game.card.Card;
import org.junit.Test;

public class PlayFirstImprovedTest {

    @Test
    public void testNextBest() throws UnexpectedSituationException {
        Game g = GameLoader.load("simple-game");
        PlayFirstImproved s = new PlayFirstImproved();
        List<Card> cards = new ArrayList<Card>(Arrays.asList(new Card("7P"),
                                                             new Card("QK"),
                                                             new Card("AT")));
        Hand hand = new Hand(cards);
        List<Card> playedCards = new ArrayList<Card>(Arrays.asList(new Card("AC"), new Card("AP"),
                                                                   new Card("AK"), new Card("KT"),
                                                                   new Card("XC"), new Card("8C"),
                                                                   new Card("JC")));

        Card c = s.play(hand, playedCards);
        assertEquals(c, new Card("QK"));
    }

    @Test
    public void testSmallest() throws UnexpectedSituationException {
        Game g = GameLoader.load("simple-game");
        PlayFirstImproved s = new PlayFirstImproved();
        List<Card> cards = new ArrayList<Card>(Arrays.asList(new Card("9K"),
                                                             new Card("7P"),
                                                             new Card("XT")));
        Hand hand = new Hand(cards);
        List<Card> playedCards = new ArrayList<Card>(Arrays.asList(new Card("AC"), new Card("AP"),
                                                                   new Card("AK"), new Card("KT"),
                                                                   new Card("XC"), new Card("JC")));

        Card c = s.play(hand, playedCards);
        assertEquals(new Card("7P"),c);
    }


}
