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

import org.cardai.exception.InternalException;
import org.cardai.game.Game;
import org.cardai.game.GameHand;
import org.cardai.game.card.Card;
import org.cardai.game.card.Suit;
import org.cardai.game.play.Strategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.cardai.simplegame.playingstrategy.PlayFirstCard;
import org.cardai.simplegame.playingstrategy.PlayHighestCard;
import org.cardai.simplegame.playingstrategy.PlayRandomCard;

public class SimpleGame extends Game {
    /**
     * Simple Game is a dummy card game used to develop the engine
     * rules are easy :
     * Each player plays one card
     * All cards are allowed to be play
     * Card order is 7 < 8 < 9 < 10 < J < Q < K <A
     * The highest card wins the trick
     * In case of draw the first played card wins
     * The objective of a player is to win a maximal number of tricks
     */

    int[] scores = new int[numPlayers];

    @Override
    /**
     * All card are allowed
     */
    public List<Card> allowedCards(List<Card> cards, List<Card> alreadyPlayed, Suit trumpColor) {
        return cards;
    }

    @Override
    protected void setStrategies() {
        this.strategies = new HashMap<String,Strategy>();

        // TODO Find a proper way to be more dynamic ? Dynamically load class files ?)
        this.strategies.put(PlayRandomCard.getLabel()  , new PlayRandomCard());
        this.strategies.put(PlayHighestCard.getLabel() , new PlayHighestCard());
        this.strategies.put(PlayFirstCard.getLabel()   , new PlayFirstCard());
    }

    @Override
    public Integer nextPlayer(GameHand gamehand) {
        int next;
        List<Card> playedCards = gamehand.getPlayedCards();

        try {
            // trick is done, there is a winner
            List<Card> trick = lastTrick(playedCards);
            int winindex = computeWinnerIndex(trick);
            next = (winindex + gamehand.getTrickStarter()) % numPlayers;
            gamehand.setTrickStarter(next); // TODO Ughh manage this in gamehand not here !!!!
//            System.out.println("w:"+next+ " ");
        } catch (InternalException e) {
            // trick not finished yet
            next = (gamehand.getReferencePosition() + 1) % numPlayers;
        }

        if (gamehand.getHands().get(next).size() == 0) {
            return null;
        }
        return next;
    }

    /**
     * Computes the winner cards from a list of cards
     * @param cards list of cards to analyse
     * @return winner card index
     * @throws InternalException cards contains no cards
     */
    private int computeWinnerIndex(List<Card> cards) {
        assert(cards.size() > 0);

        int winIndex = 0;
        for (int i = 1; i < cards.size(); i++) {
            if (cards.get(winIndex).getOrder() < cards.get(i).getOrder()) {
                winIndex = i;
            }
        }
        return winIndex;
    }

    //TODO
    private List<Card> lastTrick(List<Card> cards) throws InternalException {
        if(cards.size() == 0)
            throw new InternalException("No card play yet");

        if (! isTrickDone(cards))
            throw new InternalException("Cannot take last trick when trick is unfinished");

        ArrayList<Card> trick = new ArrayList<>(numPlayers);
        int lastIndex = cards.size() - 1;
        for (int i = numPlayers - 1; i >= 0; i--) {
            trick.add(cards.get(lastIndex -  i));
        }
        return trick;
    }

    //TODO implement generic List<E>
    private boolean isTrickDone(List<Card> cards) {
        return (cards.size() % numPlayers) == 0;
    }

    @Override
    public void register(GameHand gamehand) {
        List<Card> playedCards = gamehand.getPlayedCards();
        int[] computeWinnerArray = computeWinnerArray(playedCards);
        for (int winIndex : computeWinnerArray) {
            int index = (gamehand.getOriginalReferencePosition() + winIndex) % scores.length;
            scores[index]++;
        }
    }

    /**
     * Replay the game to compute list of winners
     * @param cards
     * @return
     * @throws InternalException
     */

    public int[] computeWinnerArray(List<Card> cards) {
        assert(cards.size() > 0);
        assert(cards.size() % numPlayers == 0);

        int[] winnerArray = new int[cards.size() / numPlayers];

        int index = this.computeWinnerIndex(cards.subList(0, numPlayers));
        winnerArray[0] = index;

        for (int i = 1; i < winnerArray.length; i++) {
            index = this.computeWinnerIndex(cards.subList(numPlayers*i, numPlayers*(i+1)));
            winnerArray[i] = (winnerArray[i-1] + index) % numPlayers;
        }

        return winnerArray;
    }

    @Override
    public void analyse() {
        double sum = 0;
        for (int s : scores) {
            sum += s;
        }
        System.out.println("Player    % tricks wins");
        for (int i = 0; i < scores.length; i++) {
            String out = "Player <" +  i + ">: ";

            double d = (double) scores[i] / sum * 100.0;
            String dformat = String.format("%8.2f", d);

            out += dformat + "% ";
            System.out.println(out);
        }
    }

    public List<Card> currentTrick() {
        // TODO Auto-generated method stub
        return null;
    }
}

