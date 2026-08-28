package cat.andreu.jovia.games;

/**
 * Represents a minigame available in Jovia.
 *
 * @author Andreu
 * @version 2.0
 */
public interface Minigame {

    /**
     * Returns the unique identifier of the minigame.
     *
     * @return Minigame identifier.
     */
    String getId();

    /**
     * Returns the translation key used for the minigame name.
     *
     * @return Minigame name translation key.
     */
    String getNameKey();
}