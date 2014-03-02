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

import java.util.concurrent.RecursiveAction;

import org.cardai.exception.UnexpectedSituationException;

public class GameFork extends RecursiveAction {

    private static final long serialVersionUID = 348454750777276545L;

    private final GameEngine gameEngine;
    private final int        numOfRun;
    private final boolean    computeDirectly;
    private final int        start;
    private final int        stop;

    public GameFork(GameEngine gameEngine, int numOfRun) {
        this(gameEngine, numOfRun, 0, numOfRun, false);
    }

    public GameFork(GameEngine gameEngine, int numOfRun, int start, int stop, boolean computeDirectly) {
        super();
        this.gameEngine      = gameEngine;
        this.numOfRun        = numOfRun;
        this.computeDirectly = computeDirectly;
        this.start           = start;
        this.stop            = stop;
    }

    private void computeDirectly(int start, int stop) {
        try {
            gameEngine.runAllHands(start, stop);
        } catch (UnexpectedSituationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fork computation of runs in available processors (1 thread / proc)
     * Split computation equally between n-1 theads and fork them
     * Compute directly the rest
     */
    @Override
    protected void compute() {
        if (computeDirectly) {
            this.computeDirectly(this.start, this.stop);
        }
        else {
            final int numOfProc  = Runtime.getRuntime().availableProcessors();
            final int runPerFork = numOfRun / numOfProc;
            for (int i = 0; i < numOfProc-1; i++) {
                final GameFork gf = new GameFork(gameEngine, numOfRun, i*runPerFork, (i+1)*runPerFork, true);
                gf.fork();
            }
            computeDirectly((numOfProc-1) * runPerFork, numOfRun);
        }

    }

}
