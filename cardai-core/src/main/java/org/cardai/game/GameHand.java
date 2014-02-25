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

import org.cardai.exception.UnexpectedSituationException;
import org.cardai.game.card.Card;

import java.util.ArrayList;
import java.util.List;

public class GameHand {

    /**
     * GameHand manage public game board
     */

    private Integer originalReferencePosition;
    private Integer referencePosition;
    private int numPlayers;
    private List<Hand> hands;
    private List<Card> playedCards; // all played cards
    private int trickStarter;

    public GameHand(int numPlayers) throws UnexpectedSituationException {
        this(numPlayers,0);
    }

    public GameHand(int numPlayers, int referencePosition) throws UnexpectedSituationException {
        this.referencePosition         = referencePosition;
        this.trickStarter              = referencePosition;
        this.originalReferencePosition = referencePosition;
        this.numPlayers        = numPlayers;
        this.playedCards       = new ArrayList<Card>(100);
        this.hands             = new ArrayList<Hand>(numPlayers);
    }

    public void play(Card card) {
        playedCards.add(card);
        hands.get(referencePosition).removeCard(card);
    }

    public List<Card> getPlayedCards() {
        return playedCards;
    }

    public Integer getReferencePosition() {
        return this.referencePosition;
    }

    public void setReferencePosition(Integer pos) {
        this.referencePosition = pos;
    }

    public List<Hand> getHands() {
        return this.hands;
    }

    public void setHands(List<List<Card>> deal) {
        for (int i = 0; i < this.numPlayers; i++) {
            Hand h = new Hand(deal.get(i));
            this.hands.add(i, h);
        }
    }

    public int getOriginalReferencePosition() {
        return this.originalReferencePosition;
    }

    public int getTrickStarter() {
        return this.trickStarter;
    }

    public void setTrickStarter(int trickStarter) {
        this.trickStarter = trickStarter;
    }
}
