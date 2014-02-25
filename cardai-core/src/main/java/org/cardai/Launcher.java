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


package org.cardai;

import java.util.ArrayList;
import java.util.List;

import org.cardai.exception.UnexpectedSituationException;
import org.cardai.game.Game;
import org.cardai.game.GameEngine;
import org.cardai.game.GameLoader;
import org.cardai.game.Player;

public class Launcher {

    public static void main(String[] args) throws UnexpectedSituationException {

        String gamename = (args.length > 0) ? args[0] : "simple-game";
        String numOfRun = (args.length > 1) ? args[1] : "1000";

        Game g = GameLoader.load(gamename);

        String[] strategies = new String[g.getNumOfPlayers()];

        for (int i = 0; i < strategies.length; i++) {
            strategies[i] = (args.length > i+2) ?  args[i+2] : "first-card";
        }

        List<Player> players = initializePlayers(g,strategies);

        GameEngine ge = new GameEngine(g,players);
        ge.run(Integer.parseInt(numOfRun));

    }

    /**
     * First static version of player configuration
     * @param g game
     * @param strategies
     * @return
     * @return list of players
     * @throws UnexpectedSituationException
     */
    private static List<Player> initializePlayers(Game g, String[] strategies) throws UnexpectedSituationException {

        List<Player> players = new ArrayList<Player>(g.getNumOfPlayers());

        for (int i = 0; i < strategies.length; i++) {
            Player p = new Player(i);
            p.setStrategy(g.getStrategy(strategies[i]));
            players.add(p);
        }
        return players;
    }

}
