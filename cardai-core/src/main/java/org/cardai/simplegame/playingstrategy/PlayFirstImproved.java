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

package org.cardai.simplegame.playingstrategy;

import java.util.List;

import org.cardai.simplegame.SimpleGame;
import org.cardai.game.card.Card;
import org.cardai.game.play.PlayingStrategy;
import org.cardai.game.GameLoader;
import org.cardai.game.Hand;

public class PlayFirstImproved extends PlayingStrategy {

    /**
     * Improve play first strategy
     */

    public static String getLabel() {
        return "first-improved";
    }

    @Override
    public Card play(Hand hand, List<Card> playedCards) {
        if (playedCards.size() % GameLoader.game().getNumOfPlayers() == 0) // First to play
            return hand.getCards().get(0);

        int numOfPlayers = GameLoader.game().getNumOfPlayers();

        List<Card> trick = playedCards.subList(
                               playedCards.size() - (playedCards.size() % numOfPlayers),
                               playedCards.size()
                           );

        Card maxPlayedCard = Card.maxCard(trick);
        Card myMaxCard     = Card.maxCard(hand.getCards());

        // Do not have a better card => play min card
        if (myMaxCard.getOrder() < maxPlayedCard.getOrder()) {
            return Card.minCard(hand.getCards());
        }

        // Ensure trick with smallest card
        if (playedCards.size() % numOfPlayers == numOfPlayers - 1) {
            return selectNextBest(
                       hand.getCards(),
                       playedCards.subList(playedCards.size() - (numOfPlayers - 1), playedCards.size())
                   );
        }

        // Play first one (i.e. random)
        return hand.getCards().get(0);
    }


    private Card selectNextBest(List<Card> mycards, List<Card> playedCards) {
        SimpleGame g = (SimpleGame) GameLoader.game();
        int index = g.computeWinnerIndex(playedCards);
        Card bestCard = playedCards.get(index);

        Card maxCard = null;
        for (Card c : mycards) {
            if (c.getOrder() > bestCard.getOrder()) {
                if (maxCard == null || c.getOrder() < maxCard.getOrder()) {
                    maxCard = c;
                }
            }
        }

        if (maxCard == null)
            return mycards.get(0); // Should never happen !

        return maxCard;
    }


}
