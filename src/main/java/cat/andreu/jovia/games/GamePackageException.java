package cat.andreu.jovia.games;

/**
 * Indicates that a Jovia game package could not be loaded or validated.
 *
 * @author Andreu
 * @version 1.0
 */
public final class GamePackageException extends Exception {

    /**
     * Creates an exception with the specified message.
     *
     * @param message Error description.
     */
    public GamePackageException(String message) {
        super(message);
    }


    /**
     * Creates an exception with the specified message and cause.
     *
     * @param message Error description.
     * @param cause   Original cause.
     */
    public GamePackageException(
            String message,
            Throwable cause
    ) {
        super(
                message,
                cause
        );
    }
}