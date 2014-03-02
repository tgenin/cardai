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
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class GameEngine {

    private final Game         game;
    private final List<Player> playerStrategies;

    public GameEngine(Game game, final List<Player> players) {
        this.game              = game;
        this.playerStrategies  = players;
    }

    /**
     * Plays the whole hand, which means plays as long as a player can play a card
     * @param players
     */
    private void playHand(GameHand gamehand, List<Player> players) {
        RandomSeed.reset(); //TODO Only used for random strategies try to remove it

        while (gamehand.getReferencePosition() != null) {
            final Player player    = players.get(gamehand.getReferencePosition());
            final List<Card> cards = gamehand.getPlayedCards();
            final Card card        = player.play(cards);
            gamehand.play(card);
            gamehand.setReferencePosition(game.nextPlayer(gamehand));
        }
    }

    void runAllHands(int startRun, int stopRun) throws UnexpectedSituationException {
        for (int i = startRun; i < stopRun; i++) {
            for (int dealer = 0; dealer < game.getNumOfPlayers(); dealer++) {
                runOneHand(dealer, i);
            }
        }
    }

    private void runAllHands(int numOfRun) throws UnexpectedSituationException {
        runAllHands(0,numOfRun);
    }

    /**
     * Deal the deck, set hands and run the game
     * this method can be multithreaded
     * @param fork boolean indicates if computation is multithreaded
     * @param gamehand
     * @throws UnexpectedSituationException
     */
    public void run(int numOfRun, boolean fork) throws UnexpectedSituationException {
        RandomSeed.setSeed(numOfRun); //TODO Only used for random strategies try to remove it

        if (!fork) {
            runAllHands(numOfRun);
        }
        else {
            final int numCores = Runtime.getRuntime().availableProcessors();
            System.out.println("Fork on <"+numCores+"> processors");

            final ForkJoinPool pool = new ForkJoinPool(numCores);
            final GameFork gf = new GameFork(this, numOfRun);
            pool.invoke(gf);
            pool.shutdown();
            try {
                pool.awaitTermination(60, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        game.analyse();
    }

    /**
     * Run one seeded hand
     * @param dealer
     * @param seed
     * @throws UnexpectedSituationException
     */
    public void runOneHand(int dealer, int seed) throws UnexpectedSituationException {
        final GameHand gamehand = new GameHand(game.getNumOfPlayers(), dealer);
        final List<Player> players = new ArrayList<Player>(this.playerStrategies.size());

        for (Player player : this.playerStrategies) {
            players.add(player.clone());
        }

        prepareHand(gamehand, players, dealer, seed);
        playHand(gamehand,players);
        game.register(gamehand);
    }

    private void prepareHand(GameHand gamehand, List<Player> players, int dealer, int seed) throws UnexpectedSituationException {
        // TODO dealer and first player are not the same player in real card games

        List<List<Card>> deal = game.deal(new Deck(this.game, seed), dealer);

        gamehand.setReferencePosition(dealer);
        gamehand.setHands(deal);
        for (int i = 0; i < players.size(); i++) {
            players.get(i).setHand(gamehand.getHands().get(i));
        }
    }
}

