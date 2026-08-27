package cat.andreu.minijocs.idioma;

import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Gestiona l'idioma de l'aplicació.
 */

public final class GestorIdioma {

    //
    // ATRIBUTS
    //

    private static Idioma idiomaActual;
    private static ResourceBundle missatges;

    //
    // CONSTRUCTOR
    //

    private GestorIdioma() {}

    //
    // MÈTODES
    //

    public static void establirIdioma(Idioma idioma) {
        idiomaActual = Objects.requireNonNull(
                idioma,
                "L'idioma no pot ser nul."
        );

        missatges = ResourceBundle.getBundle(
                "i18n.missatges",
                idiomaActual.getLocale()
        );
    }

    public static Idioma obtenirIdioma() {
        return idiomaActual;
    }

    public static boolean hiHaIdiomaSeleccionat() {
        return idiomaActual != null;
    }

    public static String text(String clau) {
        if(missatges == null) {
            throw new IllegalStateException(
                    "S'ha d'establir un idioma abans de consultar els textos."
            );
        }

        return missatges.getString(clau);
    }

}
