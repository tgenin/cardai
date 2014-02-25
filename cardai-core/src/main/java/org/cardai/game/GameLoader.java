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
import org.cardai.simplegame.SimpleGame;

public class GameLoader {

    private static Game game;

    public static Game load(String gamename) throws UnexpectedSituationException {
        switch(gamename) {
            case "simple-game":
                game = new SimpleGame();
                break;
            default:
                throw new UnexpectedSituationException("Unknown game <"+gamename+">");
        }
        return game;
    }

    public static Game game()  {
        assert(game != null);

        return game;
    }
}
