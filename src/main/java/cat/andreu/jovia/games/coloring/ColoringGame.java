package cat.andreu.jovia.games.coloring;

import cat.andreu.jovia.games.Minigame;

/**
 * Represents the coloring minigame.
 *
 * @author Andreu
 * @version 2.0
 */
public final class ColoringGame implements Minigame {

    @Override
    public String getId() {
        return "COLORING";
    }

    @Override
    public String getNameKey() {
        return "game.coloring.name";
    }
}