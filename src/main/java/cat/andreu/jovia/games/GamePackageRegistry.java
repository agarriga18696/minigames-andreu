package cat.andreu.jovia.games;

import io.github.agarriga18696.andreuutils.core.StringUtils;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Stores the game packages loaded into Jovia.
 *
 * @author Andreu
 * @version 1.0
 */
public final class GamePackageRegistry {

    // ----------------------------------------
    // ATTRIBUTES
    // ----------------------------------------

    private final Map<String, GamePackage> packages;

    // ----------------------------------------
    // CONSTRUCTOR
    // ----------------------------------------

    /**
     * Creates an empty game package registry.
     */
    public GamePackageRegistry() {
        packages = new LinkedHashMap<>();
    }

    // ----------------------------------------
    // REGISTRATION
    // ----------------------------------------

    /**
     * Registers a loaded game package.
     *
     * @param gamePackage Game package to register.
     */
    public void register(
            GamePackage gamePackage
    ) {

        if (gamePackage == null) {
            throw new IllegalArgumentException(
                    "Game package cannot be null."
            );
        }

        String id =
                gamePackage.id();

        if (packages.containsKey(id)) {
            throw new IllegalArgumentException(
                    "A game package is already registered with id: "
                            + id
            );
        }

        packages.put(
                id,
                gamePackage
        );
    }

    // ----------------------------------------
    // LOOKUP
    // ----------------------------------------

    /**
     * Finds a loaded game package by identifier.
     *
     * @param id Game identifier.
     * @return Game package, if registered.
     */
    public Optional<GamePackage> find(
            String id
    ) {

        if (StringUtils.isNullOrBlank(id)) {
            return Optional.empty();
        }

        return Optional.ofNullable(
                packages.get(id)
        );
    }

    /**
     * Returns all registered game packages.
     *
     * @return Unmodifiable collection of game packages.
     */
    public Collection<GamePackage> getAll() {
        return packages
                .values()
                .stream()
                .toList();
    }

    /**
     * Returns the number of registered game packages.
     *
     * @return Number of packages.
     */
    public int size() {
        return packages.size();
    }
}