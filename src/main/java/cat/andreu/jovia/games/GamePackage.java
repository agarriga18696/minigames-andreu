package cat.andreu.jovia.games;

import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a game package loaded into Jovia.
 *
 * @param id       Unique game identifier.
 * @param type     Game engine type.
 * @param names    Localized game names indexed by language code.
 * @param iconPath Path of the icon inside the package.
 * @param gamePath Path of the game definition inside the package.
 * @param source   Original Jovia package file.
 * @author Andreu
 * @version 1.1
 */
public record GamePackage(
        String id,
        String type,
        Map<String, String> names,
        String iconPath,
        String gamePath,
        Path source
) {

    /**
     * Creates and validates a game package.
     */
    public GamePackage {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException(
                    "Game id cannot be blank."
            );
        }

        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException(
                    "Game type cannot be blank."
            );
        }

        if (names == null || names.isEmpty()) {
            throw new IllegalArgumentException(
                    "Game must contain at least one localized name."
            );
        }

        for (Map.Entry<String, String> entry : names.entrySet()) {
            if (entry.getKey() == null
                    || entry.getKey().isBlank()) {

                throw new IllegalArgumentException(
                        "Language code cannot be blank."
                );
            }

            if (entry.getValue() == null
                    || entry.getValue().isBlank()) {

                throw new IllegalArgumentException(
                        "Localized game name cannot be blank."
                );
            }
        }

        if (iconPath == null || iconPath.isBlank()) {
            throw new IllegalArgumentException(
                    "Game icon path cannot be blank."
            );
        }

        if (gamePath == null || gamePath.isBlank()) {
            throw new IllegalArgumentException(
                    "Game definition path cannot be blank."
            );
        }

        Objects.requireNonNull(
                source,
                "Game package source cannot be null."
        );

        names = Map.copyOf(names);
    }

    /**
     * Returns the localized name for the specified language.
     *
     * <p>If the requested language is not available, English is used
     * as a fallback. If English is also unavailable, the first
     * available localized name is returned.</p>
     *
     * @param languageCode Language code.
     * @return Localized game name.
     */
    public String getName(
            String languageCode
    ) {
        if (languageCode != null) {
            String localizedName =
                    names.get(
                            languageCode
                    );

            if (localizedName != null) {
                return localizedName;
            }
        }

        String englishName =
                names.get("en");

        if (englishName != null) {
            return englishName;
        }

        return names
                .values()
                .iterator()
                .next();
    }
}