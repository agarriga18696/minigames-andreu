package cat.andreu.jovia.games;

import java.util.Map;

/**
 * Represents the manifest contained in a Jovia game package.
 *
 * @param formatVersion Package format version.
 * @param id            Unique game identifier.
 * @param type          Game engine type.
 * @param name          Localized game names indexed by language code.
 * @param icon          Path of the game icon inside the package.
 * @param game          Path of the game definition inside the package.
 *
 * @author Andreu
 * @version 1.0
 */
public record GameManifest(
        int formatVersion,
        String id,
        String type,
        Map<String, String> name,
        String icon,
        String game
) {
}