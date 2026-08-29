package cat.andreu.jovia.games;

import java.nio.file.Path;
import java.util.Collection;

/**
 * Coordinates loading, registration and resource access
 * for Jovia games.
 *
 * @author Andreu
 * @version 1.2
 */
public final class GameService {

    // ----------------------------------------
    // ATTRIBUTES
    // ----------------------------------------

    private final GamePackageLoader packageLoader;
    private final GamePackageRegistry packageRegistry;
    private final GameEngineRegistry engineRegistry;
    private final GameResourceLoader resourceLoader;

    // ----------------------------------------
    // CONSTRUCTOR
    // ----------------------------------------

    /**
     * Creates a game service.
     *
     * @param packageLoader   Game package loader.
     * @param packageRegistry Loaded game package registry.
     * @param engineRegistry  Available game engine registry.
     * @param resourceLoader  Game resource loader.
     */
    public GameService(
            GamePackageLoader packageLoader,
            GamePackageRegistry packageRegistry,
            GameEngineRegistry engineRegistry,
            GameResourceLoader resourceLoader
    ) {
        if (packageLoader == null) {
            throw new IllegalArgumentException(
                    "Game package loader cannot be null."
            );
        }

        if (packageRegistry == null) {
            throw new IllegalArgumentException(
                    "Game package registry cannot be null."
            );
        }

        if (engineRegistry == null) {
            throw new IllegalArgumentException(
                    "Game engine registry cannot be null."
            );
        }

        if (resourceLoader == null) {
            throw new IllegalArgumentException(
                    "Game resource loader cannot be null."
            );
        }

        this.packageLoader =
                packageLoader;

        this.packageRegistry =
                packageRegistry;

        this.engineRegistry =
                engineRegistry;

        this.resourceLoader =
                resourceLoader;
    }

    // ----------------------------------------
    // OPENING
    // ----------------------------------------

    /**
     * Loads and registers a Jovia game package.
     *
     * <p>If the same package is already loaded from the same source,
     * the existing package is returned.</p>
     *
     * @param source Jovia package file.
     * @return Loaded game package.
     * @throws GamePackageException If the package is invalid,
     *                              unsupported or conflicts with
     *                              another loaded package.
     */
    public GamePackage openGame(
            Path source
    ) throws GamePackageException {

        GamePackage gamePackage =
                packageLoader.load(
                        source
                );

        if (!engineRegistry.supports(
                gamePackage.type()
        )) {
            throw new GamePackageException(
                    "Unsupported game type: "
                            + gamePackage.type()
            );
        }

        var existingPackage =
                packageRegistry.find(
                        gamePackage.id()
                );

        if (existingPackage.isPresent()) {
            GamePackage existing =
                    existingPackage.get();

            if (existing.source().equals(
                    gamePackage.source()
            )) {
                return existing;
            }

            throw new GamePackageException(
                    "Another game package with the same id is already loaded: "
                            + gamePackage.id()
            );
        }

        packageRegistry.register(
                gamePackage
        );

        return gamePackage;
    }

    // ----------------------------------------
    // GAMES
    // ----------------------------------------

    /**
     * Returns all currently loaded game packages.
     *
     * @return Loaded game packages.
     */
    public Collection<GamePackage> getGames() {
        return packageRegistry.getAll();
    }

    // ----------------------------------------
    // RESOURCES
    // ----------------------------------------

    /**
     * Loads a resource from a game package.
     *
     * @param gamePackage Game package.
     * @param path        Resource path inside the package.
     * @param maxSize     Maximum allowed size in bytes.
     * @return Resource contents.
     * @throws GamePackageException If the resource cannot be loaded.
     */
    public byte[] loadResource(
            GamePackage gamePackage,
            String path,
            int maxSize
    ) throws GamePackageException {

        return resourceLoader.load(
                gamePackage,
                path,
                maxSize
        );
    }
}