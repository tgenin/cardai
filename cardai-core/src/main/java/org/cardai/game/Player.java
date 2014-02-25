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

import java.util.List;

import org.cardai.game.card.Card;
import org.cardai.game.play.PlayingStrategy;
import org.cardai.game.play.Strategy;


public class Player {
    private Hand hand;

    private PlayingStrategy playingStrategy;

    public Player (int position) {
        this.playingStrategy = null;
    }

    public Hand getHand() {
        return this.hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public Card play(List<Card> playedCards) {
        // TODO allow player instance to access to playedCards
        Card c = this.playingStrategy.play(hand, playedCards);
        this.hand.removeCard(c);
        return c;
    }

    public void setStrategy(Strategy strategy) {
        this.playingStrategy = (PlayingStrategy) strategy;
    }
}
