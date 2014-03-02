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

import java.util.Random;

public class RandomSeed {

    private static Random r = new Random(0);
    private static int seed = 0;

    //TODO Remove this class and manage random locally

    public static int nextInt(int n) {
        return r.nextInt(n);
    }

    public static void setSeed(int seed) {
        RandomSeed.seed = seed;
        r.setSeed(seed);
    }

    protected static Random reset() {
        r.setSeed(RandomSeed.seed);
        return r;
    }

    public static Random getGenerator() {
        return r;
    }

}
