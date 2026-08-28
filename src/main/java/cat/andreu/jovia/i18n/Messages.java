package cat.andreu.jovia.i18n;

import io.github.agarriga18696.andreuutils.core.Language;
import io.github.agarriga18696.andreuutils.core.LanguageManager;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * Provides translated application messages for Jovia.
 *
 * @author Andreu
 * @version 1.0
 */
public final class Messages {

    // ----------------------------------------
    // CONSTANTS
    // ----------------------------------------

    private static final String BUNDLE_NAME = "i18n.messages";

    private Messages() {
        // Utility class
    }

    // ----------------------------------------
    // TRANSLATION
    // ----------------------------------------

    /**
     * Returns a translated message using the current application language.
     *
     * @param key       Translation key.
     * @param arguments Optional message arguments.
     * @return The translated message.
     */
    public static String text(
            String key,
            Object... arguments
    ) {

        return text(
                LanguageManager.getLanguage(),
                key,
                arguments
        );
    }

    /**
     * Returns a translated message using the specified language.
     *
     * @param language  Language to use.
     * @param key       Translation key.
     * @param arguments Optional message arguments.
     * @return The translated message.
     */
    public static String text(
            Language language,
            String key,
            Object... arguments
    ) {

        ResourceBundle bundle =
                ResourceBundle.getBundle(
                        BUNDLE_NAME,
                        language.locale()
                );

        String pattern = bundle.getString(key);

        if (arguments.length == 0) {
            return pattern;
        }

        return new MessageFormat(
                pattern,
                language.locale()
        ).format(arguments);
    }
}