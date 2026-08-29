package cat.andreu.jovia.games;

import javax.swing.JPanel;

/**
 * Defines an engine capable of running a specific type of Jovia game.
 *
 * @author Andreu
 * @version 1.0
 */
public interface GameEngine {

    /**
     * Returns the game type supported by this engine.
     *
     * @return Supported game type.
     */
    String getType();

    /**
     * Creates the user interface for the specified game package.
     *
     * @param gamePackage Game package to run.
     * @return Game panel.
     */
    JPanel createView(
            GamePackage gamePackage
    );
}