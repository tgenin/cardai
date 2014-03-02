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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.cardai.exception.UnexpectedSituationException;
import org.cardai.game.card.Card;
import org.cardai.game.card.Suit;
import org.cardai.game.card.Value;
import org.cardai.game.play.Strategy;

public abstract class Game {

    protected List<Suit> suits;
    protected List<Value> values;
    protected Map<String, Strategy> strategies = null; // Must be specified in inherited class
    protected int numPlayers;

    public Game() {
        setCardSuits();
        setCardValues(); // Set Values outside constructor so that the method can be overridable
        setStrategies();
        this.numPlayers = 4; // To be parameterizable
    }

    // TODO be more generic, not all games have trump or alreadyplayed
    public abstract List<Card> allowedCards (List<Card> cards, List<Card> alreadyPlayed, Suit trumpColor) throws UnexpectedSituationException;

    protected abstract void setStrategies();

    public abstract Integer nextPlayer(GameHand gamehand);

    public abstract void register(GameHand gamehand);

    public abstract void analyse();

    private void setCardSuits() {
        this.suits = Suit.getClassicalSuits();
    };

    private void setCardValues() {
        this.values = Value.get32(true);
    }

    public List<Suit> suits() {
        return this.suits;
    }

    public List<Value> values() {
        return this.values;
    }

    /**
     * Deal the input cards to each player w.r.t the dealer
     * @param cards
     * @param startPlayer
     * @return
     */
    public List<List<Card>> deal(Deck deck, int startPlayer) {
        List<List<Card>> deal = new ArrayList<List<Card>>(numPlayers + 1); // + 1 for remaining cards
        int counter = deck.size();

        for (int i = 0; i < numPlayers + 1; i++) {
            deal.add(new ArrayList<Card>(counter / numPlayers));
        }
        // Deal the cards to players
        while (this.numPlayers <= counter) {
            for (int i = 0; i < numPlayers; i++) {
                deal.get((startPlayer + i) % numPlayers).add(deck.get(counter-1));
                counter--;
            }
        }
        // Put the remaining cards on the last pack
        while (counter > 0) {
            deal.get(numPlayers).add(deck.get(counter-1));
            counter--;
        }
        return deal;
    }

    public int getNumOfPlayers() {
        return numPlayers;
    }

    public Strategy getStrategy(String label) throws UnexpectedSituationException {

        if (! strategies.containsKey(label))
            throw new UnexpectedSituationException("Unknown strategy <" + label + ">");

        return strategies.get(label);
    }


}
