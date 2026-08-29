package cat.andreu.jovia.games;

import io.github.agarriga18696.andreuutils.core.StringUtils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

/**
 * Stores the game engines available in Jovia.
 *
 * @author Andreu
 * @version 1.0
 */
public final class GameEngineRegistry {

    // ----------------------------------------
    // ATTRIBUTES
    // ----------------------------------------

    private final Map<String, GameEngine> engines;

    // ----------------------------------------
    // CONSTRUCTOR
    // ----------------------------------------

    /**
     * Creates an empty game engine registry.
     */
    public GameEngineRegistry() {
        engines = new HashMap<>();
    }

    // ----------------------------------------
    // REGISTRATION
    // ----------------------------------------

    /**
     * Registers a game engine.
     *
     * @param engine Game engine to register.
     */
    public void register(
            GameEngine engine
    ) {
        if (engine == null) {
            throw new IllegalArgumentException(
                    "Game engine cannot be null."
            );
        }

        String type = normalizeType(
                engine.getType()
        );

        if(engines.containsKey(type)) {
            throw new IllegalArgumentException(
                    "A game engine is already registered for type: "
                    + type
            );
        }

        engines.put(type, engine);
    }

    // ----------------------------------------
    // LOOKUP
    // ----------------------------------------

    /**
     * Finds the engine registered for the specified game type.
     *
     * @param type Game type.
     * @return Registered engine, if available.
     */
    public Optional<GameEngine> find(
            String type
    ) {
        return Optional.ofNullable(
                engines.get(
                        normalizeType(type)
                )
        );
    }

    /**
     * Checks whether an engine exists for the specified game type.
     *
     * @param type Game type.
     * @return {@code true} if an engine is registered.
     */
    public boolean supports(
            String type
    ) {
        return engines.containsKey(
                normalizeType(type)
        );
    }

    // ----------------------------------------
    // INTERNAL
    // ----------------------------------------

    private String normalizeType(
            String type
    ) {
        if (StringUtils.isNullOrBlank(type)) {
            throw new IllegalArgumentException(
                    "Game type cannot be blank."
            );
        }

        return type
                .strip()
                .toLowerCase(Locale.ROOT);
    }
}
